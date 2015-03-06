/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI.strategies;

import GameLogic.Statistics;
import Utils.Lista;

/**
 * Etsii pelatuista siirroista toistuvia kuvioita ja ennustaa vastustajan
 * siirron pisimmän löydetyn kuvion perusteella.
 */
public class PatternMatchingPlayer extends Strategy {

    int bestLength = 0;
    int p1_prediction;
    int p2_prediction;
    int patternLength;

    /**
     * @param patternLength: etsittävien patternien maksimipituus.
     */
    public PatternMatchingPlayer(int patternLength) {
        this.patternLength = patternLength;
    }

    /**
     * Palauttaa p1:n todennäköisimmän siirron, etsimällä pelaajan kaikkien siirtojen historiasta toistuvuuksia.
     *
     * @return
     */
    @Override
    public int predictPlayerMove() {
        searchPatterns(patternLength, Statistics.p1moves, 1);
        bestLength = 0;
        return p1_prediction;
    }

    /**
     * Luo annetun pituisen patternin ja etsii vastaavuuksia siihen aiemmista
     * siirroista.
     *
     * @param n patternin maksimipituus
     * @param siirrot lista pelatuista siirroista joista patternit etsitään (p1
     * tai p2)
     * @param id listan pelaajan id.
     */
    private void searchPatterns(int n, Lista<Integer> siirrot, int id) {
        int[] pattern = getPattern(n, siirrot);
        int length = 0;
            OUTER:
            for (int i = Statistics.round - 2; i >= n; i--) {   //lähtee toisiksiviimeisestä siirrosta ensimmäiseen asti kunnes löydetään vastaava kuvio
                for (int j = 0; j < n; j++) {
                    if (pattern[j] == siirrot.get(i - j)) {
                        length++;
                        //jos löydetään parasta pidempi pattern, niin ennustusta päivitetään. 
                        if (length > bestLength) {
                            foundPatternLongerThanBest(i - j, j + 1, id);
                        } 
                        //jos löydetään maksimipituinen pattern, niin etsintä lopetetaan.
                        if(length == n){
                            break OUTER;
                        }
                    } else {
                        length = 0;
                        break;
                    }
                }
            }
    }
    
    /**
     * Luo annetun pituisen patternin viimeisistä siirroista ja palauttaa sen.
     * @param n
     * @param siirrot
     * @return 
     */
    private int[] getPattern(int n, Lista<Integer> siirrot){
        int[] pattern = new int[n];
        if (Statistics.round > n + 1) {
            for (int i = 0; i < n; i++) {
                pattern[i] = siirrot.get(Statistics.round - i - 1);  // luo kuvion n:stä viimeisimmästä siirrosta.
            }
        }
        return pattern;
    }

    /**
     * Päivittää todennäköisimmän valinnan pisimmän löydetyn toistuvuuden
     * perusteella.
     *
     * @param i
     * @param length
     * @param id
     */

    private void foundPatternLongerThanBest(int i, int length, int id) {
        if (length > bestLength && id == 1) {
            bestLength = length;
            p1_prediction = Statistics.p1moves.get(i + length);
        }
        if (length > bestLength && id == 2) {
            bestLength = length;
            p2_prediction = Statistics.p2moves.get(i + length);
        }
//        System.out.println("Löydettiin " + length + "-pitkä kuvio. Ennustus: " + p1_prediction);

    }

    /**
     * Palauttaa p2:n todennäköisimmän siirron, etsimällä pelaajan kaikkien siirtojen historiasta toistuvuuksia.
     * @return
     */
    @Override
    public int predictAiMove() {
        searchPatterns(patternLength, Statistics.p2moves, 2);
        bestLength = 0;
        return p2_prediction;
    }

    @Override
    public void updateModels(double decay) {
    }

    /**
     * Palauttaa vuoron jonka jälkeen algoritmi voi alkaa päivittämään itseään.     *
     * @return
     */
    @Override
    public int getFirstRoundToUpdateModels() {
        return 1;
    }

}
