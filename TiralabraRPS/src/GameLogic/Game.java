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
        setMatrix();
        running = true;
        turn = 1;
    }

    public void playRound() {
        int player = ui.askHands(turn) - 1;
        int AI = ai.chooseHand(pelihistoria, matriisi);

        if (player == -1) {
            running = false;
        } else {
            checkWinners(player, AI);
            pelihistoria.add(player);
            updateMatrix();
            running = ui.showResults(turn, pisteet, kadet[player], kadet[AI], pelihistoria, tulos);
//            printMatrix();
            turn++;
        }
    }

    public void updateMatrix() {
        if(turn >= 2){
        int viimeinen = pelihistoria.get(pelihistoria.size() - 1);
        int toisiksiviimeinen = pelihistoria.get(pelihistoria.size() - 2);
        matriisi[toisiksiviimeinen][viimeinen]++;
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

    public void setMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriisi[i][j] = 0;
            }
        }
    }

    public void printMatrix() {
        System.out.println("  K S P");
        for (int i = 0; i < 3; i++) {
            System.out.print(kirjaimet[i] + ":");
            for (int j = 0; j < 3; j++) {
                System.out.print(matriisi[i][j] + " ");
            }
            System.out.println("");
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
