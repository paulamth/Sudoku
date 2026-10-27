package com.example.sudoku.model.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * Datenbank
 * @author  Paula Matthies
 */
@Database(entities = {Spiel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final String DB_NAME = "sudoku_db";

    /**
     * Liefert Datenbank Instanz zurück
     */
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /**
     * Liefert Instanz von SudokuInDao
     */
    public abstract SudokuInDao sudokuSpielInDao();

}
