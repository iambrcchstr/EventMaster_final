package com.example.eventmanagementapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addEventBtn;
    RecyclerView recyclerView;
    MainAdapter v_mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_id);
        addEventBtn = findViewById(R.id.add_event_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addEventBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddRecord.class)));

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