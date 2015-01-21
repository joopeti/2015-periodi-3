package GameLogic;

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 *
 * @author joopeti TODO: 1.) Metastrategies and their rating 2.) Decaying points
 * 3.) Different difficulty levels
 */
public class AI {

    private ArrayList<Integer> historia;
    private int matriisi[][];
    private Random rnd;
    private char[] kirjaimet = new char[]{'K', 'S', 'P'};

    public AI() {
        rnd = new Random();
        matriisi = new int[3][3];
        historia = new ArrayList();
        setMatrix();
    }

    public int chooseHand() {
        if (historia.size() < 2) {
            return rnd.nextInt(3);
        } else {
            int rivi[] = matriisi[historia.get(historia.size() - 1)];
            if (rivi[0] > rivi[1] && rivi[0] > rivi[2]) {
                return 2;
            } else if (rivi[1] > rivi[2]) {
                return 0;
            } else if (rivi[1] < rivi[2]) {
                return 1;
            } else {
                return rnd.nextInt(3);
            }

        }
    }

    public void updateMatrix() {
        if (historia.size() >= 2) {
            int viimeinen = historia.get(historia.size() - 1);
            int toisiksiviimeinen = historia.get(historia.size() - 2);
            matriisi[toisiksiviimeinen][viimeinen]++;
        }
    }

    public void setMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriisi[i][j] = 0;
            }
        }
    }
    
    public void addToGameHistory(int player){
        historia.add(player);
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

}
