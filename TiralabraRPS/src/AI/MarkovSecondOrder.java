package AI;

import GameLogic.Statistics;
import java.util.Random;

/**
 * Muodostaa pelaajan ja tekoälyn historiasta matriisin
 *
 * @author joopeti
 */
public class MarkovSecondOrder extends Strategy {

    private double[][][] p1matrix;
    private double[][][] p2matrix;
    private Random rnd;

    public MarkovSecondOrder() {
        p1matrix = new double[3][3][3];
        p2matrix = new double[3][3][3];
        setMatrices();
        rnd = new Random();
    }

    @Override
    public int predictPlayerMove() {
        double rivi[] = p1matrix[Statistics.getLastP1Move()][Statistics.getLastP2Move()];
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

    @Override
    public int predictAiMove() {
        double rivi[] = p2matrix[Statistics.getLastP1Move()][Statistics.getLastP2Move()];
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
     * Päivittää matriisit.
     *
     * @param decay
     */
    @Override
    public void updateModels(double decay) {
        int[] a = Statistics.getLastTwoPlayerMoves();
        int[] b = Statistics.getLastTwoAiMoves();
        p1matrix[a[0]][b[0]][a[1]]++;
        p2matrix[a[0]][b[0]][b[1]]++;
    }

    /**
     * Kertoo kaikki pelaajamatriisin luvut annetulla
     *
     * @param d:lla
     */
    public void matrixDecay(double d) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    p1matrix[i][j][k] *= d;
                    p2matrix[i][j][k] *= d;
                }
            }
        }
    }

    /**
     * Alustaa matriisit.
     */
    public final void setMatrices() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    p1matrix[i][j][k] = 0;
                    p2matrix[i][j][k] = 0;
                }
            }
        }
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 3;
    }

}
