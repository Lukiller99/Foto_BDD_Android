package com.example.aplicacionbdd;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aplicacionbdd.Consulta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

//    private void Actualizacion() {
//        url = "http://192.168.1.133/BDAndroid/.php?id=3";
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                JSONArray json = response.optJSONArray("fotos");
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = json.getJSONObject(0);
//                    titulo = jsonObject.optString("titulo");
//                    descripcion = jsonObject.optString("descripcion");
//                    ruta = jsonObject.optString("ruta");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////                    urlImagen="http://192.168.1.133/BDAndroid/"+txtRutaC.getText();
////                    Toast.makeText(getApplicationContext(),"URL: "+urlImagen,Toast.LENGTH_SHORT).show();
////                    cargarWebServiceImagen(urlImagen);
//            }
//
//
////                private void cargarWebServiceImagen(String urlImagen) {
////                    urlImagen = urlImagen.replace(" ","%20");
////                    ImageRequest imageRequest = new ImageRequest(urlImagen, response -> viewFotoC.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, null,
////                            error -> Toast.makeText(getApplicationContext(),"No se puede cargar la imagen "+error.toString(),Toast.LENGTH_SHORT).show());
////                    request.add(imageRequest);
////                }
//        }, error -> {
//            Toast.makeText(getApplicationContext(), "No se puede conectar: " + error.toString(), Toast.LENGTH_SHORT).show();
//            System.out.println();
//
//            Log.d("ERROR", error.toString());
//        });
//
//        request.add(jsonObjectRequest);
//    }


}

