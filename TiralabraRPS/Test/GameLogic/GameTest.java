/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import GameLogic.Game;
import GameLogic.Game;
import java.io.ByteArrayInputStream;
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
public class GameTest {

    public Game game;
    public Statistics st;

    @Before
    public void setUp() {
        game = new Game();
        st = new Statistics();
    }

    @Test
    public void gameIsRunningAfterSettings() {
        game.setSettings("3");
        assertEquals(true, game.running);
    }

    @Test
    public void gameTurnIsOneAfterSettings() {
        game.setSettings("3");
        assertEquals(1, Statistics.round);
    }

    @Test
    public void gameTurnIsTwoAfterOneRound() {
        game.setSettings("3");
        game.playRound();
        assertEquals(2, Statistics.round);
    }
    

    @Test
    public void endGameSetsRunningFalse() {
        game.running = true;
        game.setSettings("3");
        game.endGame();
        assertEquals(false, game.running);
    }

    @Test
    public void turnIncrementsAfterPlayedRound() {
        game.setSettings("3");
        game.playRound();
        assertEquals(2, Statistics.round);
    }

    @Test
    public void checkWinnersUpdatesScoresWhenPlayerWins() {
        game.setSettings("3");
        for (int i = 0; i < 10; i++) {
            game.checkResults(0, 1);
        }
        assertEquals(10, Statistics.roundStats[0]);
    }

    @Test
    public void checkWinnersUpdatesScoresWhenAiWins() {
        game.setSettings("3");
        for (int i = 0; i < 10; i++) {
            game.checkResults(2, 1);
        }
        assertEquals(10, Statistics.roundStats[2]);
    }

    @Test
    public void checkWinnersDoesntGivePointsForLosing() {
        game.setSettings("3");
        for (int i = 0; i < 10; i++) {
            game.checkResults(1, 0);
        }
        assertEquals(0, Statistics.roundStats[0]);
    }

    @Test
    public void checkWinnersCountsDraws() {
        game.setSettings("3");
        for (int i = 0; i < 10; i++) {
            game.checkResults(1, 1);
            game.checkResults(0, 0);
            game.checkResults(2, 2);
        }
        assertEquals(30, Statistics.roundStats[1]);
    }
}
