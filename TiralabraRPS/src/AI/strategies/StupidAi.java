/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI.strategies;

import GameLogic.Statistics;

/**
 *
 * @author joopeti
 */
public class StupidAi extends Strategy{
    public int[] wins = new int[]{2, 0, 1};
    
    @Override
    public int predictPlayerMove() {
        return wins[Statistics.getLastP1Move()];
//        return Statistics.getLastP1Move();
    }

    @Override
    public int predictAiMove() {
        return wins[Statistics.getLastP2Move()];
//        return Statistics.getLastP2Move();
    }

    @Override
    public void updateModels(double decay) {
        // Rollseyes...
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 2;
    }
    
}
