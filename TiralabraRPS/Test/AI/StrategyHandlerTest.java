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
        st = new StrategyHandler(2, 6, 0.95, true);
        mk = new MarkovFirstOrder();
        gm = new Game();
    }

    @Before
    public void setUp() {
//        Statistics.p1moves.add(0);
//        Statistics.p2moves.add(2);
//        Statistics.winner = 0;
//        st.afterRoundUpdate();
//        Statistics.p1moves.add(2);
//        Statistics.p2moves.add(1);
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
//        Statistics.round = 3;
    }

    @Test
    public void oneStrategyAfterAdding() {
        st.addStrategy(mk);
        assertEquals(1, st.numStrat);
    }

    @Test
    public void chooseHandGivesBestHandAfterTwoRounds() {
//        st.addStrategy(mk);
//        st.addStrategy(mk);
//        st.addStrategy(mk);
//                Statistics.p1moves.add(0);
//        Statistics.p2moves.add(2);
//        Statistics.winner = 0;
//        st.afterRoundUpdate();
//        Statistics.p1moves.add(2);
//        Statistics.p2moves.add(1);
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
//        Statistics.round = 3;
//        assertEquals(2, st.getBestChoice());
    }

    @Test
    public void metaChoicesAreUpdatedAfterRound() {

    }

    @Test
    public void getBestChoiceGivesChoiceWithBestStrategyScore() {

    }

    @Test
    public void strategyModelsAreUpdatedAfterRound() {

    }

    @Test
    public void strategyModelsAreNotUpdatedIfRoundLessThanNeeded() {

    }

    @Test
    public void metaScoresAreIncrementedAfterWinningChoice() {

    }

    @Test
    public void metaScoresAreDecrementedAfterLosingChoice() {

    }

    @Test
    public void metaScoresAreMultipliedWithDecayAfterUpdatingScores() {

    }

    @Test
    public void decayMultiplierIsIncreasedAfterWin() {

    }

    @Test
    public void decayMultiplierIsDecreasedAfterLoss() {

    }

    @Test
    public void addingStrategyCreatesMetaScoreArraysToo() {

    }

}
