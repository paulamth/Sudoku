package com.example.sudoku.model.game.logik;

import com.example.sudoku.model.game.ui.Zelle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Loeser dient zum Loesen von Sudokufeldern
 * @author  Paula Matthies
 */
public class Loeser implements Loesen {


    // Quelle: https://www.youtube.com/watch?v=mcXc8Mva2bA&t=969s
    // Algorithmus leicht abgeändert
    /**
     * Prüft, ob das übergeben Spielfeld lösbar ist
     * @param feld das Spielfeld, das gelöst werden soll
     * @return true, wenn Spielfeld lösbar ist
     */
    public boolean loeseSudokufeld(Zelle[][] feld){
        Random random = new Random();
        int zahl;

        for(int r = 0; r < feld.length; r++){
            for(int s = 0; s < feld.length; s++){
                if(feld[r][s].getWert() == 0){
                    for(int i = 1; i <= feld.length; i++){
                        zahl = random.nextInt(feld.length)+1;
                        if(istRichtigePosition(feld, zahl, r, s)){
                            feld[r][s].setWert(zahl);
                            if (loeseSudokufeld(feld)){
                                return true;
                            }
                            else {
                                feld[r][s].setWert(0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prüft, ob die übergebene zahl in die Zelle gesetzt werden,
     * indem kontrolliert wird, ob dieselbe Zahl sich in der aktuellen Spalte, Reihe oder derselben Box befindet
     * @param feld das Spielfeld
     * @param zahl der Zahlenwert, der in die Zelle gestzt werden soll
     * @param reihe die Reihe der Zelle im Spielfeld
     * @param spalte die Spalte der Zelle im Spielfeld
     * @return true, wenn zahl an die aktuelle Positon im Feld gesetzt werden darf
     */
    private boolean istRichtigePosition(Zelle[][] feld, int zahl, int reihe, int spalte){
        return !zahlInReihe(feld, zahl, reihe) && !zahlInSpalte(feld, zahl, spalte) && !zahlInBox(feld, zahl, reihe, spalte);
    }

    /**
     * Prüft, ob sich bereits in derselben Reihe die Zahl befindet
     * @param feld das Spielfeld, das gelöst werden soll
     * @param zahl der Zahlenwert, der in die Zelle gesstzt werden soll
     * @param reihe die Reihe der Zelle im Spielfeld
     * @return true, wenn sich die Zahl in derselben Reihe befindet
     */
    public boolean zahlInReihe(Zelle[][] feld, int zahl, int reihe){
        for(int i = 0; i < feld.length; i++){
            if(feld[reihe][i].getWert() == zahl){
                return true;
            }
        }
        return false;
    }

    /**
     * Prüft, ob sich bereits in derselben Spalte die Zahl befindet
     * @param feld das Spielfeld, das gelöst werden soll
     * @param zahl der Zahlenwert, der in die Zelle gesstzt werden soll
     * @param spalte die Spalte der Zelle im Spielfeld
     * @return true, wenn sich die Spalte in derselben Spalte befindet
     */
    private  boolean zahlInSpalte(Zelle[][] feld, int zahl, int spalte){
        for(int i = 0; i < feld.length; i++){
            if(feld[i][spalte].getWert() == zahl){
                return true;
            }
        }
        return false;
    }

    /**
     * Prüft, ob sich bereits in derselben Box die Zahl befindet
     * @param feld das Spielfeld, das gelöst werden soll
     * @param zahl der Zahlenwert, der in die Zelle gesstzt werden soll
     * @param reihe die Reihe der Zelle im Spielfeld
     * @param spalte die Spalte der Zelle im Spielfeld
     * @return true, wenn sich die Zahl in derselben Box befindet
     */
    private  boolean zahlInBox(Zelle[][] feld, int zahl, int reihe, int spalte){
        int boxLaenge = (int) (Math.sqrt(feld.length));
        int boxReihe = reihe - reihe % boxLaenge;
        int boxSpalte = spalte - spalte % boxLaenge;

        for (int r = boxReihe; r < boxReihe + boxLaenge; r++) {
            for (int s = boxSpalte; s < boxSpalte + boxLaenge; s++) {
                if (feld[r][s].getWert() == zahl) {
                    return true;
                }
            }
        }
        return false;
    }
}
