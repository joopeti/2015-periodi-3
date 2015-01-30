package GameLogic;

import AI.Strategy;
import AI.StrategyHandler;
import UI.TextUI;
import java.util.Scanner;

/**
 *
 * @author joopeti
 */
public class Player {
    String name;
    boolean bot; 
    
    public int chooseHand(TextUI ui){
        return ui.chooseHand() - 1;
    }
    public void setName(String name){
        this.name = name;
    }
    public void afterRoundUpdate(){
    }
    public void addStrategy(Strategy s){
    }
    public void printMetascores(){
    }
}
