package com.example.sudoku.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.sudoku.model.game.ui.Zelle;

/**
* CustomView für das Spielfeld
* @author  Paula Matthies
*/
public class SudokufeldCustomView extends View {

    private Canvas canvas;
    private Paint paint;
    private int breite;
    private int seitenlaenge;
    private int anzahlFelder;
    private int aktReihe;
    private int aktSpalte;
    private boolean pausiert;
    private Zelle[][] feld;

    public SudokufeldCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        aktReihe = -1;
        aktSpalte = -1;
        pausiert = false;
    }


    /**
     * Zeichnet die View / das Spielfeld
     */
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        breite = getWidth();
        seitenlaenge = breite/anzahlFelder;
        this.zeichneFeld();
        if(feld != null && pausiert==false){
            this.zeichneZahlen();
        }
    }

    /**
     * Zeichnet das Grundgerüst des Spielfeldes (ohne Zahlen)
     */
    private void zeichneFeld(){
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,breite,breite,paint);
        if (pausiert == false) {
            this.faerbeZellen();
        }
        this.zeichneGitter();
    }

    /**
     * Zeichnet das Raster des Sudokufeldes
     */
    private void zeichneGitter(){
        paint.setColor(Color.BLACK);

        for(int i = 0;i<=anzahlFelder;i++){
            if(i%(Math.sqrt(anzahlFelder))==0){
                paint.setStrokeWidth(5);
            }
            else{
                paint.setStrokeWidth(1);
            }
            canvas.drawLine(0,i*seitenlaenge, breite, i*seitenlaenge, paint);
            canvas.drawLine(i*seitenlaenge,0, i*seitenlaenge, breite, paint);
        }
    }

    /**
     * Färbt die aktuell angeklickte Zelle und ihre Reihe, Spalte und Box
     */
    private void faerbeZellen(){
        int wurzel = (int)Math.sqrt(anzahlFelder);
        if(aktSpalte == -1 || aktReihe == -1) return;

        for(int r = 0; r<anzahlFelder; r++){
            for(int s = 0; s<anzahlFelder; s++) {
                if(r == aktReihe && s == aktSpalte){
                    paint.setColor(Color.GRAY);
                    canvas.drawRect(s*seitenlaenge,r*seitenlaenge,s*seitenlaenge+seitenlaenge,r*seitenlaenge+seitenlaenge,paint);

                }
                else if (r == aktReihe || s == aktSpalte){
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRect(s*seitenlaenge,r*seitenlaenge,s*seitenlaenge+seitenlaenge,r*seitenlaenge+seitenlaenge,paint);


                }
                else if(r /wurzel == aktReihe/wurzel && s/wurzel == aktSpalte/wurzel){
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRect(s*seitenlaenge,r*seitenlaenge,s*seitenlaenge+seitenlaenge,r*seitenlaenge+seitenlaenge,paint);
                }
            }
        }
    }

    /**
     * Zeichnet alle aufgedeckten Zahlen, Fehler und Notizen ins Spielfeld ein
     */
    private void zeichneZahlen(){
        for(int r = 0; r<feld.length;r++){
            for(int s =0;s<feld[r].length;s++){
                paint.setTextSize(seitenlaenge);
                //falsche Zahlen zeichnen
                if (feld[r][s].getFalscheZahl()!=0){
                    paint.setColor(Color.RED);
                    canvas.drawText(""+feld[r][s].getFalscheZahl(),s*seitenlaenge+seitenlaenge/4,r*seitenlaenge+seitenlaenge-seitenlaenge*1/10, paint);
                }
                //richtige Zahlen zeichnen
                else if (feld[r][s].istAufgedeckt()){
                    paint.setColor(Color.BLACK);
                    canvas.drawText(""+feld[r][s].getWert(),s*seitenlaenge+seitenlaenge/4,r*seitenlaenge+seitenlaenge-seitenlaenge*1/10, paint);
                }
                //Notizen zeichnen
                else if(!feld[r][s].getNotizen().isEmpty()){
                    paint.setColor(Color.BLACK);
                    this.zeichneNotizen(r,s);
                }
            }
        }
    }

    /**
     * Zeichnet alle gesetzen Notizen ins Spielfeld
     * @param r die Reihe der Zelle
     * @param s die Spalte der Zelle
     */
    private void zeichneNotizen(int r, int s) {
        int notizBreite = seitenlaenge / (int)Math.sqrt(anzahlFelder);
        paint.setTextSize(notizBreite);
        for(int notiz : feld[r][s].getNotizen()){
                int reiheInBox = (notiz-1)/(int)Math.sqrt(anzahlFelder);
                int spalteInBox = (notiz-1)%(int)Math.sqrt(anzahlFelder);
                float breite = paint.measureText(Integer.toString(notiz));
                canvas.drawText(""+notiz, s*seitenlaenge+(notizBreite*spalteInBox)+notizBreite/2-breite/2f, r*seitenlaenge+(notizBreite*reiheInBox)+notizBreite*9/10,paint);
        }
    }

    /**
     * Wird in der Activity durch den onTouchListener ausgelöst und berechnet die angeklickte Zelle
     */
    public void touch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int r = (int)Math.ceil(y/seitenlaenge)-1;
        int s = (int)Math.ceil(x/seitenlaenge)-1;

        if(r!= -1 && s!= -1){
            aktReihe = r;
            aktSpalte = s;
        }
    }

    // Setter und Getter der Variablen

    public void setzeAktZelle(int r, int s){
        aktReihe = r;
        aktSpalte = s;
        invalidate();
    }

    public void setFeld(Zelle[][] feld){
        this.anzahlFelder = feld.length;
        this.feld = feld;
        invalidate();
    }

    public void setPausiert(boolean pausiert){
        this.pausiert = pausiert;
    }

    public int getAktSpalte(){
        return aktSpalte;
    }

    public int getAktReihe(){
        return aktReihe;
    }


}
