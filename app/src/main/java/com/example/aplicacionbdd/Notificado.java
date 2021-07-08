package com.example.aplicacionbdd;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Notificado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String ultimoRegistro = getIntent().getStringExtra("ultimoRegistro");
        String titulo = getIntent().getStringExtra("titulo");
        String ruta = getIntent().getStringExtra("ruta");
        String descripcion = getIntent().getStringExtra("descripcion");
        TextView tv = new TextView(this);
        tv.setText(new String(ultimoRegistro));
        tv.setText(new String(titulo));
        tv.setText(new String(ruta));
        tv.setText(new String(descripcion));
        tv.setTextColor(Color.BLUE);
        setContentView(tv);

    }
}
