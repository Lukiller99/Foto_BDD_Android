package com.example.aplicacionbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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