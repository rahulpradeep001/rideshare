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
import android.widget.EditText;
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

public class viewride extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    String url;
    SharedPreferences sh;
    ArrayList<String> user,fromto,date,time,contact,rid;
    String rrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewride);
        l1=findViewById(R.id.list99);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/view_ridereq";
        RequestQueue queue = Volley.newRequestQueue(viewride.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(viewride.this, response+"", Toast.LENGTH_SHORT).show();

                    JSONArray ar = new JSONArray(response);

                    user = new ArrayList<>();
                    fromto = new ArrayList<>();
                    date = new ArrayList<>();
//                    contact = new ArrayList<>();
                    rid = new ArrayList<>();
                    time = new ArrayList<>();




                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        user.add(jo.getString("firstname")+jo.getString("lastname"));
                        fromto.add(jo.getString("from")+"\n to "+jo.getString("to"));
                        date.add(jo.getString("date"));
                        time.add(jo.getString("time"));
//                        contact.add(jo.getString("contact"));
                        rid.add(jo.getString("req_id"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom4(viewride.this, user, fromto, date,time));
                    l1.setOnItemClickListener(viewride.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewride.this, "err" + error, Toast.LENGTH_SHORT).show();
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


        AlertDialog.Builder ald=new AlertDialog.Builder(viewride.this);
        ald.setTitle("Approve ")
                .setPositiveButton("Accept  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            ///////////////////////////////////////

                            RequestQueue queue = Volley.newRequestQueue(viewride.this);
                            url = "http://" + sh.getString("ip", "") + ":5000/accept";

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the response string.
                                    Log.d("+++++++++++++++++", response);
                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String res = json.getString("task");

                                        if (res.equalsIgnoreCase("valid")) {
                                            Toast.makeText(viewride.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), userhome.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(viewride.this, "please try again", Toast.LENGTH_SHORT).show();

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

                                    params.put("rid",rrid );


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
                .setNegativeButton("Reject ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestQueue queue = Volley.newRequestQueue(viewride.this);
                        url = "http://" + sh.getString("ip", "") + ":5000/reject";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String res = json.getString("task");

                                    if (res.equalsIgnoreCase("valid")) {
                                        Toast.makeText(viewride.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                        Intent ik = new Intent(getApplicationContext(), userhome.class);
                                        startActivity(ik);

                                    } else {

                                        Toast.makeText(viewride.this, "please try again", Toast.LENGTH_SHORT).show();

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

                                params.put("rid",rrid );


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