package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class driverhome extends AppCompatActivity {
    Button b1,b2,b5,b6,b7,b8,b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverhome);

        b1=findViewById(R.id.button4);
        b2=findViewById(R.id.button5);
        b5=findViewById(R.id.button8);
        b6=findViewById(R.id.button9);
        b7=findViewById(R.id.button6);
        b8=findViewById(R.id.button7);
        b9=findViewById(R.id.button10);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewcabbooking.class);
                startActivity(ik);

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewbookingstatus.class);
                startActivity(ik);

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), sendrating.class);
                startActivity(ik);

            }
        });


        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), nearestworkshop.class);
                startActivity(ik);

            }
        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), complaint.class);
                startActivity(ik);

            }
        });


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewfeedback.class);
                startActivity(ik);

            }
        });


        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), login.class);
                startActivity(ik);

            }
        });

    }
}