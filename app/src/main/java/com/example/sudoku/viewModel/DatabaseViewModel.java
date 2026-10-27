package com.example.sudoku.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sudoku.model.SpielRepository;
import com.example.sudoku.model.data.Spiel;

import java.util.List;

/**
 * ViewModel für Zugriff auf Daten der Datenbank
 * Hält Objekt der Klasse Repository und leitet an sie alle Methoden weiter
 * @author  Paula Matthies
 */
public class DatabaseViewModel extends AndroidViewModel {

    private SpielRepository repository;

    public DatabaseViewModel(Application application){
        super(application);
        repository = new SpielRepository(application);
    }

    public LiveData<List<Spiel>> getAlleSpiele(){
        return repository.getAlleSpiele();
    }

    public void insert(Spiel spiel){
        repository.insert(spiel);
    }

    public LiveData<Integer> getHighscoreAnfaenger(){
        return repository.getHighscoreAnfaenger();
    }

    public LiveData<Integer> getHighscoreMittel(){
        return repository.getHighscoreMittel();
    }

    public LiveData<Integer> getHighscoreSchwierig(){
        return repository.getHighscoreSchwierig();
    }

    public LiveData<Integer> getHighscoreEinfach(){
        return repository.getHighscoreEinfach();
    }
}
