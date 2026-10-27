package com.example.sudoku.model.game.ui;

import java.util.ArrayList;

/**
 * Klasse der Zelle, aus denen das Spielfeld aufgebaut ist
 * @author  Paula Matthies
 */
public class Zelle {

    private int wert;
    private ArrayList<Integer> notizen;
    private boolean aufgedeckt;
    private int falscheZahl;

    public Zelle(){
        this.wert = 0;
        this.notizen = new ArrayList<>();
        this.aufgedeckt = false;
        this.falscheZahl = 0;
    }

    public int getWert(){
        return wert;
    }

    public int getFalscheZahl(){
        return falscheZahl;
    }

    public boolean istAufgedeckt(){
        return aufgedeckt;
    }

    public ArrayList<Integer> getNotizen() {
        return notizen;
    }

    public void setWert(int wert){
        this.wert = wert;
    }

    public void setAufgedeckt(boolean aufgedeckt){
        this.aufgedeckt = aufgedeckt;
    }

    public void setFalscheZahl(int falscheZahl){
        this.falscheZahl = falscheZahl;
    }

    public void notizHinzufuegen(int zahl){
        this.notizen.add(zahl);
    }

    public void notizLoeschen(int zahl){
        this.notizen.remove(new Integer(zahl));
    }
}
