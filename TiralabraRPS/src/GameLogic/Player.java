package GameLogic;

import AI.Strategy;
import UI.TextUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
