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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bookcab extends AppCompatActivity {
    EditText e1, e2, e3;
    Button b1;
    SharedPreferences sh;
    String start, end, time, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcab);
        e1 = findViewById(R.id.editTextTextPersonName5);
        e2= findViewById(R.id.editTextTextPersonName6);
        e3 = findViewById(R.id.editTextTextPersonName8);
        b1 = findViewById(R.id.button25);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                start = e1.getText().toString();
                end = e2.getText().toString();
                time = e3.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(bookcab.this);
                url = "http://" + sh.getString("ip", "") + ":5000/book_cab";

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
                                Toast.makeText(bookcab.this, "send successfully ", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), viewnearestcab.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(bookcab.this, "please try again", Toast.LENGTH_SHORT).show();

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


                        params.put("lid", sh.getString("lid", ""));
                        params.put("cid", sh.getString("cid", ""));
                        params.put("start", start);
                        params.put("end", end);
                        params.put("time", time);


                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
}
