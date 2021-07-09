package com.example.aplicacionbdd;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import static com.example.aplicacionbdd.MainActivity.descripcion;
import static com.example.aplicacionbdd.MainActivity.titulo;
import static com.example.aplicacionbdd.MainActivity.ruta;
public class GetSomeService extends IntentService {


    public GetSomeService() {
        super("GetSomeService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        try {
            notificar(titulo,descripcion,ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void notificar(String titulo,String descripcion,String ruta) {
        final int NOTIF_ID = 1;
        final int REQ_CODE = 2;

        Notification.Builder notifB =  new Notification.Builder(this, "IdPrueba");
        notifB.setSmallIcon(R.drawable.ic_stat_name);
        notifB.setContentTitle("Actualización!");
        notifB.setContentText("Hay nueva información del servicio");
        notifB.setAutoCancel(true);

        Intent intent = new Intent(this, Notificado.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("descripcion", descripcion);
        intent.putExtra("ruta", ruta);

        notifB.setContentIntent(PendingIntent.getActivity(this, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        Notification notif = notifB.build();

        NotificationManager notifMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifMgr.notify(NOTIF_ID, notif);
    }


}

