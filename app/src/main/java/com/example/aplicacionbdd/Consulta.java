package com.example.aplicacionbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;

public class Consulta extends AppCompatActivity   {
    Button btnBuscarC;
    TextView txtIDC,txtTituloC,txtDescripcionC,txtRutaC;
    ProgressDialog progeso;
    ImageView viewFotoC ;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String urlImagen,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        btnBuscarC=findViewById(R.id.btnBuscarC);
        txtIDC=findViewById(R.id.txtIDC);
        txtTituloC=findViewById(R.id.txtTituloC);
        txtRutaC=findViewById(R.id.txtRutaC);
        txtDescripcionC=findViewById(R.id.txtDescripcionC);
        viewFotoC=findViewById(R.id.viewFotoC);
        request= Volley.newRequestQueue(this);

        btnBuscarC.setOnClickListener(view -> cargarServidorWeb());

    }
    private void comprobar (){

    }

    private void cargarServidorWeb() {
        progeso=new ProgressDialog(this);
        progeso.setMessage("Cargando...");
        progeso.show();

        url="http://192.168.1.133/BDAndroid/JSONConsulta.php?id="+txtIDC.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progeso.hide();
                JSONArray json =response.optJSONArray("fotos");
                JSONObject jsonObject=null;
                try {
                    jsonObject=json.getJSONObject(0);
                    txtTituloC.setText(jsonObject.optString("titulo"));
                    txtDescripcionC.setText(jsonObject.optString("descripcion"));
                    txtRutaC.setText(jsonObject.optString("ruta"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                urlImagen="http://192.168.1.133/BDAndroid/"+txtRutaC.getText();
                Toast.makeText(getApplicationContext(),"URL: "+urlImagen,Toast.LENGTH_SHORT).show();
                cargarWebServiceImagen(urlImagen);
            }
            private void cargarWebServiceImagen(String urlImagen) {
                urlImagen = urlImagen.replace(" ","%20");
                ImageRequest imageRequest = new ImageRequest(urlImagen, response -> viewFotoC.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, null,
                        error -> Toast.makeText(getApplicationContext(),"No se puede cargar la imagen "+error.toString(),Toast.LENGTH_SHORT).show());
                request.add(imageRequest);
            }
        }, error -> {
            Toast.makeText(getApplicationContext(),"No se puede conectar: "+error.toString(),Toast.LENGTH_SHORT).show();
            System.out.println();
            progeso.hide();
            Log.d("ERROR",error.toString());
        });
        request.add(jsonObjectRequest);

    }

}