package com.example.eventmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainPackage extends AppCompatActivity {

    ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_package);

        backbtn = findViewById(R.id.back_package);

        backbtn.setOnClickListener(v -> startActivity(new Intent(MainPackage.this, MainActivity.class)));

        //// YUNG BACKGROUND SA VENUE XML PALITAN KO, PERO AYUN NA YUNG PACKAGES
    }
}