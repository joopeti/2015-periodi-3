package GameLogic;

import AI.strategies.*;
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
     * Valittu pelimoodi. 0 = normaali peli tekoälyä vastaan. 1 = tekoäly
     * näyttää valintansa etukäteen, voidaan käyttää tekoäly vs tekoäly
     * peleissä.
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

    private TextUI ui;
    private Statistics st;
    Player p1;
    Player p2;
    public boolean running;
    private boolean debug;
    private boolean print;
    private int roundLimit;
    int p1choice;
    int p2choice;

    /**
     * Alustaa käyttöliittymän, tilastoluokan ja pelaajat (tekoäly tai ihminen).
     */
    public Game() {
        this.ui = new TextUI();
        this.st = new Statistics();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        p1 = new Player();
        debug = false;
        print = true;
    }

    /**
     * Kysyy pelaajalta pelin asetukset ennen peliä ja laittaa pelin pyörimään
     * sen jälkeen.
     */
    public void setSettings() {
        setGameMode();
        running = true;
        roundLimit = 200;
        Statistics.round = 1;
    }

    /**
     * Kysyy pelaajalta pelimoodin ja käyttää sitä pelin asetusten säätämiseen.
     * 1 = ihminen vs tekoäly 2 = tekoäly vs tekoäly (tekoäly näyttää minkä
     * siirron aikoo tehdä etukäteen) 3 = ihminen vs testipelaaja
     */
    public void setGameMode() {
        OUTER:
        while (true) {
            String i = ui.askGameMode();
            switch (i) {
                case "1":
                    gamemode = 1;
                    print = true;
                    setDifficulty();
                    break OUTER;
                case "2":
                    gamemode = 2;
                    p2 = new Player();
                    p1 = new StrategyHandler(0, 6, 0.95, true);
                    p1.addStrategy(new MarkovFirstOrder());
                    p1.addStrategy(new MarkovSecondOrder());
                    p1.addStrategy(new PatternMatching(5));
                    p1.addStrategy(new PatternMatchingPlayer(5));
                    p1.addStrategy(new StupidAi());
                    debug = true;
                    break OUTER;
                case "3":
                    gamemode = 3;
                    p2 = new TestPlayer();
                    break OUTER;
                default:
                    ui.errorMessage();
                    break;
            }
        }
    }

    /**
     * Kysyy pelaajalta vaikeustason ja säätää tekoälyn sen mukaan. 1 = helppo,
     * 2 = keskitaso, 3 = haastava
     */
    public void setDifficulty() {
        OUTER:
        while (true) {
            String i = ui.askDifficulty();
            switch (i) {
                case "1":
                    p2 = new StrategyHandler(2, 2, 0.95, false);
                    p2.addStrategy(new StupidAi());
                    p2.addStrategy(new PatternMatching(5));
                    break OUTER;
                case "2":
                    p2 = new StrategyHandler(2, 6, 0.95, true);
                    p2.addStrategy(new MarkovSecondOrder());
//                    p2.addStrategy(new PatternMatching(5));
                    break OUTER;
                case "3":
                    p2 = new StrategyHandler(2, 2, 0.95, true);
//                    p2.addStrategy(new MarkovFirstOrder());
//                    p2.addStrategy(new MarkovSecondOrder());
                    p2.addStrategy(new StupidAi());
                    p2.addStrategy(new PatternMatching(4));
                    break OUTER;
                default:
                    ui.errorMessage();
                    break;
            }
        }
    }

    /**
     * Ottaa pelaajan ja tekoälyn valitsemat kädet ja laskee kierroksen tuloksen
     * ja näyttää sen käyttöliittymän kautta.
     *
     */
    public void playRound() {
        askPlayerHands();
        checkInput();
        if (running) {
            checkResults(p1choice, p2choice);
            checkPrints();
            st.updatePlayerAndAiMoves(p1choice, p2choice);
            p1.afterRoundUpdate();
            p2.afterRoundUpdate();
            st.roundIncrement();
        }
    }

    public void checkPrints() {
        if (print) {
            ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[p1choice], kadet[p2choice], tulos);
        }
        if (debug) {
            printDebug();
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
     * Käynnistää pelin. Aluksi kysytään asetukset, jonka jälkeen pelaaja 1:ltä
     * ja pelaaja 2:lta kysytään kädet ja annetaan ne playround-metodille.
     */
    public void start() {
        setSettings();
        while (Statistics.round < roundLimit && running) {
            playRound();
        }
        endGame();
        st.saveGameStatsToFile();
    }

    /**
     * Kysyy pelaajilta kädet ja tallentaa ne.
     */
    public void askPlayerHands() {
        p1choice = p1.chooseHand(ui);
        if (gamemode == 2) {
            System.out.println("Tekoälyn valinta: " + kadet[p1choice]);
        }
        p2choice = p2.chooseHand(ui);
    }

    /**
     * Tarkistaa pelaajien valinnat erikoistapausten varalta. "-1" lopettaa
     * pelin ja "9" näyttää tekoälyn valintamatriisit.
     */
    public void checkInput() {
        while (p1choice == 9 || p2choice == 9) {
            debug = !debug;
            askPlayerHands();                        //!!!!!!!!!!!!!!!!
        }
        if (p1choice == -1 || p2choice == -1) {
            running = false;
        }
    }

    /**
     * Lopettaa pelin ja näyttää lopputuloksen.
     */
    public void endGame() {
        running = false;
//        ui.showResults(Statistics.round, st.getRoundStatistics(), kadet[0], kadet[0], tulos);
        System.out.println("");
        System.out.println("****** TULOKSET ******");
        printDebug();
        st.printStatistics();
    }

    public void printDebug() {
        p1.printMetascores();
        p2.printMetascores();
//        st.showMoveHistory();
    }
}
