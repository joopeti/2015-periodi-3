/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI.strategies;

import AI.Strategy;
import GameLogic.Statistics;
import Utils.Lista;

/**
 * Etsii pelatuista siirtoyhdistelmistä toistuvuuksia ja ennustaa vastustajan
 * siirron pisimmän löydetyn kuvion perusteella.
 */
public class PatternMatching extends Strategy {

    int bestLength = 0;
    int p1_prediction;
    int p2_prediction;
    int patternLength;

    /**
     * Konstruktori.
     * @param patternLength: etsittävien patternien maksimipituus.
     */
    public PatternMatching(int patternLength) {
        this.patternLength = patternLength;
    }

    /**
     * Palauttaa p1:n todennäköisimmän siirron.
     * @return
     */
    @Override
    public int predictPlayerMove() {
        searchPatterns(patternLength, 1);
        bestLength = 0;
        return p1_prediction;
    }

    /**
     * Luo annetun pituisen patternin molempien pelaajien siirroista ja etsii vastaavuuksia niihin aiemmista
     * siirroista.
     *
     * @param n patternin maksimipituus
     * @param siirrot lista pelatuista siirroista joista patternit etsitään (p1
     * tai p2)
     * @param id listan pelaajan id.
     */
    private void searchPatterns(int n, int id) {
        int[] p1pattern = getPattern(n, Statistics.p1moves);
        int[] p2pattern = getPattern(n, Statistics.p2moves);
        int length = 0;
            OUTER:
            for (int i = Statistics.round - 2; i >= n; i--) {   //lähtee toisiksiviimeisestä siirrosta ensimmäiseen asti kunnes löydetään vastaava kuvio
                for (int j = 0; j < n; j++) {
                    if (p1pattern[j] == Statistics.p1moves.get(i - j) && p2pattern[j] == Statistics.p2moves.get(i - j)) {
                        length++;
                        if (length > bestLength) {
                            foundPatternLongerThanBest(i - j, j + 1, id);
                        } 
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
    }

    /**
     * Palauttaa p2:n todennäköisimmän siirron.
     * @return
     */
    @Override
    public int predictAiMove() {
        searchPatterns(patternLength, 2);
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
