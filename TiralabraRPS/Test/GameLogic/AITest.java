/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import GameLogic.AI;
import GameLogic.AI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joopeti
 */
public class AITest {

    public AI ai;

    @Before
    public void setUp() {
        ai = new AI();
    }

    @Test
    public void predictMoveWorksRight() {
        int prediction = ai.predictMove(new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 4}}, 2);
        assertEquals(2, prediction);
    }

    @Test
    public void predictMoveWorksRight2() {
        int prediction = ai.predictMove(new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}}, 2);
        assertEquals(1, prediction);
    }

    @Test
    public void predictMoveWorksRight3() {
        int prediction = ai.predictMove(new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}}, 1);
        assertEquals(2, prediction);
    }

    @Test
    public void updateMetahandsUpdatesMeta0Correct() {
        int prediction = 2;
        int aipredict = 1;
        ai.updateMetaHands(prediction, aipredict);
        assertTrue(1.0 == ai.metastrat[0]);
    }

    @Test
    public void updateMetahandsUpdatesMeta3Correct() {
        int prediction = 2;
        int aipredict = 1;
        ai.updateMetaHands(prediction, aipredict);
        assertEquals(2, ai.metastrat[1]);
    }

    @Test
    public void aiGivesPositiveHandNumber() {
        ai.addToGameHistory(1, 2);
        assertTrue(0 <= ai.chooseHand());
    }

    @Test
    public void aiGivesValidHandNumber() {
        ai.addToGameHistory(1, 2);
        assertTrue(ai.chooseHand() < 3);
    }

    @Test
    public void updateMetascoresIncrementsForWin() {
        ai.playermoves.add(2);
        ai.playermoves.add(2);
        ai.playermoves.add(2);
        ai.metapoints[1] = 1;
        ai.metastrat[1] = 1;
        ai.updateMetaScores();
        assertTrue(2 == ai.metapoints[1]);
    }
    
    @Test
    public void updateMatricesIncrementsLastMoveToRightPlace(){
        ai.playermovematrix = new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}};
        
        ai.playermoves.add(1);
        ai.playermoves.add(1);
        ai.updateMatrices();
        assertTrue(1.0 == ai.playermovematrix[1][1]);
    }
    
    @Test
    public void checkBestMetaStrategyGivesIndexOfStrategyWithMostPoints(){
        ai.metapoints = new double[]{0, -1, 2, 5, 3, 6, 0};
        ai.checkBestMetaStrategy();
        assertEquals(5, ai.best);
    }
    
    @Test
    public void matrixDecayMultipliesValuesInMatrix(){
        ai.playermovematrix = new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}};
        ai.matrixDecay(0.5);
        assertTrue(0.5 == ai.playermovematrix[2][0]);
    }
    
    @Test
    public void addToGameHistoryAddsToPlayerHistory(){
        ai.addToGameHistory(1, 2);
        ai.addToGameHistory(0, 1);
        ai.addToGameHistory(2, 0);
        assertTrue(1 == ai.playermoves.get(0));
    }
    
    @Test
    public void addToGameHistoryAddsToAiHistory(){
        ai.addToGameHistory(1, 2);
        ai.addToGameHistory(0, 1);
        ai.addToGameHistory(2, 0);
        assertTrue(0 == ai.aimoves.get(2));
    }
    
    @Test
    public void initializeArraysMakesEmptyMetaLists(){
        ai.initializeArrays();
        double n = 0;
        for (int i = 0; i < 7; i++) {
            n += ai.metastrat[i];
            n += ai.metapoints[i];
        }
        assertTrue(0.0 == n);
    }
    

}
