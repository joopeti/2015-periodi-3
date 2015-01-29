/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import AI.MarkovFirstOrder;
import AI.MarkovFirstOrder;
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
public class MarkovFirstOrderTest {

    public MarkovFirstOrder ai;
    public Statistics st;

    @Before
    public void setUp() {
        ai = new MarkovFirstOrder();
        st = new Statistics();
        ai.playerFirstOrderMatrix = new double[][]{
            {0, 2, 1},
            {2, 0, 3},
            {1, 3, 4}};
        ai.aiFirstOrderMatrix = new double[][]{
            {6, 2, 3},
            {0, 5, 1},
            {2, 1, 4}};
    }

    @Test
    public void predictPlayerMoveWorksRight() {
        Statistics.playermoves.add(2);
        int prediction = ai.predictPlayerMove();
        assertEquals(2, prediction);
    }

    @Test
    public void predictPlayerMoveWorksRight2() {
        Statistics.playermoves.add(1);
        int prediction = ai.predictPlayerMove();
        assertEquals(2, prediction);
    }

    @Test
    public void predictPlayerMoveWorksRight3() {
        Statistics.playermoves.add(0);
        int prediction = ai.predictPlayerMove();
        assertEquals(1, prediction);
    }

    @Test
    public void predictAiMoveWorksRight() {
        Statistics.aimoves.add(0);
        int prediction = ai.predictAiMove();
        assertEquals(0, prediction);
    }

    @Test
    public void predictAiMoveWorksRight2() {
        Statistics.aimoves.add(1);
        int prediction = ai.predictAiMove();
        assertEquals(1, prediction);
    }

    @Test
    public void predictAiMoveWorksRight3() {
        Statistics.aimoves.add(2);
        int prediction = ai.predictAiMove();
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
    public void updateMatricesIncrementsLastMoveToRightPlace() {
        ai.playerFirstOrderMatrix = new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}};

        ai.playermoves.add(1);
        ai.playermoves.add(1);
        ai.updateMatrices();
        assertTrue(1.0 == ai.playerFirstOrderMatrix[1][1]);
    }

    @Test
    public void checkBestMetaStrategyGivesIndexOfStrategyWithMostPoints() {
        ai.metapoints = new double[]{0, -1, 2, 5, 3, 6, 0};
        ai.checkBestMetaStrategy();
        assertEquals(5, ai.best);
    }

    @Test
    public void matrixDecayMultipliesValuesInMatrix() {
        ai.playerFirstOrderMatrix = new double[][]{
            {0, 2, 3},
            {2, 0, 3},
            {1, 3, 2}};
        ai.matrixDecay(0.5);
        assertTrue(0.5 == ai.playerFirstOrderMatrix[2][0]);
    }

    @Test
    public void addToGameHistoryAddsToPlayerHistory() {
        ai.addToGameHistory(1, 2);
        ai.addToGameHistory(0, 1);
        ai.addToGameHistory(2, 0);
        assertTrue(1 == ai.playermoves.get(0));
    }

    @Test
    public void addToGameHistoryAddsToAiHistory() {
        ai.addToGameHistory(1, 2);
        ai.addToGameHistory(0, 1);
        ai.addToGameHistory(2, 0);
        assertTrue(0 == ai.aimoves.get(2));
    }

    @Test
    public void initializeArraysMakesEmptyMetaLists() {
        ai.initializeArrays();
        double n = 0;
        for (int i = 0; i < 7; i++) {
            n += ai.metastrat[i];
            n += ai.metapoints[i];
        }
        assertTrue(0.0 == n);
    }

}
