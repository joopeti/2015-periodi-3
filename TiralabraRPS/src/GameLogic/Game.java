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
    private Statistics st;
    Player p1;
    Player p2;

    /** Alustaa käyttöliittymän ja tekoälyn.
     */
    public Game() {
        this.ui = new TextUI();
        this.st = new Statistics();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        p1 = new StrategyHandler(0, 6, 0.95);
        p2 = new StrategyHandler(2, 3, 0.95);
        p1.addStrategy(new MarkovFirstOrder());
        p2.addStrategy(new MarkovFirstOrder());
        
        running = false;
    }

    /** Kysyy pelaajalta pelin asetukset ennen peliä ja laittaa pelin pyörimään sen jälkeen.
     */
    public void setSettings() {
//        this.gamemode = ui.askGameMode();
//        this.players = ui.askPlayers();
        running = true;
        Statistics.round = 1;
    }

    /** Ottaa pelaajan ja tekoälyn valitsemat kädet ja laskee kierroksen tuloksen ja näyttää sen käyttöliittymän kautta.
     * Sisältää tällä hetkellä täysin turhaan tekoälyn metodikustuja.
     *
     * @param player
     * @param AI
     */
    public void playRound(int player, int AI) {
        if (player == -1 || Statistics.round > 50) {
            running = false;
            ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[player], kadet[AI], tulos);
//            p1.printMetascores();
//            p1.printMetaChoices();
//            p1.printDecay();
        } else {
            checkResults(player, AI);
            st.updatePlayerAndAiMoves(player, AI);
            p1.afterRoundUpdate();
            p2.afterRoundUpdate();
//            st.showMoveHistory();
            st.roundIncrement();
        }
    }

    /** Laskee pisteet kierrokselta ja tallentaa ne pisteet-taulukkoon.
     * Valitsee oikean tuloksen näytettäväksi pelaajalle.
     * @param pelaaja
     * @param tekoaly
     */
    public void checkResults(int pelaaja, int tekoaly) {
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
            int a = p1.chooseHand(ui);
            int b = p2.chooseHand(ui);
            playRound(a, b);
        }
        st.printStatistics();
        st.saveGameStatsToFile();
    }
}
