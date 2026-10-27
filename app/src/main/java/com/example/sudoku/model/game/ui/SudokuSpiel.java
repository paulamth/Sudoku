package com.example.sudoku.model.game.ui;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import com.example.sudoku.model.game.logik.Loesen;
import com.example.sudoku.model.game.logik.Loeser;
import java.util.Random;

/**
 * Spiel-Klasse der Sudoku App
 * Ist für die Spiellogik zuständig
 * Umfasst alle notwendigen Methoden und Variablen für das Spiel
 * @author  Paula Matthies
 */
public class SudokuSpiel {

    private Loesen loeser;
    private int aktReihe;
    private int aktSpalte;
    private int anzahlFelder;
    private Zelle[][] spielfeld;
    private MutableLiveData<Boolean> geloestLiveData = new MutableLiveData<>();
    private MutableLiveData<Pair<Integer,Integer>> aktuelleZelleLiveData = new MutableLiveData<>();
    private MutableLiveData<Zelle[][]> feldLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> fehleranzahlLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> hinweisanzahlLiveData = new MutableLiveData<>();


    public SudokuSpiel(int anzahlFelder){
        this.anzahlFelder = anzahlFelder;
        this.loeser = new Loeser();
        this.aktSpalte = -1;
        this.aktReihe = -1;
        this.aktuelleZelleLiveData.setValue(new Pair<>(aktReihe,-aktSpalte));
        this.feldLiveData.setValue(spielfeld);
        this.geloestLiveData.setValue(false);
        this.fehleranzahlLiveData.setValue(0);
        this.hinweisanzahlLiveData.setValue(3);
        this.initialisiereFeld();
    }

    /**
     * Spielfeld wird initialisiert
     */
    public void initialisiereFeld(){
        this.spielfeld = new Zelle[anzahlFelder][anzahlFelder];
        for(int r = 0;r< spielfeld.length;r++){
            for(int s = 0; s<spielfeld[r].length;s++){
                Zelle z = new Zelle();
                spielfeld[r][s] = z;
            }
        }
    }

    /**
     * Führt alle Methoden aus die zum Spielbeginn notwendig sind
     * @param schwierigkeit der Schwierigkeitsgrad entscheidet, wie viele Felder zu Beginn aufgedeckt sind
     */
    public void starteSpiel(int schwierigkeit){
        loeser.loeseSudokufeld(spielfeld);
        this.zahlenAufdecken(schwierigkeit);
        // LiveData-Variablen müssen gesettet werden, damit der Observer nach Veränderungen informiert wird
        feldLiveData.setValue(spielfeld);
    }

    /**
     * Deckt per Zufallsgenerator Zellen auf
     * @param schwierigkeit der Schwierigkeitsgrad entscheidet, wie viele Felder zu Beginn aufgedeckt sind
     */
    public void zahlenAufdecken(int schwierigkeit){
        Random random = new Random();

        for(int i = 0; i<schwierigkeit;i++){
            int randReihe = random.nextInt(anzahlFelder);
            int randSpalte = random.nextInt(anzahlFelder);

            if(!spielfeld[randReihe][randSpalte].istAufgedeckt()){
                spielfeld[randReihe][randSpalte].setAufgedeckt(true);
            }
            else{
                i--;
            }
        }
    }

    /**
     * Leert das aktuelle Feld, wenn es eine falsche Zahl oder Notizen beinhaltet
     */
    public void loeschen(){
        if(aktSpalte != -1 && aktReihe != -1 ){
            if(spielfeld[aktReihe][aktSpalte].getFalscheZahl()!=0) {
                spielfeld[aktReihe][aktSpalte].setFalscheZahl(0);
                spielfeld[aktReihe][aktSpalte].setAufgedeckt(false);
            }
            if(!spielfeld[aktReihe][aktSpalte].getNotizen().isEmpty()){
                spielfeld[aktReihe][aktSpalte].getNotizen().clear();
            }
        }
        feldLiveData.setValue(spielfeld);
    }

