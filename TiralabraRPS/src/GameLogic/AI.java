package GameLogic;

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Tekoäly joka tallentaa tiedot pelaajan valinnoista ja omista valinnoista.
 * Muodostaa menneistä valinnoista matriisit ja laskee niiden perusteella
 * todennäköisimmän siirron. Pitää yllä myös metastrategialistaa, joka sisältää
 * kaikki päästrategian variaatiot. Pisteyttää metastrategioita tulosten
 * perusteella ja valitsee parhaiten pärjänneen strategian ehdottaman käden.
 */
public class AI {

    /**
     * Sisältää pelaajan kaikki edelliset valinnat.
     */
    public ArrayList<Integer> playermoves;

    /**
     * Sisältää tekoälyn kaikki edelliset valinnat.
     */
    public ArrayList<Integer> aimoves;

    /**
     * Pelihistoriasta luotu matriisi, jota algoritmi käyttää pelaajan käden
     * ennakoinnissa.
     */
    public double[][] playermovematrix;

    /**
     * Tekoälyn omasta pelihistoriasta luotu matriisi, jota algoritmi käyttää
     * kolmessa metastrategiassa.
     */
    public double omamatriisi[][];

    /**
     * Metastrategioiden pistetyslista.
     */
    public double[] metapoints;

    /**
     * Metastrategioiden ehdottamat kädet.
     */
    public int[] metastrat;

    /**
     * Antaa indeksin perusteella kokonaisluvun (=käden), joka voittaa indeksin
     * (=annetun käden).
     */
    public int[] wins;

    /**
     * Parhaan metastrategian indeksi.
     */
    public int best;

    public Random rnd;
    private char[] kirjaimet = new char[]{'K', 'S', 'P'};

    /**
     * Tekoälyn konstruktori. Luo tarvittavat matriisit ja listat sekä alustaa
     * ne.
     */
    public AI() {
        rnd = new Random();
        playermovematrix = new double[3][3];
        omamatriisi = new double[3][3];
        metapoints = new double[7];
        metastrat = new int[7];
        wins = new int[3];
        best = 0;
        initializeArrays();
    }

    /**
     * Ennakoi pelaajan seuraavan siirron ja palauttaa parhaiten menestyneimmän
     * metastrategian ehdottaman käden. Jos pelihistoria on liian lyhyt, metodi
     * palauttaa satunnaisen käden.
     */
    public int chooseHand() {
        if (playermoves.size() < 2) {
            int valinta = rnd.nextInt(3);
            aimoves.add(valinta);
            return valinta;
        } else {
            int predict = predictMove(playermovematrix, playermoves.get(playermoves.size() - 1));
            int counterpredict = predictMove(omamatriisi, aimoves.get(aimoves.size() - 1));
            updateMetaHands(predict, counterpredict);
            checkBestMetaStrategy();
            return metastrat[best];
        }
    }

    /**
     * Metodi tarkistaa annetusta matriisista suurimman luvun annetulta riviltä
     * (= lastmove) ja palauttaa sen. Jos suurinta lukua ei ole, palautetaan
     * satunnainen luku.
     */
    public int predictMove(double[][] matrice, int lastmove) {
        double rivi[] = matrice[lastmove];
        if (rivi[0] > rivi[1] && rivi[0] > rivi[2]) {
            return 0;
        } else if (rivi[1] > rivi[2]) {
            return 1;
        } else if (rivi[1] < rivi[2]) {
            return 2;
        } else {
            return rnd.nextInt(3);
        }

    }

    /**
     * Päivittää metastrategioiden "kädet" annettujen ennustusten perusteella.
     * Ensimmäiset kolme strategiaa tarkastelevat vain pelaajan historiaa ja
     * jälkimmäiset kolme tekoälyn omaa pelihistoriaa. Viimeinen strategia on
     * satunnainen.
     */
    public void updateMetaHands(int prediction, int counter) {    // oletus: vastustaja valitsee kiven.
        metastrat[0] = wins[prediction];         // oletetaan, että pelaajan valinta on kivi, valitaan tällöin paperi
        metastrat[1] = prediction;                  // oletetaan, että pelaaja valitseekin sakset, valitaan tällöin kivi
        metastrat[2] = wins[metastrat[0]];       // oletetaan, että pelaaja valitseekin paperin, valitaan tällöin sakset
        metastrat[3] = wins[wins[counter]];
        metastrat[4] = wins[wins[metastrat[3]]];
        metastrat[5] = wins[wins[metastrat[4]]];
        metastrat[6] = rnd.nextInt(3);
    }

