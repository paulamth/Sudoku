package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.sudoku.R;
import com.example.sudoku.model.data.Spiel;
import com.example.sudoku.viewModel.DatabaseViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Result-Activity nach erfolgreichem Lösen eines Spielfeldes
 * @author  Paula Matthies
 */
public class ResultsActivity extends AppCompatActivity {

    private final static String SCHWIERIGKEIT = "com.example.sudoku.view.NeuesSpielActivity.SCHWIERIGKEIT";
    private final static String ZEIT = "com.example.sudoku.view.SpielfeldActivity.ZEIT";

    private Button btn_startseite;
    private Button btn_neuesSpiel;
    private TextView time;
    private TextView schwierigkeit;
    private TextView bestzeit;
    private DatabaseViewModel viewModel;
    private String schwierigkeitsGrad;
    private final static SimpleDateFormat format = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        viewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        this.initialisiere();
        this.setzeObserver();

        Intent intent_in = this.getIntent();
        long zeit = intent_in.getLongExtra(ZEIT,0);
        schwierigkeitsGrad = intent_in.getStringExtra(SCHWIERIGKEIT);
        //Werte des Intents werden als Text der TextViews gesetzt
        time.setText(""+getTimeLabel(zeit));
        schwierigkeit.setText(""+schwierigkeitsGrad);

        //ein neues Spiel wird der Datenbank hinzugefügt
        Spiel spiel = new Spiel(zeit, schwierigkeitsGrad);
        viewModel.insert(spiel);
    }

    /**
     * Textfelder und Buttons werden initialisiert und die onClickListener gesetzt
     */
    private void initialisiere(){
        btn_startseite = this.findViewById(R.id.startseite);
        btn_neuesSpiel = this.findViewById(R.id.neuesSpiel);
        time = this.findViewById(R.id.zeit);
        schwierigkeit = this.findViewById(R.id.schwierigkeit);
        bestzeit = this.findViewById(R.id.bestzeit);

        btn_startseite.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btn_neuesSpiel.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, NeuesSpielActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Observer der Datenbankinhalte werden gesetzt
     */
    private void setzeObserver(){
        final Observer<Integer> spielzeitAnfaenger = zeitAnfaenger -> this.setSpielzeitenAnfaenger(zeitAnfaenger);
        final Observer<Integer> spielzeitEinfach = zeitEinfach -> this.setSpielzeitenEinfach(zeitEinfach);
        final Observer<Integer> spielzeitMittel = zeitMittel -> this.setSpielzeitenMittel(zeitMittel);
        final Observer<Integer> spielzeitSchwierig = zeitSchwierig -> this.setSpielzeitenSchwierig(zeitSchwierig);

        viewModel.getHighscoreAnfaenger().observe(this,spielzeitAnfaenger);
        viewModel.getHighscoreEinfach().observe(this,spielzeitEinfach);
        viewModel.getHighscoreMittel().observe(this, spielzeitMittel);
        viewModel.getHighscoreSchwierig().observe(this,spielzeitSchwierig);
    }

    /**
     * Setzt in der TextView die Bestzeit der Schwierigkeit "Schwierig"
     * @param zeit Bestzeit des Schwierigkeit "Schwierig"
     */
    private void setSpielzeitenSchwierig(Integer zeit) {
        if(schwierigkeitsGrad.equals("Schwierig") && zeit != null){
            bestzeit.setText(""+getTimeLabel(zeit));
        }
    }

    /**
     * Setzt in der TextView die Bestzeit der Schwierigkeit "Mittel"
     * @param zeit Bestzeit des Schwierigkeit "Mittel"
     */
    private void setSpielzeitenMittel(Integer zeit) {
        if(schwierigkeitsGrad.equals("Mittel") && zeit != null){
            bestzeit.setText(""+getTimeLabel(zeit));
        }
    }

    /**
     * Setzt in der TextView die Bestzeit der Schwierigkeit "Einfach"
     * @param zeit Bestzeit des Schwierigkeit "Einfach"
     */
    private void setSpielzeitenEinfach(Integer zeit) {
        if(schwierigkeitsGrad.equals("Einfach") && zeit != null){
            bestzeit.setText(""+getTimeLabel(zeit));
        }
    }

    /**
     * Setzt in der TextView die Bestzeit der Schwierigkeit "Anfaenger"
     * @param zeit Bestzeit des Schwierigkeit "Anfaenger"
     */
    private void setSpielzeitenAnfaenger(Integer zeit) {
        if(schwierigkeitsGrad.equals("Anfänger") && zeit != null){
            bestzeit.setText(""+getTimeLabel(zeit));
        }
    }

    // Quelle: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    /**
     * Wandelt die Spielzeit in ein anderes Format
     * @param time Wert der in Minuten und Sekunden umgeformt werden soll
     */
    public static String getTimeLabel(long time) {
        return format.format(new Date(time));
    }
}