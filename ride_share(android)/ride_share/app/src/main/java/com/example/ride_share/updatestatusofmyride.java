package com.example.ride_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class updatestatusofmyride extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedPreferences sh;
    String url;
    ListView l1;
    ArrayList<String>rid,from,to,date,status;
    String rrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestatusofmyride);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.list24);
        url = "http://" + sh.getString("ip", "") + ":5000/viewmyride";
        RequestQueue queue = Volley.newRequestQueue(updatestatusofmyride.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    from = new ArrayList<>();
                    to = new ArrayList<>();
                    date = new ArrayList<>();
                    status = new ArrayList<>();
                    rid = new ArrayList<>();




                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        from.add(jo.getString("from"));
                        to.add(jo.getString("to"));
                        date.add(jo.getString("date"));
                        status.add(jo.getString("status"));
                        rid.add(jo.getString("r_id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom4(updatestatusofmyride.this, from, to, date,status));
                    l1.setOnItemClickListener(updatestatusofmyride.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(updatestatusofmyride.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        rrid=rid.get(i);

//
        AlertDialog.Builder ald=new AlertDialog.Builder(updatestatusofmyride.this);
        ald.setTitle("update ")
                .setPositiveButton("completed  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
               ///////////////////////////////////////

                            RequestQueue queue = Volley.newRequestQueue(updatestatusofmyride.this);
                            url = "http://" + sh.getString("ip", "") + ":5000/updatestatus";

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the response string.
                                    Log.d("+++++++++++++++++", response);
                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String res = json.getString("task");

                                        if (res.equalsIgnoreCase("success")) {
                                            Toast.makeText(updatestatusofmyride.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), userhome.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(updatestatusofmyride.this, "please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                    Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("r_id",rrid );


                                    return params;
                                }
                            };
                            queue.add(stringRequest);







                            /////////////////////////////
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent ik = new Intent(getApplicationContext(), updatestatusofmyride.class);

                        startActivity(ik);

                    }
                });


        AlertDialog al = ald.create();
        al.show();


    }
}