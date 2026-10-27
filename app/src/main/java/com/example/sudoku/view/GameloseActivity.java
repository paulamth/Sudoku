package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sudoku.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Activity bei verlorenem Spiel
 * @author  Paula Matthies
 */
public class GameloseActivity extends AppCompatActivity {

    private Button btn_startseite;
    private Button btn_neuesSpiel;

    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelose);
        this.initialisiereButtons();
    }

    /**
     * Buttons werden initialisiert und die onClickListener gesetzt
     */
    private void initialisiereButtons() {
        btn_startseite = this.findViewById(R.id.startseite);
        btn_neuesSpiel = this.findViewById(R.id.neuesSpiel);

        btn_startseite.setOnClickListener(v -> {
            Intent intent = new Intent(GameloseActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btn_neuesSpiel.setOnClickListener(v -> {
            Intent intent = new Intent(GameloseActivity.this, NeuesSpielActivity.class);
            startActivity(intent);
        });
    }

}