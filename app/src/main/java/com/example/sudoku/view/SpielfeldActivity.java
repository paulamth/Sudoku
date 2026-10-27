package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import com.example.sudoku.R;
import com.example.sudoku.model.game.ui.Zelle;
import com.example.sudoku.view.custom.SudokufeldCustomView;
import com.example.sudoku.viewModel.SpielfeldViewModel;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity des Spielfeldes, auf der das Sudokuspiel stattfindet
 * @author  Paula Matthies
 */
public class SpielfeldActivity extends AppCompatActivity{

    private final static String ANZAHL_FELDER = "com.example.sudoku.view.NeuesSpielActivity.ANZAHL_FELDER";
    private final static String AUFGEDECKTE_FELDER_ANZAHL = "com.example.sudoku.view.NeuesSpielActivity.AUFGEDECKTE_FELDER_ANZAHL";
    private final static String SCHWIERIGKEIT = "com.example.sudoku.view.NeuesSpielActivity.SCHWIERIGKEIT";
    private final static String ZEIT = "com.example.sudoku.view.SpielfeldActivity.ZEIT";

    private Button btn_return;
    private Button btn_notizen;
    private Button btn_hinweis;
    private Button btn_loeschen;
    private ImageButton btn_play_pause;
    private TextView format;
    private TextView fehler;
    private TextView tv_schwierigkeit;
    private Chronometer chronometer;
    private int anzahlFelder;
    private String schwierigkeitsGrad;
    private int schwierigkeit;
    private boolean running = false;
    private long pauseOffset;
    private boolean notizen = false;
    private long zusatzZeit;
    private SpielfeldViewModel viewModel;
    private SudokufeldCustomView sudokufeldCustomView;


    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SpielfeldViewModel.class);
        setContentView(R.layout.activity_spielfeld);

        Intent intent_in = this.getIntent();
        anzahlFelder = intent_in.getIntExtra(ANZAHL_FELDER,0);
        schwierigkeit = intent_in.getIntExtra(AUFGEDECKTE_FELDER_ANZAHL,0);
        schwierigkeitsGrad = intent_in.getStringExtra(SCHWIERIGKEIT);

        this.initialisiere();
        this.erstelleZahlButton();
        this.messeZeit();

    }

    /**
     * Initialisiert Buttons, Observer und onClickListener
     */
    private void initialisiere(){
        //Buttons und TextView initialisieren
        btn_return = this.findViewById(R.id.return1);
        btn_notizen = this.findViewById(R.id.notizen);
        btn_hinweis = this.findViewById(R.id.hinweis);
        btn_loeschen = this.findViewById(R.id.loeschen);
        btn_play_pause = this.findViewById(R.id.pause);
        chronometer = this.findViewById(R.id.zeit);
        format = this.findViewById(R.id.groesse);
        tv_schwierigkeit = this.findViewById(R.id.schwierigkeit);
        fehler = this.findViewById(R.id.fehler);
        format.setText(""+anzahlFelder+"/"+anzahlFelder);
        tv_schwierigkeit.setText(schwierigkeitsGrad);
        sudokufeldCustomView = this.findViewById(R.id.imageViewSpielfeld);

        // Observer erstellen
        final Observer<Pair<Integer, Integer>> aktuelleZelle = zelle -> sudokufeldCustomView.setzeAktZelle(zelle.first, zelle.second);
        final Observer<Zelle[][]> spielfeld = feld -> sudokufeldCustomView.setFeld(feld);
        final Observer<Boolean> spielGeloest = geloest -> this.spielBeendet(geloest);
        final Observer<Integer> fehleranzahl = fehler -> this.setFehleranzahl(fehler);
        final Observer<Integer> hinweisanzahl = hinweis -> this.setHinweisanzahl(hinweis);;

        // Daten aus ViewModel beobachten
        viewModel.init(schwierigkeit,anzahlFelder);
        viewModel.getAktZelleLiveData().observe(this,aktuelleZelle);
        viewModel.getFeldLiveData().observe(this,spielfeld);
        viewModel.getGeloestLiveData().observe(this,spielGeloest);
        viewModel.getFehleranzahlLiveData().observe(this,fehleranzahl);
        viewModel.getHinweisanzahlLivedata().observe(this,hinweisanzahl);

        //onTouchListener für die CustomView bzw. das Sudokufeld
        sudokufeldCustomView.setOnTouchListener((view, motionEvent)->{
            if(motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                sudokufeldCustomView.touch(motionEvent);
                viewModel.setAktZelle(sudokufeldCustomView.getAktReihe(), sudokufeldCustomView.getAktSpalte());
            }
            return true;
        });

        //onClickListener für die Buttons
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(SpielfeldActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btn_play_pause.setOnClickListener(v -> {
            this.messeZeit();
        });
        btn_hinweis.setOnClickListener(v -> {
            if(running == true) {
                viewModel.oeffneHinweis();
            }
        });
        btn_loeschen.setOnClickListener(v -> {
            if(running == true) {
                viewModel.loeschen();
            }
        });
        btn_notizen.setOnClickListener(v -> {
            if(running == true) {
                if (!notizen) {
                    btn_notizen.setBackgroundColor(Color.parseColor("#8e91e8"));
                    notizen = true;
                } else {
                    btn_notizen.setBackgroundColor(Color.parseColor("#33379e"));
                    notizen = false;
                }
            }
        });
    }

    /**
     * Zahlenbuttons werden erstellt
     */
    private void erstelleZahlButton(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.zahlButton);
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
        for(int i = 1; i<=anzahlFelder;i++){
            Button button = new Button(this);
            button.setId(i);
            button.setText(""+i);
            button.setTextSize(40);
            button.setLayoutParams(params);
            button.setOnClickListener(v -> {
                if(running == true){
                    viewModel.klickZahlButton(Integer.parseInt(button.getText().toString()), notizen);
                }
            });
            linearLayout.addView(button);
        }
    }

    /**
     * Spielzeit wird gemessen
     */
    private void messeZeit(){
        //Spiel wird pausiert
        if (running == true) {
            btn_play_pause.setBackgroundResource(R.drawable.ic_play);
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            sudokufeldCustomView.setPausiert(true);
        }
        else{
            chronometer.setBase(SystemClock.elapsedRealtime() -pauseOffset);
            btn_play_pause.setBackgroundResource(R.drawable.ic_pause);
            chronometer.start();
            running = true;
            sudokufeldCustomView.setPausiert(false);
        }
        sudokufeldCustomView.invalidate();
    }

    /**
     * Bei Änderung der Hinweisanzahl, wird Textfeld geändert und die Zeit erhöht sich um zehn Sekunden
     */
    private void setHinweisanzahl(Integer hinweisanzahl) {
        btn_hinweis.setText("Hinweis("+hinweisanzahl+")");
        zusatzZeit += 10000;
    }

    /**
     * Bei Änderung der Fehleranzahl, wird Textfeld geändert
     * Ist die maximale Fehleranzahl erreicht, wird das Spiel beendet
     */
    private void setFehleranzahl(Integer fehleranzahl) {
        if(fehleranzahl<3){
            fehler.setText("Fehler: "+fehleranzahl+"/3");
        }
        else{
            Intent intent = new Intent(SpielfeldActivity.this, GameloseActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Wenn das Spielfeld vollständig gelöst ist, öffnet sich eine neue Activity
     */
    private void spielBeendet(Boolean geloest){
        if(geloest){
            chronometer.stop();
            Intent intent = new Intent(SpielfeldActivity.this, ResultsActivity.class);
            intent.putExtra(ZEIT,SystemClock.elapsedRealtime() - chronometer.getBase()+zusatzZeit-10000);
            intent.putExtra(SCHWIERIGKEIT,schwierigkeitsGrad);
            startActivity(intent);
        }
    }

}