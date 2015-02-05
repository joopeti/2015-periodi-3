/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author joopeti
 */
public class Statistics {

    /**
     * Pelaajan siirtohistoria.
     */
    public static ArrayList<Integer> p1moves = new ArrayList();

    /**
     *
     */
    public static ArrayList<Integer> p2moves = new ArrayList();

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
    public static int getLastP1Move() {
        return p1moves.get(p1moves.size() - 1);
    }
    
    public static int getLastOpponentMove(int id){
        if(id == 0){
            return getLastP2Move();
        } else{
            return getLastP1Move();
        }
    }

    /**
     * Palauttaa viimeisen tekoälyn siirron.
     *
     * @return
     */
    public static int getLastP2Move() {
        return p2moves.get(p2moves.size() - 1);
    }

    /**
     * Päivittää siirtohistoriataulukot.
     *
     * @param player
     * @param ai
     */
    public void updatePlayerAndAiMoves(int player, int ai) {
        p1moves.add(player);
        p2moves.add(ai);
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
        int[] moves = new int[]{p1moves.get(p1moves.size() - 2), p1moves.get(p1moves.size() - 1)};
        return moves;
    }

    /**
     * Palauttaa viimeiset kaksi siirtoa tekoälyltä.
     *
     * @return
     */
    public static int[] getLastTwoAiMoves() {
        int[] moves = new int[]{p2moves.get(p2moves.size() - 2), p2moves.get(p2moves.size() - 1)};
        return moves;
    }

    /**
     * Palauttaa pelin tilastot oletusarvoisiksi.
     */
    public void resetStats() {
        p1moves.clear();
        p2moves.clear();
        roundStats = new int[]{0, 0, 0};
        round = 1;
    }

    /**
     * Tulostaa molempien pelaajien siirtohistorian.
     *
     */
    public void showMoveHistory() {
        System.out.println("Pelaaja: " + p1moves);
        System.out.println("Tekoäly: " + p2moves);
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
            for (Integer playermove : p1moves) {
                pw.print(playermove + " ");
            }
            pw.println();
            for (Integer aimove : p2moves) {
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
        System.out.println("");
        System.out.println(p1moves);
        System.out.println(p2moves);
    }
}
