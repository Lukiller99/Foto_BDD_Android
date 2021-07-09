package com.example.aplicacionbdd.Helpers;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
public class NotificacionHelper extends Application {
    public static final String CANAL_ID="canal1";
    private static final String CANAL_NOMBRE="Canal 1";
    private static final String CANAL_DESCRIPCION="Desc Canal 1";

    @Override
    public void onCreate() {
        super.onCreate();
        crearCanalesNotificaciones();
    }

    private void crearCanalesNotificaciones() {

            NotificationChannel canal1= new NotificationChannel(
                    CANAL_ID,
                    CANAL_NOMBRE,
                    NotificationManager.IMPORTANCE_HIGH
            );

            canal1.setDescription(CANAL_DESCRIPCION);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal1);

    }
}
