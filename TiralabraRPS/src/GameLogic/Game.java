package GameLogic;

import UI.*;

/**
 *
 * @author joopeti
 */
public class Game {

    private int gamemode;
    private int players;
    private int turn;
    private int pisteet[][];
    private boolean running;
    private TextUI ui;
    private AI ai;
    private Hand kasi;
    private Hand[] kadet;

    public Game() {
        this.ui = new TextUI();
        this.ai = new AI();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        running = false;
    }

    public void setSettings() {
        this.gamemode = ui.askGameMode();
        this.players = ui.askPlayers();
        pisteet = new int[3][9999];
        running = true;
        turn = 1;
    }
    
    public void playRound(){
        int playerSelection = ui.askHands(turn) - 1;
        int AISelection = ai.chooseHand(3);
        checkWinners(playerSelection, AISelection);
        running = ui.showResults(turn, pisteet, kadet[playerSelection], kadet[AISelection]);
        turn++;
    }
    
    public void checkWinners(int pelaaja, int tekoaly){
        if(pelaaja == tekoaly){
            pisteet[1][0] += 1;
        } else if((pelaaja == 0 && tekoaly == 1) || (pelaaja == 1 && tekoaly == 2) || (pelaaja == 2 && tekoaly == 0)){
            pisteet[0][0] += 1;
        } else{
            pisteet[2][0] += 1;
        }
    }

    public void start() {
        setSettings();
        while (running) {
            playRound();
        }
    }
}
