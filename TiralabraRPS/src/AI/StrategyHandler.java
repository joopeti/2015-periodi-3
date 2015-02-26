package AI;

import GameLogic.Player;
import GameLogic.Statistics;
import UI.TextUI;
import Utils.Lista;
import java.util.ArrayList;
import java.util.Random;

/**
 * Pisteyttää eri strategiat ja palauttaa parhaan strategian ehdottaman käden.
 * Huolehtii strategioiden päivittämisestä.
 */
public class StrategyHandler extends Player {

    /**
     * Metastrategioiden pistetytys ja valintalistat.
     */
    public double[][] stratPerformance;
    public int[][] stratChoice;
    public int numStrat;
    public int numMeta;
    public double decayMultiplier;
    private boolean decayOn;
    private boolean failSafe;

    private Random rnd;
    private int id;

    public ArrayList<Strategy> strategies;
    /**
     * Antaa indeksin perusteella kokonaisluvun (=käden), joka voittaa indeksin
     * (=annetun käden). Esim 0 = kivi, 2 = paperi wins[0] == 2;
     */
    public int[] wins = new int[]{2, 0, 1};

    public StrategyHandler(int id, int numMeta, double decay, boolean decayOn) {
        this.id = id;
        numStrat = 0;
        this.numMeta = numMeta;
        this.decayOn = decayOn;
        stratPerformance = new double[6][6];
        stratChoice = new int[6][6];
        decayMultiplier = decay;
        strategies = new ArrayList();
        rnd = new Random();
        failSafe = false;
    }

    /**
     * Ennakoi pelaajan seuraavan siirron ja palauttaa parhaiten menestyneimmän
     * metastrategian ehdottaman käden. Jos pelihistoria on liian lyhyt, metodi
     * palauttaa satunnaisen käden.
     *
     * @return
     */
    @Override
    public int chooseHand(TextUI ui) {
        if (Statistics.round > 2 && !failSafe) {
            return getBestChoice();
        } 
        else {
            return rnd.nextInt(3);
//            return 0;
        }
    }

    /**
     * Päivittää kierroksen jälkeen metastrategioiden pisteytyksen,
     * strategioiden sisäiset mallit ja niiden perusteella päivittää
     * metastrategioiden käsivalinnat.
     */
    @Override
    public void afterRoundUpdate() {
        updateMetaScores();
        updateAllStrategyModels();
        updateMetaChoices();
    }

    /**
     * Päivittää metastrategioiden valinnat annettujen ennustusten perusteella.
     * Ensimmäiset kolme strategiaa perustuvat vastapelaajan siirtoihin ja
     * jälkimmäiset kolme pelaajan omiin siirtoihin.
     */
    public void updateMetaChoices() {
        for (int i = 0; i < numStrat; i++) {
            int prediction = strategies.get(i).predictPlayerMove();
            int counter = strategies.get(i).predictAiMove();
            if (this.id == 0) {           //Jos ai:n id on 0, niin prediction ja counter muuttujat vaihdetaan keskenään.
                int tmp = prediction;   //Tämä mahdollistaa ai vs ai-pelit kun ai tietää kumpi malli on vastustaja.           
                prediction = counter;
                counter = tmp;
            }
            stratChoice[i][0] = wins[prediction];               //Voittaa pelaajan todennäköisimmän käden.
            stratChoice[i][1] = prediction;                     //Voittaa sen käden, joka voittaisi ylemmän vaihtoehdon käden.
            stratChoice[i][2] = wins[stratChoice[i][0]];        //Voittaa sen käden, joka voittaisi ylemmän vaihtoehdon käden.
            stratChoice[i][3] = wins[wins[counter]];            //Voittaa pelaajan käden, jos pelaaja käyttää samaa strategiaa ai:ta vastaan.
            stratChoice[i][4] = wins[wins[stratChoice[i][3]]];  //Voittaa sen käden, joka voittaisi ylemmän vaihtoehdon käden.
            stratChoice[i][5] = wins[wins[stratChoice[i][4]]];  //Voittaa sen käden, joka voittaisi ylemmän vaihtoehdon käden.
        }
    }

