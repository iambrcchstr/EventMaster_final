package com.example.eventmanagementapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addEventBtn;
    RecyclerView recyclerView;
    Button btnPackage, btnVenue;
    MainAdapter v_mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEventBtn = findViewById(R.id.add_event_button);
        btnPackage=findViewById(R.id.btn_package);
        btnVenue =findViewById(R.id.btn_venue);
        recyclerView = findViewById(R.id.rv_id);

        addEventBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddRecord.class)));
        btnPackage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainPackage.class)));
        btnVenue.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainVenue.class)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("event"), MainModel.class)
                        .build();

        v_mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(v_mainAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        v_mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        v_mainAdapter.stopListening();
    }
}