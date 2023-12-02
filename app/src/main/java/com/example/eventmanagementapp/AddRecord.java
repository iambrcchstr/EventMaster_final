package com.example.eventmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AddRecord extends AppCompatActivity {

    EditText ev_name, ev_time, ev_date, ev_loc, ev_people, ev_type;

    ImageButton save_event, back_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);
        ev_name = findViewById(R.id.event_name_text);
        ev_time = findViewById(R.id.event_time_text);
        ev_date = findViewById(R.id.event_date_text);
        ev_loc = findViewById(R.id.event_loc_text);
        ev_type =findViewById(R.id.event_type_text);
        ev_people = findViewById(R.id.event_attendees_text);

        save_event = findViewById(R.id.save_event_btn);
        back_event = findViewById(R.id.back_btn);

        ev_date.setOnClickListener(view -> showCalendar());
        ev_time.setOnClickListener(view -> showClockTime());

        back_event.setOnClickListener(v -> startActivity(new Intent(AddRecord.this, MainActivity.class)));

        save_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_is = ev_name.getText().toString().trim();
                String time_is = ev_time.getText().toString().trim();
                String date_is = ev_date.getText().toString().trim();
                String loc_is = ev_loc.getText().toString().trim();
                String type_is = ev_type.getText().toString().trim();
                String people_is = ev_people.getText().toString().trim();

                if (name_is.isEmpty()) {
                    ev_name.setError( "Event name is required");
                } else if (time_is.isEmpty()) {
                    ev_time.setError( "Event time is required");
                } else if (date_is.isEmpty()) {
                    ev_date.setError( "Event date is required");
                } else if (loc_is.isEmpty()) {
                    ev_loc.setError("Event location is required");
                } else if (type_is.isEmpty()) {
                    ev_type.setError( "Event date is required");
                } else if (people_is.isEmpty()) {
                    ev_people.setError("Number of attendees is required");
                }
                else {
                    // Call the f_insert_record method
                    f_insert_record(name_is, time_is, date_is, loc_is,type_is, people_is);

                }
            }
        });
    }
    private void f_insert_record(String name_is, String time_is, String date_is, String loc_is, String type_is, String people_is) {
        HashMap<String,Object> data_hashmap = new HashMap<>();
        data_hashmap.put("b_name", name_is);
        data_hashmap.put("c_time", time_is);
        data_hashmap.put("d_date", date_is);
        data_hashmap.put("e_loc", loc_is);
        data_hashmap.put("f_type", type_is);
        data_hashmap.put("g_people", people_is);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tbl_reference = database.getReference("event");

        String idkey = tbl_reference.push().getKey();
        data_hashmap.put("a_idno", idkey);

        tbl_reference.child(idkey).setValue(data_hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddRecord.this, "Event Saved!", Toast.LENGTH_SHORT).show();
                ev_name.getText().clear();
                ev_date.getText().clear();
                ev_time.getText().clear();
                ev_loc.getText().clear();
                ev_type.getText().clear();
                ev_people.getText().clear();
                ev_name.requestFocus();

            }
        });
    }
    private void showCalendar() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = (monthOfYear + 1) +   "-" + dayOfMonth +  "-" + year1;
                    ev_date.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void showClockTime() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfDay) -> {
                    String selectedTime = hourOfDay + ":" + minuteOfDay;
                    ev_time.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }

}
