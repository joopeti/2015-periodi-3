/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameLogic.Statistics;

/**
 * Etsii pelatuista siirroista toistuvia kuvioita ja ennustaa siirron niiden perusteella.
 */
public class PatternMatching extends Strategy{
    int k = 0;
    
    @Override
    public int predictPlayerMove() {
        for (int i = 0; i < Statistics.round; i++) {
            
        }
        return 0;
    }
    
    private int getPatternWithFive(){
        int[] pattern = new int[5];
        if(Statistics.round > 5){
            pattern[0] = Statistics.p1moves.get(k - 5);
        }
        return 0;
    }

    @Override
    public int predictAiMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateModels(double decay) {
        
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 1;
    }
    
}
