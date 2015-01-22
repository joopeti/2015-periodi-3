/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import UI.*;

/**Hoitaa mahdollisesti kommunikoinnin pelilogiikan ja käyttöliittymän välillä.
 * Tällä hetkellä ei käytössä.
 * @author joopeti
 */
public class Controller {
    private Game game;
    private TextUI ui;
    
    public Controller(Game game, TextUI ui){
        this.game = game;
        this.ui = ui;
    }
    
}
