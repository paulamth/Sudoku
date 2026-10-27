package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.sudoku.R;

/**
 * Activity für die Startseite der App
 * Verfügt über das Hauptmenü
 * @author  Paula Matthies
 */
public class MainActivity extends AppCompatActivity {

    private Button btn_addGame;
    private Button btn_rankingliste;

    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initialisiereButtons();
    }

    /**
     * Buttons werden initialisiert und die onClickListener gesetzt
     */
    private void initialisiereButtons() {
        btn_addGame = this.findViewById(R.id.neuesSpiel);
        btn_rankingliste = this.findViewById(R.id.Rankingliste);

        // Activity für ein neues Spiel starten
        btn_addGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NeuesSpielActivity.class);
            startActivity(intent);
        });

        // Activity für die Anzeige der Rankingliste öffnen
        btn_rankingliste.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
    }
}