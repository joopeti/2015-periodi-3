package GameLogic;

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 *
 * @author joopeti
 */
public class AI {

    private int[][] tulokset;
    private StringBuilder sb;
    private Hand kasi;
    private Random rnd;

    public AI() {
        sb = new StringBuilder();
        rnd = new Random();
    }

    public int chooseHand(ArrayList<Integer> historia, int[][] matriisi) {
        if(historia.size() < 2){
            return rnd.nextInt(3);
        } else{
            int rivi[] = matriisi[historia.get(historia.size() -1 )];
            if(rivi[0] > rivi[1] && rivi[0] > rivi[2]){
                return 2;
            } else if(rivi[1] > rivi[2]){
                return 0;
            } else if(rivi[1] < rivi[2]){
                return 1;
            } else {
                return rnd.nextInt(3);
            }
            
        }

    }
    
    

   
}
