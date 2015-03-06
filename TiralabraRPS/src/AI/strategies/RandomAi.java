/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI.strategies;

import java.util.Random;

/**
 * Palauttaa satunnaisen luvun.
 */
public class RandomAi extends Strategy{
    Random rnd = new Random();
    @Override
    public int predictPlayerMove() {
        return rnd.nextInt(3);
    }

    @Override
    public int predictAiMove() {
        return rnd.nextInt(3);
    }

    @Override
    public void updateModels(double decay) {
        
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 1;
    }
    
}
