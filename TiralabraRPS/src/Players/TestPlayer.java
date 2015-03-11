/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import UI.TextUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lukee aiemmin pelatuista peleistä pelaajan valinnat ja noudattaa niitä.
 */
public class TestPlayer extends Player {

    Scanner sc;
    String s;
    boolean manual;
    private int i;
    
    public TestPlayer() {
        try {
            sc = new Scanner(new File("pelaajasiirrot.txt"));
        } catch (FileNotFoundException ex) {
            manual = true;
            s = "11201020120012020202012002121120102012001202020201200212";
        }
    }

    /**
     * Lukee pelattujen siirtojen listalta seuraavan siirron, kunnes siirtoja ei enään ole.
     * @param ui
     * @return 
     */
    @Override
    public int chooseHand(TextUI ui) {
        i++;
        if(manual){
            return s.charAt(i);
        }
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else if(sc.hasNextLine()){
            sc.nextLine();
            return sc.nextInt();
        } else{
            return -1;
        }
    }
}
