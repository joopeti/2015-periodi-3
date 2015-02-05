/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameLogic.Game;
import GameLogic.Statistics;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joopeti
 */
public class StrategyHandlerTest {

    public StrategyHandler st;
    public MarkovFirstOrder mk;
    public Game gm;

    public StrategyHandlerTest() {
        st = new StrategyHandler(2, 6, 0.5, false);
        mk = new MarkovFirstOrder();
        gm = new Game();
    }

    @Before
    public void setUp() {
        Statistics.p1moves.add(0);
        Statistics.p2moves.add(2);
        Statistics.p1moves.add(2);
        Statistics.p2moves.add(1);
        st.stratChoice = new int[][]{{1, 2, 1, 2, 1, 2}, {0, 1, 2, 0, 1, 2}, {2, 1, 0, 2, 1, 0}};
        st.stratPerformance = new double[][]{{3, 2, 1, 0, 1, 2}, {0, 1, 2, 2, 3, 2,}, {6, 2, 2, 1, 1, 0}};
    }

    @Test
    public void oneStrategyAfterAdding() {
        st.addStrategy(mk);
        assertEquals(1, st.numStrat);
    }

    @Test
    public void chooseHandReturnsBestHandAfterTwoRounds() {
        st.addStrategy(mk);
        st.addStrategy(mk);
        st.addStrategy(mk);

//        Statistics.winner = 2;
//        st.afterRoundUpdate();
//        Statistics.p1moves.add(0);
//        Statistics.p2moves.add(2);
//        st.afterRoundUpdate();
//        Statistics.winner = 0;
//        Statistics.p1moves.add(2);
//        Statistics.p2moves.add(1);
//        st.afterRoundUpdate();
//        Statistics.winner = 2;
        Statistics.round = 3;
        assertEquals(2, st.getBestChoice());
    }

    @Test
    public void updateMetaChoicesIncrementsPointsForWin() {
        st.updateMetaChoices();
    }

    @Test
    public void getBestChoiceGivesChoiceWithBestStrategyScore() {
        st.addStrategy(mk);
        st.addStrategy(mk);
        st.addStrategy(mk);
        assertEquals(2, st.getBestChoice());
    }

    @Test
    public void strategyModelsAreUpdatedAfterRound() {
        st.addStrategy(mk);
        Statistics.round = 3;
        mk.playerFirstOrderMatrix = new double[][]{{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
        st.updateAllStrategyModels();
        assertTrue(1.0 == mk.playerFirstOrderMatrix[1][0]);
    }

    @Test
    public void strategyModelsAreNotUpdatedIfRoundLessThanNeeded() {
        st.addStrategy(mk);
        Statistics.round = 1;
        mk.playerFirstOrderMatrix = new double[][]{{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
        Statistics.winner = 0;
        st.updateAllStrategyModels();
        assertTrue(2.0 == mk.playerFirstOrderMatrix[1][0]);
    }

    @Test
    public void metaScoresAreIncrementedAfterWinningChoice() {
        st.addStrategy(mk);
        st.updateMetaScores();
        System.out.println(st.stratPerformance[0][0]);
        assertTrue(2.0 == st.stratPerformance[0][0]);
    }

    @Test
    public void metaScoresAreDecrementedAfterLosingChoice() {
        st.addStrategy(mk);
        st.addStrategy(mk);
        st.updateMetaScores();
        System.out.println(st.stratPerformance[1][0]);
        assertTrue(-0.5 == st.stratPerformance[1][0]);
    }

    @Test
    public void decayMultiplierIsIncreasedAfterWin() {
        st.decayMultiplier = 0.5;
        Statistics.winner = 2;
        st.updateMultiplier();
        assertTrue(0.55 == st.decayMultiplier);
    }

    @Test
    public void decayMultiplierIsDecreasedAfterLoss() {
        st.decayMultiplier = 0.5;
        Statistics.winner = 0;
        st.updateMultiplier();
        assertTrue(0.45 == st.decayMultiplier);
    }

    @Test
    public void addingStrategyCreatesMetaScoreArraysToo() {
        
    }

}
