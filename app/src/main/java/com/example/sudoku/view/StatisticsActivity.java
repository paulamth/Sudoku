package com.example.sudoku.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sudoku.R;
import com.example.sudoku.model.data.Spiel;
import com.example.sudoku.viewModel.DatabaseViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Activity für die Anzeige der Rankingliste
 * @author  Paula Matthies
 */
public class StatisticsActivity extends AppCompatActivity {

    private Button btn_return;
    private final static SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private DatabaseViewModel viewModel;

    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //das Layout der Activity festlegen
        setContentView(R.layout.activity_statistics);
        //Beziehung zum ViewModel setzen für Weitergabe von Benutzerinteraktionen
        viewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        //Observer erstellen, der die gespeicherte Spielliste in der Datenbank bzw. aus dem VieModel beobachtet
        final Observer<List<Spiel>> spiele = s -> this.erstelleRangliste(s);
        viewModel.getAlleSpiele().observe(this,spiele);
        this.initialisiereButton();

    }

    /**
     * Button wird initialisiert und onClickListener gesetzt
     */
    private void initialisiereButton(){
        btn_return = this.findViewById(R.id.zurueck);

        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Erstellt Strings mit den Daten der gelösten Spiele
     * Die Strings werden als Texte der TextViews gesetzt
     * @param spiele die Liste aller gelösten Spiele
     */
    private void erstelleRangliste(List<Spiel> spiele) {
        TextView schwierigkeit = this.findViewById(R.id.schwierigkeit);
        TextView zeit = this.findViewById(R.id.zeit);
        String s = "";
        String z = "";
        for(int i = 0; i<spiele.size(); i++){
            s += spiele.get(i).getSchwierigkeit() +"\n";
            z += getTimeLabel(spiele.get(i).getSpielzeit()) +"\n";
        }
        schwierigkeit.setText(s);
        zeit.setText(z);
    }

    // Quelle: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    /**
     * Wandelt die Spielzeit in ein anderes Format
     * @param time Wert der in Minuten und Sekunden umgeformt werden soll
     */
    private static String getTimeLabel(long time) {
        return format.format(new Date(time));
    }
}