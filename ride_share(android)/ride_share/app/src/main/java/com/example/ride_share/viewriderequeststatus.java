package com.example.ride_share;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewriderequeststatus extends AppCompatActivity {
    ListView l1;
    String url;
    SharedPreferences sh;
    ArrayList<String> user,from,to,date,time,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewriderequeststatus);
        l1=findViewById(R.id.list103);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/view_riderequeststatus";
        RequestQueue queue = Volley.newRequestQueue(viewriderequeststatus.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(viewriderequeststatus.this, response+"", Toast.LENGTH_SHORT).show();

                    JSONArray ar = new JSONArray(response);

                    user = new ArrayList<>();
                    from = new ArrayList<>();
                    to = new ArrayList<>();
                    date = new ArrayList<>();
                    time = new ArrayList<>();
                    status = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        user.add(jo.getString("firstname"));
                        from.add(jo.getString("from"));
                        to.add(jo.getString("to"));
                        date.add(jo.getString("rdate"));
                        time.add(jo.getString("time"));
                        status.add(jo.getString("status"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom6(viewriderequeststatus.this, user, from,to, date,time,status));
//                    l1.setOnItemClickListener(viewrequest.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewriderequeststatus.this, "err" + error, Toast.LENGTH_SHORT).show();
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
}