package AI;

import java.util.*;
import GameLogic.Statistics;

/**
 * Muodostaa menneistä valinnoista matriisit ja laskee niiden perusteella
 * todennäköisimmän siirron.
 */
public class MarkovFirstOrder extends Strategy {

    /**
     * Pelihistoriasta luotu matriisi, jota algoritmi käyttää pelaajan käden
     * ennakoinnissa.
     */
    public double[][] playerFirstOrderMatrix;
    /**
     * Tekoälyn omasta pelihistoriasta luotu matriisi, jota algoritmi käyttää
     * kolmessa metastrategiassa.
     */
    public double[][] aiFirstOrderMatrix;

    public Random rnd;

    /**
     * Tekoälyn konstruktori. Luo tarvittavat matriisit ja listat sekä alustaa
     * ne.
     */
    public MarkovFirstOrder(){
        super();
        rnd = new Random();
        playerFirstOrderMatrix = new double[3][3];
        aiFirstOrderMatrix = new double[3][3];

        setMatrix();
    }

    /**
     * Metodi tarkistaa annetusta matriisista suurimman luvun annetulta riviltä
     * (= lastmove) ja palauttaa sen. Jos suurinta lukua ei ole, palautetaan
     * satunnainen luku.
     *
     * @return
     */
    @Override
    public int predictPlayerMove() {
        double rivi[] = playerFirstOrderMatrix[Statistics.getLastPlayerMove()];
        if (rivi[0] > rivi[1] && rivi[0] > rivi[2]) {
            return 0;
        } else if (rivi[1] > rivi[2]) {
            return 1;
        } else if (rivi[1] < rivi[2]) {
            return 2;
        } else {
            return rnd.nextInt(3);
        }
    }

    /**
     * Metodi tarkistaa annetusta matriisista suurimman luvun annetulta riviltä
     * (= lastmove) ja palauttaa sen. Jos suurinta lukua ei ole, palautetaan
     * satunnainen luku.
     *
     * @return
     */
    @Override
    public int predictAiMove() {
        printMatrix();
        double rivi[] = aiFirstOrderMatrix[Statistics.getLastAiMove()];
        if (rivi[0] > rivi[1] && rivi[0] > rivi[2]) {
            return 0;
        } else if (rivi[1] > rivi[2]) {
            return 1;
        } else if (rivi[1] < rivi[2]) {
            return 2;
        } else {
            return rnd.nextInt(3);
        }
    }

    /**
     * Päivittää pelaajan ja tekoälyn pelihistoriamatriisit pelihistorian
     * perusteella. Strategyhandleri säätää decayta tuloksen mukaan.
     * @param decay
     */
    @Override
    public void updateModels(double decay) {
        int[] pmoves = Statistics.getLastTwoPlayerMoves();
        int[] aimoves = Statistics.getLastTwoAiMoves();
        if (Statistics.round > 2) {
            playerFirstOrderMatrix[pmoves[0]][pmoves[1]]++;
            aiFirstOrderMatrix[aimoves[0]][aimoves[1]]++;
        }
        matrixDecay(decay);
    }

    /**
     * Kertoo kaikki pelaajamatriisin luvut annetulla
     *
     * @param d:lla
     */
    public void matrixDecay(double d) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playerFirstOrderMatrix[i][j] *= d;
            }
        }
    }

    /**
     * Alustaa matriisit.
     */
    public final void setMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playerFirstOrderMatrix[i][j] = 0;
                aiFirstOrderMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Tulostaa pelaajamatriisin debuggausta varten.
     */
    public void printMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%2.1f", playerFirstOrderMatrix[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 3;
    }
}