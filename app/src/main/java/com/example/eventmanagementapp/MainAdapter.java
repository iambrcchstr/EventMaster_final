package com.example.eventmanagementapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getB_name());
        holder.time.setText(model.getC_time());
        holder.date.setText(model.getD_date());
        holder.loc.setText(model.getE_loc());
        holder.peeps.setText(model.getG_people());
        holder.type.setText(model.getF_type());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Deleting this event will delete its data. " +
                        "Are you sure you want to make changes?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String key = getRef(position).getKey();
                        FirebaseDatabase.getInstance().getReference("event")
                                .child(key).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(builder.getContext(), "Deletion cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();

            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_event))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.edit_name_text);
                EditText time = view.findViewById(R.id.edit_time_text);
                EditText date = view.findViewById(R.id.edit_date_text);
                EditText loc = view.findViewById(R.id.edit_loc_text);
                EditText peeps = view.findViewById(R.id.edit_attendees_text);
                EditText type = view.findViewById(R.id.edit_type_text);

                ImageButton update = view.findViewById(R.id.edit_update_event);
                ImageButton cancel = view.findViewById(R.id.edit_back_btn);

                name.setText(model.getB_name());
                time.setText(model.getC_time());
                date.setText(model.getD_date());
                loc.setText(model.getE_loc());
                type.setText(model.getF_type());
                peeps.setText(model.getG_people());
                dialogPlus.show();

                Calendar calendar = Calendar.getInstance();

                // Set initial date and time
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        date.setText(formattedDate);
                    }
                }, year, month, day);

                // Create TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = hourOfDay + ":" + minute;
                        time.setText(formattedTime);
                    }
                }, hour, minute, false);

                // Open DatePickerDialog when date edit text is clicked
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                // Open TimePickerDialog when time edit text is clicked
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerDialog.show();
                    }
                });


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_is = name.getText().toString().trim();
                        String time_is = time.getText().toString().trim();
                        String date_is = date.getText().toString().trim();
                        String loc_is = loc.getText().toString().trim();
                        String type_is = type.getText().toString().trim();
                        String peeps_is = peeps.getText().toString().trim();

                        if (name_is.isEmpty()) {
                            name.setError("Event name is required");
                        } else if (time_is.isEmpty()) {
                            time.setError("Event time is required");
                        } else if (date_is.isEmpty()) {
                            date.setError("Event date is required");
                        } else if (loc_is.isEmpty()) {
                            loc.setError("Event location is required");
                        } else if (type_is.isEmpty()) {
                            type.setError("Event date is required");
                        } else if (peeps_is.isEmpty()) {
                            peeps.setError("Number of attendees is required");
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("b_name", name_is);
                            map.put("c_time", time_is);
                            map.put("d_date", date_is);
                            map.put("e_loc", loc_is);
                            map.put("f_type", type_is);
                            map.put("g_people", peeps_is);

                            FirebaseDatabase.getInstance().getReference().child("event")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.name.getContext(), "Event Updated!", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss(); // Dismiss the dialog after successful update
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.name.getContext(), "Error updating event", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.name.getContext(), MainActivity.class);
                        holder.name.getContext().startActivity(intent);
                    }
                });
            }
        });
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    private OnEditClickListener editClickListener;

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, time, loc, peeps,type;
        ImageButton btnDelete, btnEdit;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            date = itemView.findViewById(R.id.date_txt);
            time = itemView.findViewById(R.id.time_txt);
            loc = itemView.findViewById(R.id.location_txt);
            peeps = itemView.findViewById(R.id.num_txt);
            type = itemView.findViewById(R.id.type_txt);

            btnDelete = itemView.findViewById(R.id.imgBtn_Delete);
            btnEdit = itemView.findViewById(R.id.imgBtn_Edit);


        }

}
}

