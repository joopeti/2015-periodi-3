/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import AI.strategies.MarkovSecondOrder;
import GameLogic.Statistics;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joopeti
 */
public class MarkovSecondOrderTest {

    public MarkovSecondOrderTest() {
    }

    public MarkovSecondOrder ai;
    public Statistics st;

    @Before
    public void setUp() {
        ai = new MarkovSecondOrder();
        st = new Statistics();
        ai.p1matrix = new double[][][]{{
            {0, 2, 1},
            {2, 0, 3},
            {1, 3, 4}}
        };
        ai.p2matrix = new double[][][]{
            {{6, 2, 3},
            {0, 5, 1},
            {2, 1, 4}}
        };
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
        assertTrue(1.99 < ai.p1matrix[0][2][0]);
    }

    @Test
    public void updateModelsIncrementsRightValue2() {
        Statistics.p2moves.add(1);
        Statistics.p2moves.add(2);
        Statistics.p1moves.add(0);
        Statistics.p1moves.add(2);
        ai.updateModels(1.00);
        assertTrue(1.99 < ai.p2matrix[1][2][0]);
    }

    @Test
    public void matrixDecayMultipliesValuesCorrectly() {
        ai.matrixDecay(0.5);
        assertTrue(0.0 == ai.p1matrix[0][0][0]);
    }

    @Test
    public void matrixDecayMultipliesValuesCorrectly2() {
        ai.matrixDecay(0.5);
        assertTrue(0.5 <= ai.p1matrix[1][0][0]);
    }

    public void playerMatrixIsOnlyZeroesAfterSetMatrix() {
        boolean foundNonZero = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (ai.p1matrix[i][j][k] != 0.0) {
                        foundNonZero = true;
                    }
                }
            }
        }
        assertTrue(!foundNonZero);
    }

    public void AiMatrixIsOnlyZeroesAfterSetMatrix() {
        boolean foundNonZero = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (ai.p2matrix[i][j][k] != 0.0) {
                        foundNonZero = true;
                    }
                }
            }
        }
        assertTrue(!foundNonZero);
    }

    public void getFirstRoundToUpdateModelsGivesThree() {
        assertEquals(3, ai.getFirstRoundToUpdateModels());
    }
}