    /**
     * Päivittää metastrategioiden pisteet niiden suoritusten mukaan. Häviöstä
     * strategia menettää yhden pisteen ja voitosta se saa yhden. Tasapelistä
     * saa hieman lisäpisteitä. Lopuksi kaikki pisteet kerrotaan vakiolla, jotta
     * tekoäly "unohtaisi" vanhat tulokset.
     */
    public void updateMetaScores() {
        if (playermoves.size() > 2) {
            for (int i = 0; i < 7; i++) {
                if (metastrat[i] == wins[playermoves.get(playermoves.size() - 1)]) {
                    metapoints[i] += 1.0;
                } else if (playermoves.get(playermoves.size() - 1) == wins[metastrat[i]]) {
                    metapoints[i] -= 1.0;
                } else {
                    metapoints[i] += 0.1;
                }
                metapoints[i] *= 1.0;
            }
        }
    }

    /**
     * Tarkastelee metastrategioiden pisteitä ja tallentaa sen strategian
     * indeksin, jolla on korkein pisteytys muuttujaan best.
     */
    public void checkBestMetaStrategy() {
        double d = metapoints[0];
        best = 0;
        for (int i = 1; i < 7; i++) {
            if (metapoints[i] > d) {
                best = i;
                d = metapoints[i];
            }
        }
    }

    /**
     * Päivittää pelaajan ja tekoälyn pelihistoriamatriisit pelihistorian
     * perusteella.
     */
    public void updateMatrices() {
        if (playermoves.size() >= 2) {
            int viimeinen = playermoves.get(playermoves.size() - 1);
            int toisiksiviimeinen = playermoves.get(playermoves.size() - 2);
            playermovematrix[toisiksiviimeinen][viimeinen]++;
        }
        if (aimoves.size() >= 2) {
            int omaviimeinen = aimoves.get(aimoves.size() - 1);
            int omatokavika = aimoves.get(aimoves.size() - 2);
            omamatriisi[omatokavika][omaviimeinen]++;
        }
    }

    /**
     * Kertoo kaikki pelaajamatriisin luvut annetulla
     *
     * @param d:lla
     */
    public void matrixDecay(double d) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playermovematrix[i][j] *= d;
            }
        }
    }

    /**
     * Alustaa matriisit.
     */
    public final void setMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playermovematrix[i][j] = 0;
                omamatriisi[i][j] = 0;
            }
        }
    }

    /**
     * Tallentaa
     *
     * @param player:in pelaajahistoriaan ja
     * @param oma:n tekoälyn pelihistoriaan.
     */
    public void addToGameHistory(int player, int oma) {
        playermoves.add(player);
        aimoves.add(oma);
    }

    /**
     * Tulostaa pelaajamatriisin debuggausta varten.
     */
    public void printMatrix() {
        System.out.println("   K   S   P");
        for (int i = 0; i < 3; i++) {
            System.out.print(kirjaimet[i] + ":");
            for (int j = 0; j < 3; j++) {
                System.out.printf("%2.1f", playermovematrix[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    /**
     * Tulostaa Metastrategioiden pisteytyksen ja valitseman käden.
     */
    public void printMetascores() {
        System.out.print("[");
        for (int i = 0; i < 7; i++) {
            System.out.printf("%2.1f", metapoints[i]);
            System.out.print(": ");
        }
        System.out.println("]");
        System.out.print("[");
        for (int i = 0; i < 7; i++) {
            System.out.print(metastrat[i] + ", ");
        }
        System.out.println("]");
        System.out.println("best: " + best);
    }

    /**
     * Alustaa tarvittavat listat ja arrayListat sekä voittolistan.
     *
     */
    public void initializeArrays() {
        playermoves = new ArrayList();
        aimoves = new ArrayList();
        setMatrix();
        for (int i = 0; i < 7; i++) {
            metapoints[i] = 0;
            metastrat[i] = 0;
        }
        wins[0] = 2;
        wins[1] = 0;
        wins[2] = 1;
    }

}
