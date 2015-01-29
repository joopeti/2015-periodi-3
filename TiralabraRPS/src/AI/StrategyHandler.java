package AI;

import GameLogic.Statistics;
import Utils.Lista;
import java.util.ArrayList;
import java.util.Random;

/**
 * Pisteyttää eri strategiat ja palauttaa parhaan strategian ehdottaman käden.
 * Huolehtii strategioiden päivittämisestä.
 */
public class StrategyHandler {

    /**
     * Metastrategioiden pistetytys ja valintalistat. metastrategiat
     */
    public double[][] stratPerformance;
    public int[][] stratChoice;
    public int numStrat;
    public int numMeta;
    public double decayMultiplier;
    public Random rnd;

    public ArrayList<Strategy> strategies;
    /**
     * Antaa indeksin perusteella kokonaisluvun (=käden), joka voittaa indeksin
     * (=annetun käden).
     */
    public int[] wins = new int[]{2, 0, 1};

    public StrategyHandler() {
        numStrat = 3;
        numMeta = 6;
        stratPerformance = new double[3][6];
        stratChoice = new int[3][6];
        decayMultiplier = 0.95;
        strategies = new ArrayList();
        rnd = new Random();
        Strategy markov1 = new MarkovFirstOrder();
        Strategy markov2 = new MarkovSecondOrder();
        strategies.add(markov1);
    }

    /**
     * Ennakoi pelaajan seuraavan siirron ja palauttaa parhaiten menestyneimmän
     * metastrategian ehdottaman käden. Jos pelihistoria on liian lyhyt, metodi
     * palauttaa satunnaisen käden.
     *
     * @return
     */
    public int chooseHand() {
        if (Statistics.round > 2) {
            return getBestChoice();
        } else {
            return rnd.nextInt(3);
        }
    }

    /**
     * Päivittää metastrategioiden valinnat annettujen ennustusten perusteella.
     * Ensimmäiset kolme strategiaa tarkastelevat vain pelaajan historiaa ja
     * jälkimmäiset kolme tekoälyn omaa pelihistoriaa.
     */
    public void updateMetaChoices() {
        for (int i = 0; i < 1; i++) {
            int prediction = strategies.get(i).predictPlayerMove();
            int counter = strategies.get(i).predictAiMove();
            stratChoice[i][0] = wins[prediction];
            stratChoice[i][1] = prediction;
            stratChoice[i][2] = wins[stratChoice[i][0]];
            stratChoice[i][3] = wins[wins[counter]];
            stratChoice[i][4] = wins[wins[stratChoice[i][3]]];
            stratChoice[i][5] = wins[wins[stratChoice[i][4]]];
        }
    }

    /**
     *
     * @return
     */
    public int getBestChoice() {
        double points = -999;
        int bestChoice = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                if (stratPerformance[i][j] > points) {
                    points = stratPerformance[i][j];
                    bestChoice = stratChoice[i][j];
                }
            }
        }
        return bestChoice;
    }

    /** 
     *
     */
    public void updateStrategiesAfterRound() {
        updateMultiplier(Statistics.winner);
        for (Strategy strat : strategies) {
            if(Statistics.round >= strat.getFirstRoundToUpdateModels()){
            strat.updateModels(decayMultiplier);
            }
        }
    }

    /**
     * Päivittää metastrategioiden pisteet niiden suoritusten mukaan. Häviöstä
     * strategia menettää yhden pisteen ja voitosta se saa yhden. Tasapelistä
     * saa hieman lisäpisteitä. Lopuksi kaikki pisteet kerrotaan vakiolla, jotta
     * tekoäly "unohtaisi" vanhat tulokset.
     */
    public void updateMetaScores() {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (stratChoice[i][j] == wins[Statistics.getLastPlayerMove()]) {
                    stratPerformance[i][j] += 1.0;
                } else if (Statistics.getLastPlayerMove() == wins[stratChoice[i][j]]) {
                    stratPerformance[i][j] -= 1.0;
                } else {
                    stratPerformance[i][j] += 0.0;
                }
                stratPerformance[i][j] *= decayMultiplier;
            }
        }
    }
    
    /** Päivittää decay-kerrointa viime tuloksen perusteella.
     *
     * @param winner
     */
    public void updateMultiplier(int winner){
        if(winner == 0){
            decayMultiplier *= 0.9;
        } else if(Statistics.winner == 1){
            decayMultiplier *= 0.95;
        } else{
            decayMultiplier *= 1.1;
        }
        if(decayMultiplier > 1){
            decayMultiplier = 1.0;
        }
        if(decayMultiplier < 0.1){
            decayMultiplier = 0.1;
        }
    
    }

    /**
     * Tulostaa Metastrategioiden pisteytyksen.
     */
    public void printMetascores() {
        System.out.print("[");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.printf("%2.1f", stratPerformance[i][j]);
                System.out.print(": ");
            }
            System.out.println("");
        }
    }

    /**
     * Tulostaa Metastrategioiden valitseman käden.
     */

    public void printMetaChoices() {
        System.out.print("");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(stratChoice[i][j] + " ");
                System.out.print(": ");
            }
            System.out.println("");
        }
    }
    
    /**
     *
     */
    public void printDecay(){
        System.out.printf("%2.2f", decayMultiplier);
        System.out.println("");
    }
}
