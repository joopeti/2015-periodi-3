package UI;
import Utils.Hand;
import java.util.Scanner;

/** Tekstikäyttöliittymä Kivi-Sakset-Paperi-pelille.
 * @author joopeti
 */
public class TextUI {
    private Scanner sc;
    private Hand hand;
    
    public TextUI(){
        sc = new Scanner(System.in);
    }
    
    /**
     * @return
     */
    public int chooseHand(){
        System.out.println("/***********************/");
        System.out.println("Valitse kätesi: ");
        System.out.println("(1)Kivi (2)Sakset (3)Paperi (0)Lopeta peli");
        int option = sc.nextInt();
        return option;
    }
    
    /** Ottaa vastaan tiedot kierrokselta ja näyttää pelaajalle kierrosnumeron, tuloksen, oman ja tekoälyn käden ja kokonaistulokset.
     *
     * @param round
     * @param results
     * @param player
     * @param ai
     * @param tulos
     * @return
     */
    public boolean showResults(int round, int[] results, Hand player, Hand ai, String tulos){
        System.out.println("/****** Round " + round + ". *******/");
//        System.out.println("*" + historia.toString() + "*");
        System.out.println(tulos);
        System.out.println(player + " VS " + ai);
        System.out.println("Voitot: " + results[0] + ", Tasapelit: " + results[1] + ", Häviöt: " + results[2]);
        return true;
    }
    
    /** Palauttaa kokonaislukuna pelaajan valitseman pelitilan.
     * @return
     */
    public int askGameMode(){
        System.out.println("/************************/");
        System.out.println("Valitse pelimoodi: ");
        System.out.println("(1)Pelaaja vs Pelaaja");
        System.out.println("(2)Pelaaja vs Tekoäly");
        System.out.println("(3)Tekoäly vastaan Tekoäly");
        int option = sc.nextInt();
        return option;
    }
    
    /** Palauttaa kokonaislukuna pelaajan valitseman pelaajamäärän. Käyttämätön tällä hetkellä.
     *
     * @return
     */
    public int askPlayers(){
        System.out.println("Valitse pelaajamäärä: ");
        System.out.println("(1)");
        System.out.println("(2)");
        System.out.println("(3)");
        System.out.println("/************************/");
        int option = sc.nextInt();
        return option;
    }
    
    public void errorMessage(){
        System.out.println("Valitsemasi numero ei ollut kelvollinen, valitse uudestaan.");
    }
    
    
}
