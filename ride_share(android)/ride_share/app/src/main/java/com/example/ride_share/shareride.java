package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class shareride extends AppCompatActivity {
    Button b1;
    EditText e1,e2,e3,e4;
    String from,to,date,time,url;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareride);
        b1=findViewById(R.id.button32);
        e1=findViewById(R.id.editTextTextPersonName19);
        e2=findViewById(R.id.editTextTextPersonName30);
        e3=findViewById(R.id.editTextTextPersonName31);
        e4=findViewById(R.id.editTextTextPersonName78);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = e1.getText().toString();
                to = e2.getText().toString();
                date = e3.getText().toString();
                time = e4.getText().toString();

                if (from.equalsIgnoreCase("")) {
                    e1.setError("enter your starting location");
                } else if (!from.matches(("^[a-z A-z]*$"))) {
                    e1.setError(" characters  allowed");
                }
                else if (to.equalsIgnoreCase("")) {
                    e2.setError("enter your ending location");
                } else if (!to.matches(("^[a-z A-z]*$"))) {
                    e2.setError(" characters  allowed");
                }
                else if (date.equalsIgnoreCase("")) {
                    e3.setError("enter your date");
                }
                else if (time.equalsIgnoreCase("")) {
                    e4.setError("enter your time");
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(shareride.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/share_ride";

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
                                    Toast.makeText(shareride.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(shareride.this, "please try again", Toast.LENGTH_SHORT).show();

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

                            params.put("from", from);
                            params.put("to", to);
                            params.put("date", date);
                            params.put("time", time);
                            params.put("lid", sh.getString("lid", ""));
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });

    }
}