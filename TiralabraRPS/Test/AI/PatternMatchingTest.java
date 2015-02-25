/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import AI.strategies.PatternMatching;
import GameLogic.Statistics;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joopeti
 */
public class PatternMatchingTest {
    
    public PatternMatchingTest() {
    }
    private PatternMatching pm;
    private Statistics st;
    
    @Before
    public void setUp() {
        pm = new PatternMatching(5);
        st = new Statistics();
    }

    @Test
    public void jotain(){
        
    }
}
