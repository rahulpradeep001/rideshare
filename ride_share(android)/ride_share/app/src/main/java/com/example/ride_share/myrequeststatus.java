package com.example.ride_share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

public class myrequeststatus extends AppCompatActivity {
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrequeststatus);
        l1=findViewById(R.id.list1);
    }
}