    /**
     * Palauttaa parhaan metastrategian ehdottaman käden.
     *
     * @return
     */
    public int getBestChoice() {
        double points = -999;
        int bestChoice = 0;
        for (int i = 0; i < numStrat; i++) {
            for (int j = 0; j < numMeta; j++) {
                if (stratPerformance[i][j] > points) {
                    points = stratPerformance[i][j];
                    bestChoice = stratChoice[i][j];
                }
            }
        }
        return bestChoice;
    }

    /**
     * Päivittää decay-arvon ja pyytää jokaista strategiaa päivittämään omat
     * tietonsa ja mahdollisesti kertomaan ne decay:llä.
     */
    public void updateAllStrategyModels() {
        if (decayOn) {
            updateMultiplier();
        }
        for (Strategy strat : strategies) {
            if (Statistics.round >= strat.getFirstRoundToUpdateModels()) {
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
        for (int i = 0; i < numStrat; i++) {
            for (int j = 0; j < numMeta; j++) {
                if (stratChoice[i][j] == wins[Statistics.getLastOpponentMove(id)]) {
                    stratPerformance[i][j] += 1.0;
                } else if (Statistics.getLastOpponentMove(id) == wins[stratChoice[i][j]]) {
                    stratPerformance[i][j] -= 1.0;
                } else {
                    stratPerformance[i][j] -= 0.5;
                }
                stratPerformance[i][j] *= decayMultiplier;
            }
        }
    }

    /**
     * Päivittää decay-kerrointa viime tuloksen perusteella. Häviö pienentää
     * kerrointa ja voitto kasvattaa. Mitä pienempi kerroin sitä nopeammin
     * vanhat strategiapisteytykset häviävät "muistista"
     */
    public void updateMultiplier() {
        if (id == Statistics.winner) {              //jos tekoäly voitti
            decayMultiplier *= 1.1;
        } else if (Statistics.winner == 1) {        //jos tasapeli
            decayMultiplier *= 0.95;
        } else {
            decayMultiplier *= 0.9;                 //jos tekoäly häivisi
        }
        if (decayMultiplier > 0.95) {
            decayMultiplier = 0.95;
        }
        if (decayMultiplier < 0.4) {
            decayMultiplier = 0.4;
            failSafe = true;                        //kun tekoäly häviää tarpeeksi monta kertaa peräkkäin, se alkaa ehdottaa satunnaisia käsiä.
        } else{
            failSafe = false;                       //satunnaisuus poistetaan käytöstä kun tekoäly voittaa
        }
    }

    /**
     * Lisää strategialistaan annetun strategian ja lisää kokonaistrategioiden
     * määrään yhden.
     *
     * @param s
     */
    @Override
    public void addStrategy(Strategy s) {
        strategies.add(s);
        numStrat++;
    }

    /**
     * Tulostaa Metastrategioiden pisteytyksen.
     */
    @Override
    public void printMetascores() {
        System.out.println("Pisteytysmatriisi: ");
        for (int i = 0; i < numStrat; i++) {
            for (int j = 0; j < numMeta; j++) {
                System.out.printf("%2.1f", stratPerformance[i][j]);
                System.out.print(": ");
            }
            System.out.println("");
        }
        System.out.print("Decay: ");
        printDecay();
    }

    /**
     * Tulostaa Metastrategioiden valitseman käden.
     */
    public void printMetaChoices() {
        for (int i = 0; i < numStrat; i++) {
            for (int j = 0; j < numMeta; j++) {
                System.out.print(stratChoice[i][j] + " ");
                System.out.print(": ");
            }
            System.out.println("");
        }
    }

    public void printDecay() {
        System.out.printf("%2.2f", decayMultiplier);
        System.out.println("");
    }
}
