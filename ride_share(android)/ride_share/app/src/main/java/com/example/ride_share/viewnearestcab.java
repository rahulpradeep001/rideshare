package com.example.ride_share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewnearestcab extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> workshop,lati,longi,phone,w_id,address;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnearestcab);
        l1=findViewById(R.id.list45);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url = "http://" + sh.getString("ip", "") + ":5000/viewnearestcab";
        RequestQueue queue = Volley.newRequestQueue(viewnearestcab.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    workshop = new ArrayList<>();
                    address = new ArrayList<>();
                    phone = new ArrayList<>();
                    lati=new ArrayList<>();
                    longi=new ArrayList<>();
                    w_id=new ArrayList<>();



                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        workshop.add(jo.getString("Firstname")+jo.getString("lastname"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));
                        phone.add(jo.getString("contact"));
                        address.add(jo.getString("email"));
                        w_id.add(jo.getString("lid"));




                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<>(nearestworkshop.this,android.R.layout.simple_list_item_1,workshop);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new custom(viewnearestcab.this, workshop, phone, address));
                    l1.setOnItemClickListener(viewnearestcab.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewnearestcab.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("lid", sh.getString("lid", ""));
                params.put("lattitude",LocationService.lati);
                params.put("longitude",LocationService.logi);
//                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);

    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        AlertDialog.Builder ald=new AlertDialog.Builder(viewnearestcab.this);
        ald.setTitle("BOOK CAB OR TRACK  ")
                .setPositiveButton("TRACK  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            Intent ik = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps?q="+lati.get(position)+","+longi.get(position)));
                            startActivity(ik);

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("BOOK NOW ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ik = new Intent(getApplicationContext(),bookcab.class);
                        SharedPreferences.Editor ed = sh.edit();
                        ed.putString("cid",w_id.get(position) );
                        ed.commit();
                        startActivity(ik);

                    }
                });


        AlertDialog al = ald.create();
        al.show();

    }
}
