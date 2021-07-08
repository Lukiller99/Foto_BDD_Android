package com.example.aplicacionbdd;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Notificado extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String clima = getIntent().getStringExtra("clima");
        String clima2 = getIntent().getStringExtra("clima2");
        TextView tv = new TextView(this);
        TextView tv2 = new TextView(this);
        tv.setText(new String(clima)+' '+new String(clima2));

        tv.setTextColor(Color.BLUE);
        setContentView(tv);
//        tv2.setText(new String(clima2));
//        tv2.setTextColor(Color.BLUE);
//
//
//        setContentView(tv2);

    }

}

