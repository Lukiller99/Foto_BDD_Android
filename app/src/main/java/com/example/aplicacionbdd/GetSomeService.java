package com.example.aplicacionbdd;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetSomeService extends IntentService {


    public GetSomeService() {
        super("GetSomeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {


            String clima="asdasdasd";
            String clima2="aaaaaa";
            notificar(clima,clima2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notificar(String clima,String clima2) {
        final int NOTIF_ID = 1;
        final int REQ_CODE = 2;

        Notification.Builder notifB =  new Notification.Builder(this, "IdPrueba");
        notifB.setSmallIcon(R.drawable.ic_stat_name);
        notifB.setContentTitle("Actualización!!!");
        notifB.setContentText("Hay nueva información del servicio");
        notifB.setAutoCancel(true);

        Intent intent = new Intent(this, Notificado.class);
        intent.putExtra("clima", clima);
        intent.putExtra("clima2", clima2);
        notifB.setContentIntent(PendingIntent.getActivity(this, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        Notification notif = notifB.build();

        NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifMgr.notify(NOTIF_ID, notif);
    }

}

