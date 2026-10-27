package com.example.sudoku.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.sudoku.model.data.AppDatabase;
import com.example.sudoku.model.data.Spiel;
import com.example.sudoku.model.data.SudokuInDao;

import java.util.List;

/**
 * Repository-Klasse für Zugriff auf Daten der Datenbank
 * Hält Objekt der Datenbank und ruft über Instanz des Interfaces SudokuInDao die Methoden der Datenbank aus
 * @author  Paula Matthies
 */
public class SpielRepository {

    private SudokuInDao sudokuDao;

    public SpielRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        sudokuDao = db.sudokuSpielInDao();
    }

    public LiveData<List<Spiel>> getAlleSpiele() {
        return sudokuDao.getAlleSpiele();
    }

    public void insert(Spiel spiel) {
       new InsertSpielAsyncTask(sudokuDao).execute(spiel);
    }

    public LiveData<Integer> getHighscoreAnfaenger(){
        return sudokuDao.getHighscoreAnfaenger();
    }

    public LiveData<Integer> getHighscoreMittel(){
        return sudokuDao.getHighscoreMittel();
    }

    public LiveData<Integer> getHighscoreEinfach(){
        return sudokuDao.getHighscoreEinfach();
    }

    public LiveData<Integer> getHighscoreSchwierig(){
        return sudokuDao.getHighscoreSchwierig();
    }

    // Multithreading mit AsyncTask
    private static class InsertSpielAsyncTask extends AsyncTask<Spiel, Void, Void>{

        private SudokuInDao sudokuInDao;
        private InsertSpielAsyncTask(SudokuInDao sudokuInDao){
            this.sudokuInDao = sudokuInDao;
        }
        @Override
        protected Void doInBackground(Spiel... spiel) {
            sudokuInDao.insertSpiel(spiel[0]);
            return null;
        }
    }

}
