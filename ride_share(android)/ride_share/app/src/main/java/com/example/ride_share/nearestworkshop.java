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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nearestworkshop extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> workshop,lati,longi,phone,w_id,address;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearestworkshop);
        l1=findViewById(R.id.list50);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url = "http://" + sh.getString("ip", "") + ":5000/viewnearestworskshop";
        RequestQueue queue = Volley.newRequestQueue(nearestworkshop.this);

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
                        workshop.add(jo.getString("name"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));
                        phone.add(jo.getString("contact"));
                        address.add(jo.getString("place")+"\n"+jo.getString("post")+"\n"+jo.getString("pin"));
                        w_id.add(jo.getString("lid"));




                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<>(nearestworkshop.this,android.R.layout.simple_list_item_1,workshop);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new custom(nearestworkshop.this, workshop, address, phone));
                    l1.setOnItemClickListener(nearestworkshop.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(nearestworkshop.this, "err" + error, Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        AlertDialog.Builder ald=new AlertDialog.Builder(nearestworkshop.this);
        ald.setTitle("VIEW SERVICES OR TRACK WORKSHOP ")
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
                .setNegativeButton("SERVICES ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent ik = new Intent(getApplicationContext(), services.class);
                        SharedPreferences.Editor ed = sh.edit();
                        ed.putString("w_id",w_id.get(position) );
                        ed.commit();
                        startActivity(ik);

                    }
                });


        AlertDialog al = ald.create();
        al.show();

    }
}
