package com.example.momento2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    EditText jetcod_mascota,jetnom_mascota,jetedad_mascota,jetnom_propietario;
    Button jbtregistrar,jbtconsultar,jbteditar,jbteliminar;
    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        jetcod_mascota = findViewById(R.id.etcod_mascota);
        jetnom_mascota = findViewById(R.id.etnom_mascota);
        jetedad_mascota = findViewById(R.id.etedad_mascota);
        jetnom_propietario = findViewById(R.id.etnom_propietario);
        jbtregistrar = findViewById(R.id.btregistrar);
        jbtconsultar = findViewById(R.id.btconsultar);
        jbteditar=findViewById(R.id.bteditar);
        jbteliminar = findViewById(R.id.bteliminar);
        rq = Volley.newRequestQueue(this);

        jbtregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_mascota();
            }
        });

        jbtconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar_mascota();
            }
        });

        jbteditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar_mascota();
            }
        });

        jbteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_mascota();
            }
        });

    }

    public void registrar_mascota() {
        String url = "http://172.16.59.249:8081/veterinaria/registromascota.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jetcod_mascota.setText("");
                        jetnom_mascota.setText("");
                        jetedad_mascota.setText("");
                        jetnom_propietario.setText("");
                        jetcod_mascota.requestFocus();
                        Toast.makeText(getApplicationContext(), "Registro de mascota realizado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registro de mascota incorrecto!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod_mascota",jetcod_mascota.getText().toString().trim());
                params.put("nom_mascota", jetnom_mascota.getText().toString().trim());
                params.put("edad_mascota",jetedad_mascota.getText().toString().trim());
                params.put("nom_propietario",jetnom_propietario.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void consultar_mascota()
    {
        String url = "http://172.16.59.249:8081/veterinaria/consulta.php?cod_mascota="+jetcod_mascota.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this,"No se ha encontrado la mascota "+jetcod_mascota.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {

        ClsMascota mascota = new ClsMascota();
        Toast.makeText(this,"Se ha encontrado la mascota "+jetcod_mascota.getText().toString(),Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            jetcod_mascota.setText(jsonObject.optString("cod_mascota"));
            jetnom_mascota.setText(jsonObject.optString("nom_mascota"));
            jetedad_mascota.setText(jsonObject.optString("edad_mascota"));
            jetnom_propietario.setText(jsonObject.optString("nom_propietario"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }



    }

    public void editar_mascota() {
        String url = "http://172.16.59.249:8081/veterinaria/actualiza.php?cod_mascota"+jetcod_mascota.getText().toString();;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jetcod_mascota.setText("");
                        jetnom_mascota.setText("");
                        jetedad_mascota.setText("");
                        jetnom_propietario.setText("");
                        jetcod_mascota.requestFocus();
                        Toast.makeText(getApplicationContext(), "Mascota actualizada correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error actualizando mascota!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod_mascota",jetcod_mascota.getText().toString().trim());
                params.put("nom_mascota", jetnom_mascota.getText().toString().trim());
                params.put("edad_mascota",jetedad_mascota.getText().toString().trim());
                params.put("nom_propietario",jetnom_propietario.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void eliminar_mascota() {
        String url = "http://172.16.59.249:8081/veterinaria/elimina.php?cod_mascota="+jetcod_mascota.getText().toString();;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jetcod_mascota.setText("");
                        jetnom_mascota.setText("");
                        jetedad_mascota.setText("");
                        jetnom_propietario.setText("");
                        jetcod_mascota.requestFocus();
                        Toast.makeText(getApplicationContext(), "Mascota eliminada!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error eliminando mascota!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr",jetcod_mascota.getText().toString().trim());
                params.put("nombre", jetedad_mascota.getText().toString().trim());
                params.put("correo",jetedad_mascota.getText().toString().trim());
                params.put("clave",jetnom_propietario.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }



}