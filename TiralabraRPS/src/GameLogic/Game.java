package GameLogic;

import AI.*;
import Utils.Hand;
import UI.*;

/**
 * Game Sisältää pelilogiikan, tarkistaa pisteet ja hoitaa kommunikaation tekoälyn, pelaajan ja käyttöliittymän välillä.
 * 
 */
public class Game {

    private Hand kasi;
    private Hand[] kadet;
    /** Valittu pelimoodi. Ei vielä käytössä.
     */
    public int gamemode;
    /** Valittu pelaajamäärä. Ei vielä käytössä.
     */
    public int players;
    /** Kierrolaskuri.
     */
    public int turn;
    /** Merkkijono joka kertoo pelaajalle edellisen kierroksen tuloksen.
     */
    public String tulos;
    /** Kertoo onko peli vielä käynnissä, vai lopettiko pelaaja pelaamisen.
     */
    public boolean running;
    private TextUI ui;
    private StrategyHandler ai;
    private Statistics st;

    /** Alustaa käyttöliittymän ja tekoälyn.
     */
    public Game() {
        this.ui = new TextUI();
        this.ai = new StrategyHandler();
        this.st = new Statistics();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        running = false;
    }

    /** Kysyy pelaajalta pelin asetukset ennen peliä ja laittaa pelin pyörimään sen jälkeen.
     */
    public void setSettings() {
//        this.gamemode = ui.askGameMode();
//        this.players = ui.askPlayers();
        running = true;
        ai.addStrategy(new MarkovFirstOrder());
        Statistics.round = 1;
    }

    /** Ottaa pelaajan ja tekoälyn valitsemat kädet ja laskee kierroksen tuloksen ja näyttää sen käyttöliittymän kautta.
     * Sisältää tällä hetkellä täysin turhaan tekoälyn metodikustuja.
     *
     * @param player
     * @param AI
     */
    public void playRound(int player, int AI) {
        if (player == -1) {
            running = false;
        } else {
            checkWinners(player, AI);
            st.updatePlayerAndAiMoves(player, AI);
            ai.updateMetaScores();
            ai.updateMetaChoices();
//            ai.printMetascores();
//            ai.printMetaChoices();
//            ai.printDecay();
            st.showMoveHistory();
            ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[player], kadet[AI], tulos);
            st.roundIncrement();
            ai.updateStrategiesAfterRound();
        }
    }

    /** Laskee pisteet kierrokselta ja tallentaa ne pisteet-taulukkoon.
     * Valitsee oikean tuloksen näytettäväksi pelaajalle.
     * @param pelaaja
     * @param tekoaly
     */
    public void checkWinners(int pelaaja, int tekoaly) {
        if (pelaaja == tekoaly) {
            st.updateRoundStatistics(1);
            tulos = "TASAPELI";
        } else if ((pelaaja == 0 && tekoaly == 1) || (pelaaja == 1 && tekoaly == 2) || (pelaaja == 2 && tekoaly == 0)) {
            st.updateRoundStatistics(0);
            tulos = "VOITIT!";
        } else {
            st.updateRoundStatistics(2);
            tulos = "HÄVISIT!";
        }
    }

    /** Käynnistää pelin. 
     * Aluksi kysytään asetukset, jonka jälkeen pelaajalta ja tekoälyltä kysytään kädet ja annetaan ne playround-metodille.
     */
    public void start() {
        setSettings();
        while (running) {
            int choice = ui.chooseHand();
                
            playRound(choice - 1, ai.chooseHand(ui));
        }
        st.printStatistics();
        st.saveGameStatsToFile();
    }
}
