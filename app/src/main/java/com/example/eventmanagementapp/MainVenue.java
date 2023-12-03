package com.example.eventmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainVenue extends AppCompatActivity {
    ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_package);

        backbtn = findViewById(R.id.back_venue);

        backbtn.setOnClickListener(v -> startActivity(new Intent(MainVenue.this, MainActivity.class)));


    }
}