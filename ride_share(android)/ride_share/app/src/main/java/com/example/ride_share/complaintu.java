package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class complaintu extends AppCompatActivity {
    ListView l1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> complaint,date,reply;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintu);
        l1=findViewById(R.id.list28);
        b1=findViewById(R.id.button21);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url = "http://" + sh.getString("ip", "") + ":5000/viewreply";
        RequestQueue queue = Volley.newRequestQueue(complaintu.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    complaint = new ArrayList<>();
                    date = new ArrayList<>();
                    reply = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        complaint.add(jo.getString("complaint"));
                        date.add(jo.getString("date"));
                        reply.add(jo.getString("reply"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom(complaintu.this, complaint, date, reply));
//                    l1.setOnItemClickListener(view_menu.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(complaintu.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), sendcomplaintu.class);
                startActivity(ik);

            }

        });

    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
    }
}






