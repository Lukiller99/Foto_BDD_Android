package com.example.aplicacionbdd;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.TextView;


import static com.example.aplicacionbdd.MainActivity.titulo;
import static com.example.aplicacionbdd.MainActivity.ruta;
import static com.example.aplicacionbdd.MainActivity.descripcion;
public class Notificado extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView tv = new TextView(this);

        tv.setText(new String(titulo)+' '+new String(ruta)+' '+new String(descripcion));

        tv.setTextColor(Color.BLUE);
        setContentView(tv);



    }

}

