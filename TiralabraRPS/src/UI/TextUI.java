package UI;
import GameLogic.Hand;
import java.util.Scanner;
/**
 *
 * @author joopeti
 */
public class TextUI {
    private Scanner sc;
    private Hand hand;
    
    public TextUI(){
        sc = new Scanner(System.in);
        
    }
    
    public int askHands(int round){
        System.out.println("/***********************/");
        System.out.println("Valitse kätesi: ");
        System.out.println("(1)Kivi");
        System.out.println("(2)Sakset");
        System.out.println("(3)Paperi");
        int option = sc.nextInt();
        return option;
    }
    
    public boolean showResults(int round, int[][] results, Hand player, Hand ai){
        System.out.println("/****** Round " + round + ". *******/");
        System.out.println("Tulokset: ");
        System.out.println("Sinun kätesi: " + player);
        System.out.println("Tekoälyn käsi: " + ai);
        System.out.println("Voitot: " + results[0][0] + ", Tasapelit: " + results[1][0] + ", Häviöt: " + results[2][0]);
        return true;
    }
    
    public int askGameMode(){
        System.out.println("/************************/");
        System.out.println("Valitse pelimoodi: ");
        System.out.println("(1)Pelaaja vs Pelaaja");
        System.out.println("(2)Pelaaja vs Tekoäly");
        System.out.println("(3)Tekoäly vastaan Tekoäly");
        int option = sc.nextInt();
        return option;
    }
    
    public int askPlayers(){
        System.out.println("Valitse pelaajamäärä: ");
        System.out.println("(1)");
        System.out.println("(2)");
        System.out.println("(3)");
        System.out.println("/************************/");
        int option = sc.nextInt();
        return option;
    }
    
    
    
    public void drawTurn(){
        
    }
    
}
