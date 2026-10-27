package com.example.sudoku.model.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interface eines DAO-Objekts
 * @author  Paula Matthies
 */
@Dao
public interface SudokuInDao {

    /**
     * Fügt ein Objekt der Entität Spiel in die datenabank ein
     * @param sudokuSpiel Spiel welches eingefügt wird
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpiel(Spiel sudokuSpiel);

    /**
     * Getter für die geringste Spielzeit des Niveaus "Anfänger"
     */
    @Query("SELECT MIN(spielzeit) FROM spiele WHERE schwierigkeit='Anfänger'")
    LiveData<Integer> getHighscoreAnfaenger();

    /**
     * Getter für die geringste Spielzeit des Niveaus "Einfach"
     */
    @Query("SELECT MIN(spielzeit) FROM spiele WHERE schwierigkeit='Einfach'")
    LiveData<Integer> getHighscoreEinfach();

    /**
     * Getter für die geringste Spielzeit des Niveaus "Mittel"
     */
    @Query("SELECT MIN(spielzeit) FROM spiele WHERE schwierigkeit='Mittel'")
    LiveData<Integer> getHighscoreMittel();

    /**
     * Getter für die geringste Spielzeit des Niveaus "Schwierig"
     */
    @Query("SELECT MIN(spielzeit) FROM spiele WHERE schwierigkeit='Schwierig'")
    LiveData<Integer> getHighscoreSchwierig();

    /**
     * Getter für die Liste aller Spiele in der Datenabnk aufsteigend sortiert nach der Spielzeit
     */
    @Query("SELECT * FROM spiele ORDER BY spielzeit")
    LiveData<List<Spiel>> getAlleSpiele();

}
