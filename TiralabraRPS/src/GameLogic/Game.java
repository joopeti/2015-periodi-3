package GameLogic;

import UI.*;
import java.util.ArrayList;

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

    /** Pisteytysmatriisi, käytössä vain ylin rivi. 0: pelaajan voitot, 1: tasapelit, 2: tekoälyn voitot.
     */
    public int pisteet[][];

    /** Tulos pelaajan näkökulmasta. Annetaan käyttöliittymälle.
     */
    public String tulos;

    /** Kertoo onko peli vielä käynnissä, vai lopettiko pelaaja pelaamisen.
     */
    public boolean running;

    private TextUI ui;
    private AI ai;

    /** Alustaa käyttöliittymän ja tekoälyn.
     */
    public Game() {
        this.ui = new TextUI();
        this.ai = new AI();
        kadet = new Hand[]{kasi.Kivi, kasi.Sakset, kasi.Paperi};
        running = false;
    }

    /** Kysyy pelaajalta pelin asetukset ennen peliä ja laittaa pelin pyörimään sen jälkeen.
     */
    public void setSettings() {
//        this.gamemode = ui.askGameMode();
//        this.players = ui.askPlayers();
        pisteet = new int[3][3];
        running = true;
        turn = 1;
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
            ai.addToGameHistory(player, AI);
            ai.updateMatrices();
            ai.updateMetaScores();
//            ai.matrixDecay(0.9);
            ai.printMatrix();
            ai.printMetascores();
            ui.showResults(turn, pisteet, kadet[player], kadet[AI], tulos);
            turn++;
        }
    }

    /** Laskee pisteet kierrokselta ja tallentaa ne pisteet-taulukkoon.
     * Valitsee oikean tuloksen näytettäväksi pelaajalle.
     * @param pelaaja
     * @param tekoaly
     */
    public void checkWinners(int pelaaja, int tekoaly) {
        if (pelaaja == tekoaly) {
            pisteet[1][0] += 1;
            tulos = "TASAPELI";
        } else if ((pelaaja == 0 && tekoaly == 1) || (pelaaja == 1 && tekoaly == 2) || (pelaaja == 2 && tekoaly == 0)) {
            pisteet[0][0] += 1;
            tulos = "VOITIT!";
        } else {
            pisteet[2][0] += 1;
            tulos = "HÄVISIT!";
        }
    }

    /** Käynnistää pelin. 
     * Aluksi kysytään asetukset, jonka jälkeen pelaajalta ja tekoälyltä kysytään kädet ja annetaan ne playround-metodille.
     */
    public void start() {
        setSettings();
        while (running) {
            playRound((ui.askHands(turn) - 1), ai.chooseHand());
        }
//        showStatistics();
    }

    /** Muodostaa merkkijonon pelatuista käsistä. 
     * Voitaisiin käyttää merkkijonoa hyödyntävissä hahmontunnistusalgoritmeissa.
     * @param pelaaja
     * @param tekoaly
     */
    public void createStringFromRounds(int pelaaja, int tekoaly) {
//        pelihistoria += kirjaimet[tekoaly];
//        pelihistoria += kirjaimet[pelaaja];
    }
}
