package Players;

import AI.strategies.Strategy;
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

    public int chooseHand(TextUI ui) {
        while (true) {
            String i = ui.chooseHand();
            switch (i) {
                case "1":
                    return 0;
                case "2":
                    return 1;
                case "3":
                    return 2;
                case "0":
                    return -1;
                case "9":
                    return 9;
                default:
                    ui.errorMessage();
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void afterRoundUpdate() {
    }

    public void addStrategy(Strategy s) {
    }

    public void printMetascores() {
    }
}
