package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userhome extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        b1=findViewById(R.id.button13);
        b2=findViewById(R.id.button14);
        b3=findViewById(R.id.button15);
        b4=findViewById(R.id.button16);
        b5=findViewById(R.id.button17);
        b6=findViewById(R.id.button18);
        b7=findViewById(R.id.button19);
        b8=findViewById(R.id.button20);
        b9=findViewById(R.id.button35);
        b10=findViewById(R.id.button33);
        b11=findViewById(R.id.button34);
        b12=findViewById(R.id.button84);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewnearestcab.class);
                startActivity(ik);

            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), updatestatusofmyride.class);
                startActivity(ik);

            }
        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), complaintu.class);
                startActivity(ik);

            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), sendfeedback.class);
                startActivity(ik);

            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), shareride.class);
                startActivity(ik);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewride.class);
                startActivity(ik);

            }
        });


        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewrideandreq.class);
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


        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), login.class);
                startActivity(ik);

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), cabbookingstatus.class);
                startActivity(ik);



            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewworkshopstatus.class);
                startActivity(ik);

            }
        });

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), viewriderequeststatus.class);
                startActivity(ik);

            }
        });
    }

}