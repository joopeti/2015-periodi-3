package GameLogic;

import UI.*;
import java.util.ArrayList;

/**
 *
 * @author joopeti
 */
public class Game {

    private char[] kirjaimet = new char[]{'K', 'S', 'P'};
    private Hand kasi;
    private Hand[] kadet;
    private int gamemode;
    private int players;

    private int turn;
    private int pisteet[][];
    private boolean running;

    private TextUI ui;
    private AI ai;

    private ArrayList<Integer> pelihistoria;
    private int matriisi[][];
    private String tulos;

    public Game() {
        this.ui = new TextUI();
        this.ai = new AI();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        pelihistoria = new ArrayList();
        running = false;
    }

    public void setSettings() {
//        this.gamemode = ui.askGameMode();
//        this.players = ui.askPlayers();
        pisteet = new int[3][3];
        matriisi = new int[3][3];
        running = true;
        turn = 1;
    }

    public void playRound() {
        int player = ui.askHands(turn) - 1;
        int AI = ai.chooseHand();

        if (player == -1) {
            running = false;
        } else {
            checkWinners(player, AI);
            ai.addToGameHistory(player);
            ai.updateMatrix();
            running = ui.showResults(turn, pisteet, kadet[player], kadet[AI], pelihistoria, tulos);
//            printMatrix();
            turn++;
        }
    }

    public void checkWinners(int pelaaja, int tekoaly) {
        if (pelaaja == tekoaly) {
            pisteet[1][0] += 1;
            tulos = "TASAPELI";
        } else if ((pelaaja == 0 && tekoaly == 1) || (pelaaja == 1 && tekoaly == 2) || (pelaaja == 2 && tekoaly == 0)) {
            pisteet[0][0] += 1;
            tulos = "VOITIT!";
        } else {
            pisteet[2][0] += 1;
            tulos = "HÄVISIT!";
        }
    }

    public void createStringFromRounds(int pelaaja, int tekoaly) {
//        pelihistoria += kirjaimet[tekoaly];
//        pelihistoria += kirjaimet[pelaaja];
    }

    public void start() {
        setSettings();
        while (running) {
            playRound();
        }
    }
}
