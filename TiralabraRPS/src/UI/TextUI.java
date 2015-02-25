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
    public String chooseHand(){
        System.out.println("----------------------");
        System.out.println("Valitse kätesi: ");
        System.out.println("(1)Kivi (2)Sakset (3)Paperi");
        System.out.println("(9)Näytä debug (0)lopeta peli");
        String option = sc.nextLine();
        return option;
    }
    
    /** Ottaa vastaan tiedot kierrokselta ja näyttää pelaajalle kierrosnumeron, tuloksen, oman ja tekoälyn käden ja kokonaistulokset.
     *
     * @param round
     * @param results
     * @param p1
     * @param p2
     * @param tulos
     * @return
     */
    public boolean showResults(int round, int[] results, Hand p1, Hand p2, String tulos){
        System.out.println("------ Round " + round + ". ------");
//        System.out.println("*" + historia.toString() + "*");
        System.out.println(p1.toString(1));
        System.out.println("");
        System.out.println(p2.toString(2));
        System.out.println("          " + tulos);
        System.out.println("Voitot: " + results[0] + ", Tasapelit: " + results[1] + ", Häviöt: " + results[2]);
        return true;
    }
    
    /** Palauttaa kokonaislukuna pelaajan valitseman pelitilan.
     * @return
     */
    public String askGameMode(){
        System.out.println("/----Kivi-Sakset-Paperi-----/");
        System.out.println("Valitse pelimoodi: ");
        System.out.println("(1) pelaaja vs tekoäly");
        System.out.println("(2) tekoälyavustettu peli");
        System.out.println("(3) pelaaja vs testipelaaja??");
        String option = sc.nextLine();
        return option;
    }
    
    /** Palauttaa kokonaislukuna pelaajan valitseman pelaajamäärän. Käyttämätön tällä hetkellä.
     *
     * @return
     */
    public String askDifficulty(){
        System.out.println("Valitse vaikeustaso: ");
        System.out.println("(1) Helppo");
        System.out.println("(2) Keskitaso");
        System.out.println("(3) Haastava");
        String option = sc.nextLine();
        return option;
    }
    
    public void errorMessage(){
        System.out.println("Valitsemasi numero ei ollut kelvollinen, valitse uudestaan.");
    }
    
    
}
