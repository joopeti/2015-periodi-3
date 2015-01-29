/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joopeti
 */
public class Statistics {

    /**
     * Pelaajan siirtohistoria.
     */
    public static ArrayList<Integer> playermoves = new ArrayList();

    /**
     *
     */
    public static ArrayList<Integer> aimoves = new ArrayList();

    /**
     * Tekoälyn siirtohistoria.
     */
    public static int[] roundStats;

    /**
     * Kierroslaskin.
     */
    public static int round;

    /**
     * Viime kierroksen voittaja. 0 = pelaaja, 1 = tasapeli, 2 = tekoäly
     */
    public static int winner;

    public Statistics() {
        roundStats = new int[]{0, 0, 0};
        round = 0;
    }

    /**
     * Palauttaa viimeisen pelaajan siirron.
     *
     * @return
     */
    public static int getLastPlayerMove() {
        return playermoves.get(playermoves.size() - 1);
    }

    /**
     * Palauttaa viimeisen tekoälyn siirron.
     *
     * @return
     */
    public static int getLastAiMove() {
        return aimoves.get(aimoves.size() - 1);
    }

    /**
     * Päivittää siirtohistoriataulukot.
     *
     * @param player
     * @param ai
     */
    public void updatePlayerAndAiMoves(int player, int ai) {
        playermoves.add(player);
        aimoves.add(ai);
    }

    /**
     * Päivittää tulostaulukkoa. Pelaajan voitot = 0, Tasapelit = 1, Tekoälyn
     * voitot = 2
     */
    public void updateRoundStatistics(int i) {
        roundStats[i]++;
        winner = i;
    }

    /**
     * Palauttaa tulostaulukon.
     */
    public int[] getRoundStatistics() {
        return roundStats;
    }

    /**
     * Päivittää kierroslukua yhdellä.
     */
    public void roundIncrement() {
        round++;
    }

    /**
     * Palauttaa viimeiset kaksi siirtoa pelaajalta.
     *
     * @return
     */
    public static int[] getLastTwoPlayerMoves() {
        int[] moves = new int[]{playermoves.get(playermoves.size() - 2), playermoves.get(playermoves.size() - 1)};
        return moves;
    }

    /**
     * Palauttaa viimeiset kaksi siirtoa tekoälyltä.
     *
     * @return
     */
    public static int[] getLastTwoAiMoves() {
        int[] moves = new int[]{aimoves.get(aimoves.size() - 2), aimoves.get(aimoves.size() - 1)};
        return moves;
    }

    /**
     * Palauttaa pelin tilastot oletusarvoisiksi.
     */
    public void resetStats() {
        playermoves.clear();
        aimoves.clear();
        roundStats = new int[]{0, 0, 0};
        round = 1;
    }

    /**
     * Tulostaa molempien pelaajien siirtohistorian.
     *
     */
    public void showMoveHistory() {
        System.out.println("Pelaaja: " + playermoves);
        System.out.println("Tekoäly: " + aimoves);
    }

    public void setWinner(int tulos) {
        winner = tulos;
    }

    public void saveGameStatsToFile(){
        PrintWriter pw = null;
        try {
            File file = new File("siirrot.txt");
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println("stats: ");
            for (Integer playermove : playermoves) {
                pw.print(playermove + " ");
            }
            pw.println();
            for (Integer aimove : aimoves) {
                pw.print(aimove + " ");
            }
            pw.println();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printStatistics() {
        System.out.print("Voittoprosentti: ");
        System.out.printf("%2.2f", (double)roundStats[0] / (round - roundStats[1]));
    }
}
