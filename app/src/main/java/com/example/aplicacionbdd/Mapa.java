package com.example.aplicacionbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;

import static com.example.aplicacionbdd.MainActivity.v1;
import static com.example.aplicacionbdd.MainActivity.v2;

public class Mapa extends AppCompatActivity {

    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        web = (WebView) findViewById(R.id.web);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://www.android.com");


        muestraUbicacion();

    }

    private void muestraUbicacion() {
        if (v1 != null) {
            Toast.makeText(this, "Ubicacion obtenida", Toast.LENGTH_SHORT).show();

            String url = "https://www.google.com/maps/@" +
                    v1 + "," +
                    v2 + ",15z"; // z es el nivel de zoom
            web.loadUrl(url);
        } else {
            Toast.makeText(this, "El GPS aun no obtiene la ubicacion", Toast.LENGTH_SHORT).show();
        }

    }


}