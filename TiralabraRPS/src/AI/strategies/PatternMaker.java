/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI.strategies;

import GameLogic.Statistics;

/**
 * Antaa valintansa säännöllisinä jaksoina, joita PatternMatching yrittää huomata.
 */
public class PatternMaker extends Strategy{
    private int i;
    public PatternMaker(){
        i = 1;
    }
    
    @Override
    public int predictPlayerMove() {
        return (Statistics.round + i) * 7  % 3;
    }

    @Override
    public int predictAiMove() {
        return (Statistics.round + i) * 7 % 3;
    }

    @Override
    public void updateModels(double decay) {
        if(Statistics.round % 4 == 0){
            i = (i + Statistics.round) % 1231231;       //luku estää luvun ylivuodon
        }
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 1;
    }
    
}
