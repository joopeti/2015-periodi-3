/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameLogic.Statistics;
import Utils.Lista;

/**
 * Etsii pelatuista siirroista toistuvia kuvioita
 * ja ennustaa vastustajan siirron pisimmän löydetyn kuvion perusteella.
 */
public class PatternMatching extends Strategy {

    int bestLength = 0;
    int p1_prediction;
    int p2_prediction;
    int patternLength;
    
    public PatternMatching(int patternLength) {
        this.patternLength = patternLength;
    }

    @Override
    public int predictPlayerMove() {
        searchPatterns(patternLength, Statistics.p1moves, 1);
        searchPatterns(patternLength, Statistics.p2moves, 2);
        return p1_prediction;
    }

    private void searchPatterns(int n, Lista<Integer> siirrot, int id) {
        int[] pattern = new int[n];
        if (Statistics.round > n + 1) {
            for (int i = 0; i < n; i++) {
                pattern[i] = siirrot.get(Statistics.round - i - 1);  // luo kuvion n:stä viimeisimmästä siirrosta.
//                System.out.print(pattern[i] + ", ");
            }
            for (int i = Statistics.round - 2; i >= n; i--) {   //lähtee toisiksiviimeisestä siirrosta ensimmäiseen asti kunnes löydetään vastaava kuvio
                for (int j = 0; j < n; j++) {
                    if (pattern[j] == siirrot.get(i - j)) {
                        foundPattern(i - j, j + 1, id);
                    } else{
                        break;
                    }
                }
            }
        }
    }

    private void foundPattern(int i, int length, int id) {
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

    @Override
    public int predictAiMove() {
        return 0;
    }

    @Override
    public void updateModels(double decay) {
        bestLength = 0;
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 1;
    }

}
