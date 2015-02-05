/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameLogic.Statistics;
import org.junit.Before;
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
        Statistics.p1moves.add(2);
        int prediction = ai.predictPlayerMove();
        assertEquals(2, prediction);
    }

    @Test
    public void predictPlayerMoveWorksRight2() {
        Statistics.p1moves.add(1);
        int prediction = ai.predictPlayerMove();
        assertEquals(2, prediction);
    }

    @Test
    public void predictPlayerMoveWorksRight3() {
        Statistics.p1moves.add(0);
        int prediction = ai.predictPlayerMove();
        assertEquals(1, prediction);
    }

    @Test
    public void predictAiMoveWorksRight() {
        Statistics.p2moves.add(0);
        int prediction = ai.predictAiMove();
        assertEquals(0, prediction);
    }

    @Test
    public void predictAiMoveWorksRight2() {
        Statistics.p2moves.add(1);
        int prediction = ai.predictAiMove();
        assertEquals(1, prediction);
    }

    @Test
    public void predictAiMoveWorksRight3() {
        Statistics.p2moves.add(2);
        int prediction = ai.predictAiMove();
        assertEquals(2, prediction);
    }

    @Test
    public void updateModelsIncrementsRightValue() {
        Statistics.p2moves.add(0);
        Statistics.p2moves.add(1);
        Statistics.p1moves.add(0);
        Statistics.p1moves.add(2);
        ai.updateModels(1.00);
        assertTrue(1.99 < ai.playerFirstOrderMatrix[0][2]);
    }

    @Test
    public void updateModelsIncrementsRightValue2() {
        Statistics.p2moves.add(1);
        Statistics.p2moves.add(2);
        Statistics.p1moves.add(0);
        Statistics.p1moves.add(2);
        ai.updateModels(1.00);
        assertTrue(1.99 < ai.aiFirstOrderMatrix[1][2]);
    }

    @Test
    public void matrixDecayMultipliesValuesCorrectly() {
        ai.matrixDecay(0.5);
        assertTrue(0.0 == ai.playerFirstOrderMatrix[0][0]);
    }

    @Test
    public void matrixDecayMultipliesValuesCorrectly2() {
        ai.matrixDecay(0.5);
        assertTrue(0.5 <= ai.playerFirstOrderMatrix[1][0]);
    }

    public void playerMatrixIsOnlyZeroesAfterSetMatrix() {
        boolean foundNonZero = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ai.playerFirstOrderMatrix[i][j] != 0.0) {
                    foundNonZero = true;
                }
            }
        }
        assertTrue(!foundNonZero);
    }

    public void AiMatrixIsOnlyZeroesAfterSetMatrix() {
        boolean foundNonZero = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ai.aiFirstOrderMatrix[i][j] != 0.0) {
                    foundNonZero = true;
                }
            }
        }
        assertTrue(!foundNonZero);
    }
    
    public void getFirstRoundToUpdateModelsGivesThree(){
        assertEquals(3, ai.getFirstRoundToUpdateModels());
    }

}
