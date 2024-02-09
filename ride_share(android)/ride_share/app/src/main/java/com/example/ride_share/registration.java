package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registration extends AppCompatActivity {
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        b1=findViewById(R.id.button28);
        b2=findViewById(R.id.button30);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), userreg.class);
                startActivity(ik);



            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), cabreg.class);
                startActivity(ik);




            }
        });
    }
}