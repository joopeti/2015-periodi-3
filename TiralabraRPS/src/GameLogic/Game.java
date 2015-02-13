package GameLogic;

import AI.RandomAi;
import AI.*;
import Utils.Hand;
import UI.*;

/**
 * Game Sisältää pelilogiikan, tarkistaa pisteet ja hoitaa kommunikaation
 * tekoälyn, pelaajan ja käyttöliittymän välillä.
 *
 */
public class Game {

    private Hand kasi;
    private Hand[] kadet;
    /**
     * Valittu pelimoodi. Ei vielä käytössä.
     */
    public int gamemode;
    /**
     * Valittu pelaajamäärä. Ei vielä käytössä.
     */
    public int players;
    /**
     * Kierrolaskuri.
     */
    public int turn;
    /**
     * Merkkijono joka kertoo pelaajalle edellisen kierroksen tuloksen.
     */
    public String tulos;
    /**
     * Kertoo onko peli vielä käynnissä, vai lopettiko pelaaja pelaamisen.
     */
    public boolean running;
    private TextUI ui;
    private Statistics st;
    Player p1;
    Player p2;
    private boolean debug;

    /**
     * Alustaa käyttöliittymän, tilastoluokan ja pelaajat (tekoäly tai ihminen).
     */
    public Game() {
        this.ui = new TextUI();
        this.st = new Statistics();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
//        p1 = new StrategyHandler(0, 3, 0.95, false);
//            p1.addStrategy(new MarkovSecondOrder());
//            p1.addStrategy(new MarkovFirstOrder());
//            p1.addStrategy(new StupidAi());
//            p1.addStrategy(new RandomAi());
//        p1 = new TestPlayer();
        p1 = new Player();

        p2 = new StrategyHandler(2, 3, 0.95, false);
            p2.addStrategy(new MarkovFirstOrder());
            p2.addStrategy(new MarkovSecondOrder());
            p2.addStrategy(new StupidAi());
        running = false;
    }

    /**
     * Kysyy pelaajalta pelin asetukset ennen peliä ja laittaa pelin pyörimään
     * sen jälkeen.
     */
    public void setSettings() {
//        this.gamemode = ui.askGameMode();
//        this.players = ui.askPlayers();
        running = true;
        debug = true;
        Statistics.round = 1;
    }

    /**
     * Ottaa pelaajan ja tekoälyn valitsemat kädet ja laskee kierroksen tuloksen
     * ja näyttää sen käyttöliittymän kautta. Sisältää tällä hetkellä täysin
     * turhaan tekoälyn metodikustuja.
     *
     * @param player
     * @param AI
     */
    public void playRound(int player, int AI) {
        if (player == -1 || Statistics.round > 60) {
            endGame();
        } else {
            checkResults(player, AI);
            if (debug) {
                printDebug(player, AI);
            }
            st.updatePlayerAndAiMoves(player, AI);
            p1.afterRoundUpdate();
            p2.afterRoundUpdate();
            st.roundIncrement();
        }
    }

    /**
     * Tarkistaa kuka voitti kierroksen ja päivittää tulokset tilastot-luokkaan.
     * Valitsee oikean tuloksen näytettäväksi pelaajalle.
     *
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

    /**
     * Käynnistää pelin. Aluksi kysytään asetukset, jonka jälkeen pelaaja 1:ltä ja
     * pelaaja 2:lta kysytään kädet ja annetaan ne playround-metodille.
     */
    public void start() {
        setSettings();
        while (running) {
            int a = p1.chooseHand(ui);
            int b = p2.chooseHand(ui);
            playRound(a, b);
        }
        st.printStatistics();
//        st.saveGameStatsToFile();
    }

    public void endGame() {
        running = false;
        ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[0], kadet[0], tulos);
        p1.printMetascores();
        p2.printMetascores();
    }

    public void printDebug(int player, int ai) {
        ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[player], kadet[ai], tulos);
//        p2.printMetascores();
//        st.showMoveHistory();
    }
}
