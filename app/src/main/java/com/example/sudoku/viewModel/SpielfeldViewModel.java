package com.example.sudoku.viewModel;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sudoku.model.game.ui.SudokuSpiel;
import com.example.sudoku.model.game.ui.Zelle;

/**
 * ViewModel für Zugriff auf Daten der Klasse SudokuSpiel
 * @author  Paula Matthies
 */
public class SpielfeldViewModel extends ViewModel {

    private SudokuSpiel sudokuSpiel;

    public void init(int schwierigkeit, int anzahlFelder){
        sudokuSpiel = new SudokuSpiel(anzahlFelder);
        sudokuSpiel.starteSpiel(schwierigkeit);
    }


    /**
     * Entscheidet Zahl als Notiz gesetzt werden soll oder nicht
     * @param zahl die über Zahlbutton ausgewählt wurde
     * @param notizen Wahrheitswert, ob Das Setzen von Notizen momentan aktiv ist
     */
    public void klickZahlButton(int zahl, boolean notizen){
        if(notizen){
            sudokuSpiel.setzeNotizen(zahl);
        }
        else {
            sudokuSpiel.setzeZahl(zahl);
        }
    }

    /**
     * Wenn weitere Hinweise gesetzt werden dürfen, wird die Methode in der SudokuSpiel Klasse ausgeführt
     */
    public void oeffneHinweis(){
        if(sudokuSpiel.getHinweisanzahlLiveData().getValue()>0){
            sudokuSpiel.oeffneHinweis();
        }
    }

    /**
     * Löschmethode im SudokuSpiel wird ausgeführt
     */
    public void loeschen(){
        sudokuSpiel.loeschen();
    }

    public void setAktZelle(int x, int y) {
        sudokuSpiel.setAktuelleZelle(x,y);
    }

    public LiveData<Pair<Integer,Integer>> getAktZelleLiveData(){ return sudokuSpiel.getAktuelleZelleLiveData();}

    public LiveData<Zelle[][]> getFeldLiveData(){
        return sudokuSpiel.getFeldLiveData();
    }

    public LiveData<Boolean> getGeloestLiveData() {
        return sudokuSpiel.getGeloestLiveData();
    }

    public LiveData<Integer> getHinweisanzahlLivedata(){ return sudokuSpiel.getHinweisanzahlLiveData();}

    public LiveData<Integer> getFehleranzahlLiveData(){ return this.sudokuSpiel.getFehleranzahlLiveData();}

}
