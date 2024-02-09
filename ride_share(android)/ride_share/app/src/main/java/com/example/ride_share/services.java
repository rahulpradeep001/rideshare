package com.example.ride_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class services extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    String  url;
    ArrayList<String> time, stop, charge,sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevice);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = findViewById(R.id.list33);
        url = "http://" + sh.getString("ip", "") + ":5000/view_service";
        RequestQueue queue = Volley.newRequestQueue(services.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    sid=new ArrayList<>();
                    time = new ArrayList<>();
                    stop = new ArrayList<>();
                    charge = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        time.add(jo.getString("service"));
                        stop.add(jo.getString("details"));
                        charge.add(jo.getString("cost"));
                        sid.add(jo.getString("s_id"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom(services.this, time, stop, charge));
                    l1.setOnItemClickListener(services.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(services.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("wid", sh.getString("w_id", ""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder ald=new AlertDialog.Builder(services.this);
        ald.setTitle("BOOK YOUR SERVICE")
                .setPositiveButton(" BOOK  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            RequestQueue queue = Volley.newRequestQueue(services.this);
                            url = "http://" + sh.getString("ip", "") + ":5000/book_service";

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
                                            Toast.makeText(services.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), services.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(services.this, "please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    catch (JSONException e) {
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


                                    params.put("lid", sh.getString("lid", ""));
                                    params.put("sid", sid.get(position));


                                    return params;
                                }
                            };
                            queue.add(stringRequest);


                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("CANCEL ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ik = new Intent(getApplicationContext(), nearestworkshop.class);
//                        ik.putExtra("lid", lid.get(position));
                        startActivity(ik);

                    }
                });


        AlertDialog al = ald.create();
        al.show();


    }
}
