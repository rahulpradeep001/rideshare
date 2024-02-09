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

public class viewcabbooking extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedPreferences sh;
    ListView l1;
    String url;
    ArrayList<String> name,fromto,date,bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcabbooking);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.list78);
        url = "http://" + sh.getString("ip", "") + ":5000/view_booking";
        RequestQueue queue = Volley.newRequestQueue(viewcabbooking.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    name = new ArrayList<>();
                    fromto = new ArrayList<>();
                    date = new ArrayList<>();
                    bid = new ArrayList<>();



                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        name.add(jo.getString("firstname")+jo.getString("lastname"));
                        fromto.add(jo.getString("from")+"\n to "+jo.getString("to"));
                        date.add(jo.getString("date")+"\n"+jo.getString("time"));
                        bid.add(jo.getString("b_id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom(viewcabbooking.this, name, fromto, date));
                    l1.setOnItemClickListener(viewcabbooking.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewcabbooking.this, "err" + error, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),driverhome.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder ald=new AlertDialog.Builder(viewcabbooking.this);
        ald.setTitle("VERIFY BOOKING ")
                .setPositiveButton("ACCEPT  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            RequestQueue queue = Volley.newRequestQueue(viewcabbooking.this);
                            url = "http://" + sh.getString("ip", "") + ":5000/acceptcabbooking";

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
                                            Toast.makeText(viewcabbooking.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), viewcabbooking.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(viewcabbooking.this, "please try again", Toast.LENGTH_SHORT).show();

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
                                    params.put("bid", bid.get(position));


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
                .setNegativeButton("REJECT ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestQueue queue = Volley.newRequestQueue(viewcabbooking.this);
                        url = "http://" + sh.getString("ip", "") + ":5000/rejectcabbooking";

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
                                        Toast.makeText(viewcabbooking.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                        Intent ik = new Intent(getApplicationContext(), viewcabbooking.class);
                                        startActivity(ik);

                                    } else {

                                        Toast.makeText(viewcabbooking.this, "please try again", Toast.LENGTH_SHORT).show();

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
                                params.put("bid", bid.get(position));


                                return params;
                            }
                        };
                        queue.add(stringRequest);

                    }
                });


        AlertDialog al = ald.create();
        al.show();


    }
}