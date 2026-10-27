package com.example.sudoku;

import static org.junit.Assert.assertTrue;

import com.example.sudoku.model.game.logik.Loeser;
import com.example.sudoku.model.game.ui.Zelle;

import org.junit.Before;
import org.junit.Test;

public class LoeserTest {

    Loeser loeser;

    @Before
    public void setUp() throws Exception{
        loeser = new Loeser();
    }


    @Test
    public void returnTrueWennZahlMehrfachInReihe() {

        Zelle[][] feld = new Zelle[4][4];
        for(int r = 0; r < feld.length; r++) {
            for (int s = 0; s < feld.length; s++) {
                feld[r][s] = new Zelle();
            }
        }
        feld[0][0].setWert(1);
        feld[0][1].setWert(4);
        feld[0][2].setWert(2);
        feld[0][3].setWert(3);
        feld[1][0].setWert(2);
        feld[1][1].setWert(3);
        feld[1][2].setWert(4);
        feld[1][3].setWert(1);
        feld[2][0].setWert(4);
        feld[2][1].setWert(1);
        feld[2][2].setWert(3);
        feld[2][3].setWert(2);
        feld[3][0].setWert(3); // erste 3 in letzter Zeile
        feld[3][1].setWert(2);
        feld[3][2].setWert(1);
        feld[3][3].setWert(3); // zweite 3 in letzter Zeile
        boolean doppelteZahl = loeser.zahlInReihe(feld,3,3);
        assertTrue(doppelteZahl);
    }

    @Test
    public void returnTrueWennZahlNichtMehrfachInReihe() {

        Zelle[][] feld = new Zelle[4][4];
        for(int r = 0; r < feld.length; r++) {
            for (int s = 0; s < feld.length; s++) {
                feld[r][s] = new Zelle();
            }
        }
        feld[0][0].setWert(1);
        feld[0][1].setWert(4);
        feld[0][2].setWert(2);
        feld[0][3].setWert(3);
        feld[1][0].setWert(2);
        feld[1][1].setWert(3);
        feld[1][2].setWert(4);
        feld[1][3].setWert(1);
        feld[2][0].setWert(4);
        feld[2][1].setWert(1);
        feld[2][2].setWert(3);
        feld[2][3].setWert(2);
        feld[3][0].setWert(3); // erste 3 in letzter Zeile
        feld[3][1].setWert(2);
        feld[3][2].setWert(1);
        feld[3][3].setWert(4); // durch richtige zahl ersetzt
        boolean doppelteZahl = loeser.zahlInReihe(feld,3,3);
        assertTrue(doppelteZahl);
    }

}
