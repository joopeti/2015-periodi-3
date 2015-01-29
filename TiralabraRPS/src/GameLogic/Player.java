package GameLogic;

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
        return ui.chooseHand();
    }
    public void setName(String name){
        this.name = name;
    }
    
}
