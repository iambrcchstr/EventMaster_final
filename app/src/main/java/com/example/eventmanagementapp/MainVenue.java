package com.example.eventmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainVenue extends AppCompatActivity {
    ImageButton backbton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        backbton = findViewById(R.id.back_venue_btn);

        backbton.setOnClickListener(v -> startActivity(new Intent(MainVenue.this, MainActivity.class)));


    }
}