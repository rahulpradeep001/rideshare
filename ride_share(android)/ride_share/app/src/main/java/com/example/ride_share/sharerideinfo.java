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

import java.util.HashMap;
import java.util.Map;

public class sharerideinfo extends AppCompatActivity {
    Button b1;
    ListView l1;
    String url,rideinfo;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharerideinfo);
        b1=findViewById(R.id.button36);
        l1=findViewById(R.id.list3);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }


        });
    }
}