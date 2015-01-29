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
public class StrategyHandlerTest {
    public StrategyHandler st;
    public MarkovFirstOrder mk; 
    
    public StrategyHandlerTest() {
        st = new StrategyHandler();
        mk = new MarkovFirstOrder();
        Statistics.playermoves.add(0);
        Statistics.playermoves.add(2);
        Statistics.aimoves.add(2);
        Statistics.aimoves.add(1);
    }
    
    @Before
    public void setUp() {
        
    }
    
    @Test
    public void oneStrategyAfterAdding(){
        st.addStrategy(mk);
        assertEquals(1, st.numStrat);
    }
    @Test 
    public void chooseHandGivesBestHandAfterTwoRounds(){
    
    }
    @Test 
    public void metaChoicesAreUpdatedAfterRound(){
    
    }
    @Test 
    public void getBestChoiceGivesChoiceWithBestStrategyScore(){
    
    }
    @Test 
    public void strategyModelsAreUpdatedAfterRound(){
        
    }
    @Test 
    public void strategyModelsAreNotUpdatedIfRoundLessThanNeeded(){
        
    }
    @Test 
    public void metaScoresAreIncrementedAfterWinningChoice(){
    
    }
    @Test 
    public void metaScoresAreDecrementedAfterLosingChoice(){
    
    }
    @Test 
    public void metaScoresAreMultipliedWithDecayAfterUpdatingScores(){
    
    }
    @Test 
    public void decayMultiplierIsIncreasedAfterWin(){
        
    }
    @Test 
    public void decayMultiplierIsDecreasedAfterLoss(){
        
    }
    @Test 
    public void addingStrategyCreatesMetaScoreArraysToo(){
        
    }
    


}
