package com.example.sudoku.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Daten dieser Entität-Klasse werden in der Datenbank gespeichert
 * @author  Paula Matthies
 */
@Entity(tableName= "spiele")
public class Spiel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long spielzeit;
    private String schwierigkeit;

    public Spiel(long spielzeit, String schwierigkeit){
        this.spielzeit = spielzeit;
        this.schwierigkeit = schwierigkeit;
    }

    //Getter und Setter für Instanzvariablen
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSpielzeit() {
        return spielzeit;
    }

    public void setSpielzeit(long spielzeit) {
        this.spielzeit = spielzeit;
    }

    public String getSchwierigkeit() {
        return schwierigkeit;
    }

    public void setSchwierigkeit(String schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }
}
