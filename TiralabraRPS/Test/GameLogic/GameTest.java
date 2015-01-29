/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import GameLogic.Game;
import GameLogic.Game;
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
        game.setSettings();
        assertEquals(true, game.running);
    }

    @Test
    public void gameTurnIsOneAfterSettings() {
        game.setSettings();
        assertEquals(1, Statistics.round);
    }

    @Test
    public void runningIsSetFalseAfterNegativeAsInput() {
        game.setSettings();
        game.playRound(-1, 1);
        assertEquals(false, game.running);
    }

    @Test
    public void turnIncrementsAfterPlayedRound() {
        game.setSettings();
        game.playRound(1, 1);
        assertEquals(2, Statistics.round);
    }

    @Test
    public void checkWinnersUpdatesScoresWhenPlayerWins() {
        game.setSettings();
        for (int i = 0; i < 10; i++) {
            game.checkWinners(0, 1);
        }
        assertEquals(10, Statistics.roundStats[0]);
    }
    
        @Test
    public void checkWinnersUpdatesScoresWhenAiWins() {
        game.setSettings();
        for (int i = 0; i < 10; i++) {
            game.checkWinners(2, 1);
        }
        assertEquals(10, Statistics.roundStats[2]);
    }

    @Test
    public void checkWinnersDoesntGivePointsForLosing() {
        game.setSettings();
        for (int i = 0; i < 10; i++) {
            game.checkWinners(1, 0);
        }
        assertEquals(0, Statistics.roundStats[0]);
    }

    @Test
    public void checkWinnersCountsDraws() {
        game.setSettings();
        for (int i = 0; i < 10; i++) {
            game.checkWinners(1, 1);
            game.checkWinners(0, 0);
            game.checkWinners(2, 2);
        }
        assertEquals(30, Statistics.roundStats[1]);
    }
}
