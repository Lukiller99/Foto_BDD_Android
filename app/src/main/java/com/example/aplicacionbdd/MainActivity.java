package com.example.aplicacionbdd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    Button btnCamara,btnEnviar,btnBuscar;
    TextView txtTitulo,txtDesc,txtRuta,txtId;
    ImageView imgView;
    String rutaImagen ;
    ProgressDialog progeso;
    RequestQueue request;
    File imagenArchivo;
    Bitmap imgBitmap;
    Uri fotoUri;
    StringRequest stringRequest;
    String imagenString;
    NotificationChannel channel;
    String url,urlImagen;
    JsonObjectRequest  jsonObjectRequest;
    private static final int notid = 1;

    public static String titulo,descripcion,ruta;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        btnCamara = findViewById(R.id.btnCamara);

        btnEnviar = findViewById(R.id.btnEnviar);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDesc = findViewById(R.id.txtDesc);
        txtRuta=findViewById(R.id.txtRuta);
        txtId=findViewById(R.id.txtId);
        imgView = findViewById(R.id.viewFoto);
        request= Volley.newRequestQueue(this);
        btnCamara.setOnClickListener(view -> {
            abrirCamara();
            txtId.setText("");
            txtDesc.setText("");
            txtTitulo.setText("");

        });
       btnEnviar.setOnClickListener(view -> cargarServicioWeb());
       btnBuscar.setOnClickListener(view -> {
           Intent intent = new Intent(this,Consulta.class);
           startActivity(intent);

       });

        onMapReady();
        creaAlarma();
    }

    public void onMapReady() {
        ejecutarTarea();
    }

    private final int TIEMPO = 50000;
    public void ejecutarTarea() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                actualiza(); // función para refrescar la ubicación del conductor, creada en otra línea de código

                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }
    public void creaAlarma() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 46);

     alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000*60, alarmPendingIntent);

    }

    private void createNotificationChannel() {
        channel = new NotificationChannel("IdPrueba", "CanalPrueba", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Canal de pruebas");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imagenArchivo=null;
        try {
            imagenArchivo=crearImagen();
        }catch(IOException ex){
            Log.e("Error", ex.toString());
        }
        if(imagenArchivo!=null){
            fotoUri= FileProvider.getUriForFile(this,"com.cdp.camara.fileprovider",imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
            startActivityForResult(intent, 1);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imgBitmap);
            txtRuta.setText(rutaImagen);
        }
    }
    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen= imagen.getAbsolutePath();
        return imagen;
    }
    private void actualiza() {
        progeso=new ProgressDialog(this);
        progeso.setMessage("Cargando...");
        progeso.show();

        url="http://192.168.1.133/BDAndroid/ActualizaRegistro.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progeso.hide();
                JSONArray json =response.optJSONArray("fotos");
                JSONObject jsonObject=null;
                try {
                    jsonObject=json.getJSONObject(0);

                    titulo=jsonObject.optString("titulo");
                    descripcion=jsonObject.optString("descripcion");
                    ruta=jsonObject.optString("ruta");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                urlImagen="http://192.168.1.133/BDAndroid/"+txtRutaC.getText();
//                Toast.makeText(getApplicationContext(),"URL: "+urlImagen,Toast.LENGTH_SHORT).show();
//                cargarWebServiceImagen(urlImagen);
            }
//            private void cargarWebServiceImagen(String urlImagen) {
//                urlImagen = urlImagen.replace(" ","%20");
//                ImageRequest imageRequest = new ImageRequest(urlImagen, response -> viewFotoC.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, null,
//                        error -> Toast.makeText(getApplicationContext(),"No se puede cargar la imagen "+error.toString(),Toast.LENGTH_SHORT).show());
//                request.add(imageRequest);
//            }
        }, error -> {
            Toast.makeText(getApplicationContext(),"No se puede conectar: "+error.toString(),Toast.LENGTH_SHORT).show();
            System.out.println();
            progeso.hide();
            Log.d("ERROR",error.toString());
        });
        request.add(jsonObjectRequest);
    }

    private void cargarServicioWeb() {
         progeso=new ProgressDialog(this);
         progeso.setMessage("Cargando...");
         progeso.show();

         String url="http://192.168.1.133/BDAndroid/JSONRegistro.php?";

         stringRequest= new StringRequest(Request.Method.POST, url, response -> {
            progeso.hide();

            if (response.trim().equalsIgnoreCase("registrado")){
                txtId.setText("");
                txtTitulo.setText("");
                txtDesc.setText("");
                Toast.makeText(getApplicationContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"No se ha registrado con exito",Toast.LENGTH_SHORT).show();
                progeso.hide();
            }
         }, error -> {
             Toast.makeText(getApplicationContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
             progeso.hide();
         }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 String id = txtId.getText().toString();
                 String titulo=txtTitulo.getText().toString();
                 String descripcion=txtDesc.getText().toString();
                 String foto=conversorImgString(imgBitmap);
                 Map<String,String> parametros = new HashMap<>();
                 parametros.put("id",id);
                 parametros.put("titulo",titulo);
                 parametros.put("descripcion",descripcion);
                 parametros.put("foto",foto);
                 return parametros;
             }
         };
         request.add(stringRequest);
     }
    private String conversorImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }


}