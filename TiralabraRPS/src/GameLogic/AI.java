package GameLogic;

import java.util.*;

/**
 *
 * @author joopeti
 */
public class AI {
    private int[][] tulokset;
    private int[] pisteytys;
    private StringBuilder sb;
    private Hand kasi;
    private Random rnd;
    
    public AI(){
        sb = new StringBuilder();
        rnd = new Random();
    }
    
    public int chooseHand(int options){
        return rnd.nextInt(options);
    }
}
