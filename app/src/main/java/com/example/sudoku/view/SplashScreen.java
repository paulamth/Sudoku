package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.sudoku.R;

/**
 * Activity für die SplashScreen Anzeige
 * Zeigt das Logo der App für ein paar Sekunden und öffnet dann die Startseite
 * @author  Paula Matthies
 */
public class SplashScreen extends AppCompatActivity {

    /**
     * Wird beim Erzeugen der Activity ausgeführt
     * Initialisiert die Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //ActionBar ausblenden
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Quelle: https://abhiandroid.com/programming/splashscreen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

}