    /**
     * Fügt Zahl als kleine Notiz in die aktuelle Zelle
     * @param zahl Wert, der als Notiz gesetzt werden soll
     */
    public void setzeNotizen(int zahl){
        if(aktReihe == -1 || aktSpalte == -1){
            return;
        }
        //prüft, dass die Zelle nicht bereits richtig gelöst wurde
        if(spielfeld[aktReihe][aktSpalte].istAufgedeckt()==false) {
            if (spielfeld[aktReihe][aktSpalte].getNotizen().contains(zahl)) {
                spielfeld[aktReihe][aktSpalte].notizLoeschen(zahl);
            }
            else{
                spielfeld[aktReihe][aktSpalte].notizHinzufuegen(zahl);
            }
        }
        feldLiveData.setValue(spielfeld);
    }

    /**
     * Fügt Wert als Fehler oder richtige Zahl in die aktuelle Zelle ein
     * @param zahl Wert, der in die Zelle gesetzt werden soll
     */
    public void setzeZahl(int zahl){
        if(aktReihe == -1 || aktSpalte == -1){
            return;
        }
        //prüft, dass die Zelle unaufgedekct ist
        if(spielfeld[aktReihe][aktSpalte].istAufgedeckt()==false) {
            spielfeld[aktReihe][aktSpalte].getNotizen().clear();
            //Fehler wird gesetzt, derselbe Fehler nicht schon existiert
            if (spielfeld[aktReihe][aktSpalte].getFalscheZahl() != zahl && spielfeld[aktReihe][aktSpalte].getWert() != zahl) {
                spielfeld[aktReihe][aktSpalte].setFalscheZahl(zahl);
                fehleranzahlLiveData.setValue(fehleranzahlLiveData.getValue() + 1);
            }
            //wenn Zahl richtig ist, wird Zelle aufgedeckt
            // falls in der Zelle vorher eine falsche Zahl stand, wird diese zurückgesetzt
            else if(spielfeld[aktReihe][aktSpalte].getWert() == zahl){
                spielfeld[aktReihe][aktSpalte].setAufgedeckt(true);
                spielfeld[aktReihe][aktSpalte].setFalscheZahl(0);
            }
        }
        feldLiveData.setValue(spielfeld);
        spielGeloest();
    }

    /**
     * Überprüft, ob alle Zellen mit der richtigen Zahl besetzt sind
     * @return true, wenn Spielfeld vollständig gelöst ist
     */
    public boolean spielGeloest(){
        for(int r = 0; r<anzahlFelder;r++){
            for(int s = 0; s<anzahlFelder;s++){
                if(!spielfeld[r][s].istAufgedeckt()){
                    geloestLiveData.setValue(false);
                    return false;
                }
            }
        }
        geloestLiveData.setValue(true);
        return true;
    }

    /**
     * Deckt eine zufällige Zelle auf
     */
    public void oeffneHinweis(){
        this.hinweisanzahlLiveData.setValue(hinweisanzahlLiveData.getValue()-1);
        this.zahlenAufdecken(1);
        feldLiveData.setValue(spielfeld);
        spielGeloest();
    }

    /**
     * Setter für die aktuelle Zelle
     */
    public void setAktuelleZelle(int r, int s) {
        aktReihe = r;
        aktSpalte = s;
        aktuelleZelleLiveData.setValue(new Pair<>(r,s));
    }

    //Getter für die LiveData-Variablen

    public  MutableLiveData<Integer> getHinweisanzahlLiveData() {
        return hinweisanzahlLiveData;
    }

    public MutableLiveData<Pair<Integer,Integer>> getAktuelleZelleLiveData(){ return aktuelleZelleLiveData;}

    public MutableLiveData<Zelle[][]> getFeldLiveData(){
        return feldLiveData;
    }

    public MutableLiveData<Boolean> getGeloestLiveData() {
        return geloestLiveData;
    }

    public MutableLiveData<Integer> getFehleranzahlLiveData() { return fehleranzahlLiveData;}

    public void setSpielfeld(Zelle[][] spielfeld){
        this.spielfeld = spielfeld;
    }
}
