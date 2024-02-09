package com.example.ride_share;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class sendrating extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    Button b1;
    SharedPreferences sh;
    String url,wiid;
    RatingBar r1;
    ArrayList<String> gid,wrkshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendrating);
        s1=findViewById(R.id.spinner2);
        b1=findViewById(R.id.button24);
        r1 = findViewById(R.id.ratingBar);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rate = String.valueOf(r1.getRating());



                RequestQueue queue = Volley.newRequestQueue(sendrating.this);
                url = "http://" + sh.getString("ip", "") + ":5000/add_rating";

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
                                Toast.makeText(sendrating.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), driverhome.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(sendrating.this, "please try again", Toast.LENGTH_SHORT).show();

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

                        params.put("w_id", wiid);
                        params.put("rating", rate);
                        params.put("lid", sh.getString("lid", ""));


                        return params;
                    }
                };
                queue.add(stringRequest);

            }



        });
        url = "http://" + sh.getString("ip", "") + ":5000/viewworkshop";
        RequestQueue queue = Volley.newRequestQueue(sendrating.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    wrkshop = new ArrayList<>();
                    gid = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        wrkshop.add(jo.getString("name"));
                        gid.add(jo.getString("lid"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(sendrating.this,android.R.layout.simple_list_item_1,wrkshop);
                    s1.setAdapter(ad);
                    s1.setOnItemSelectedListener(sendrating.this);

//                    l1.setAdapter(new customview(notification.this, notification, date));
//                    l1.setOnItemClickListener(view_menu.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(sendrating.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        wiid = gid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}




