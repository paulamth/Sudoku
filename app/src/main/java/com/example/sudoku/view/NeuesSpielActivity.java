package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.sudoku.R;

/**
 * Activity um ein neues Spiel zu starten
 * Nutzer kann zwischen verschiedenen Schwierigkeitsgraden wählen
 * @author  Paula Matthies
 */
public class NeuesSpielActivity extends AppCompatActivity {

    private final static String ANZAHL_FELDER = "com.example.sudoku.view.NeuesSpielActivity.ANZAHL_FELDER";
    private final static String AUFGEDECKTE_FELDER_ANZAHL = "com.example.sudoku.view.NeuesSpielActivity.AUFGEDECKTE_FELDER_ANZAHL";
    private final static String SCHWIERIGKEIT = "com.example.sudoku.view.NeuesSpielActivity.SCHWIERIGKEIT";

    private Button btn_vier;
    private Button btn_einfach;
    private Button btn_mittel;
    private Button btn_schwer;
    private Button btn_return;

    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neues_spiel);

        this.initialisiereButtons();
    }

    /**
     * Buttons werden initialisiert und die onClickListener gesetzt
     */
    private void initialisiereButtons(){
        btn_vier = this.findViewById(R.id.vier);
        btn_einfach = this.findViewById(R.id.einfach);
        btn_mittel = this.findViewById(R.id.mittel);
        btn_schwer = this.findViewById(R.id.schwer);
        btn_return = this.findViewById(R.id.zurueck);

        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(NeuesSpielActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Intent intent = new Intent(NeuesSpielActivity.this, SpielfeldActivity.class);
        // das Intent erhält abhängig von dem ausgewählten Schwierigkeitsgrad andere Werte
        btn_vier.setOnClickListener(v -> {
            intent.putExtra(ANZAHL_FELDER,4);
            intent.putExtra(AUFGEDECKTE_FELDER_ANZAHL,8);
            intent.putExtra(SCHWIERIGKEIT,"Anfänger");
            startActivity(intent);
        });

        btn_einfach.setOnClickListener(v -> {
            intent.putExtra(ANZAHL_FELDER,9);
            intent.putExtra(AUFGEDECKTE_FELDER_ANZAHL,40);
            intent.putExtra(SCHWIERIGKEIT,"Einfach");
            startActivity(intent);
        });

        btn_mittel.setOnClickListener(v -> {
            intent.putExtra(ANZAHL_FELDER,9);
            intent.putExtra(AUFGEDECKTE_FELDER_ANZAHL,35);
            intent.putExtra(SCHWIERIGKEIT,"Mittel");
            startActivity(intent);
        });

        btn_schwer.setOnClickListener(v -> {
            intent.putExtra(ANZAHL_FELDER,9);
            intent.putExtra(AUFGEDECKTE_FELDER_ANZAHL,25);
            intent.putExtra(SCHWIERIGKEIT,"Schwierig");
            startActivity(intent);
        });
    }
}