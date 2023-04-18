package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class modifyAppointment extends AppCompatActivity {

    Spinner storeSpinner, reasenSprinner ;
    private String reasenChoice;
    private String dateChoice;
    private String timeChoice;
    private String storeChoice;
    private String month;
    public String description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_appointment);

        DatabaseReference GS_DbRef = FirebaseDatabase.getInstance().getReference().child("appointment");


        //recuperation des données a partir de manageMyApp
        String text = getIntent().getStringExtra("description");
        String fullname = getIntent().getStringExtra("fullname");
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        String store = getIntent().getStringExtra("store");
        String reason = getIntent().getStringExtra("reason");



        //initialisation + prefill des données
        EditText getDescription = findViewById(R.id.description);
        getDescription.setText(text);

        EditText getFullname= findViewById(R.id.fullNameInput);
        getFullname.setText(fullname);

        Button getTime = findViewById(R.id.idBtnPickTime);
        getTime.setText(time);

        EditText getDate= findViewById(R.id.datePicker);
        getDate.setText(date);


        //prefill du spinner 
        storeSpinner = findViewById(R.id.spinnerStorePicker);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        //drop down menu store picker
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //liste stores
        String[] items = new String[]{"Select a store","anjou 084", "marcher central 992", "centre ville 651"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        storeSpinner.setAdapter(adapter);
        
        if (store.equals("anjou 084")){
            storeSpinner.setSelection(1);
        }
        else if (store.equals("marcher central 992")){
            storeSpinner.setSelection(2);
        }
        else if (store.equals("centre ville 651")){
            storeSpinner.setSelection(3);
        }

        //recuperation des données spinner

        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                storeChoice = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //mettre en rouge le picker en cas
            }
        });

        //prefill de reasonOfAppointment

        reasenSprinner = findViewById(R.id.spinnerReasenPicker);
        ArrayAdapter<CharSequence> reasenStaticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        //drop down menu store picker
        reasenStaticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //liste stores
        String[] reasenItems = new String[]{"software windows", "hardware windows", "software Apple", "hardware Apple", "other, specify in description"};

        ArrayAdapter<String> reasenAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, reasenItems);

        reasenSprinner.setAdapter(reasenAdapter);
        reasenSprinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                reasenChoice = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //mettre en rouge le picker en cas
            }
        });


        if (reason.equals("software windows")){
            reasenSprinner.setSelection(0);
        } else if (reason.equals("hardware windows")) {
            reasenSprinner.setSelection(1);
        } else if (reason.equals("software Apple")) {
            reasenSprinner.setSelection(2);
        } else if (reason.equals("software Apple")) {
            reasenSprinner.setSelection(3);
        } else if (reason.equals("hardware Apple")) {
            reasenSprinner.setSelection(4);
        } else if (reason.equals("other, specify in description")) {
            reasenSprinner.setSelection(5);
        }

        Button saveBTN= findViewById(R.id.saveBTN);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newFullname = getFullname.getText().toString();
                String newStore = storeChoice;
                String newTime = getTime.getText().toString();
                String newDate = getDate.getText().toString();
                String newReason = reasenChoice;
                String newDescription = getDescription.getText().toString();
                Appointment appointment = new Appointment(newFullname,newStore,newDate,newTime,newReason,newDescription);

                GS_DbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {

                        for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                            Appointment appointmentDB = dataSnapshot2.getValue(Appointment.class);
                            if ((date.equals(appointmentDB.getDateAppointment())) && (time.equals(appointmentDB.getTimeAppointment()))) {

                                if (!newFullname.equals("")){
                                    dataSnapshot2.getRef().child("fullName").setValue(newFullname);
                                }
                                else {
                                    getFullname.setError("Fill this field");
                                    getFullname.requestFocus();
                                    return;
                                }
                                if (!newStore.equals("Select a store")) {
                                    dataSnapshot2.getRef().child("store").setValue(newStore);
                                }
                                else {
                                    Toast.makeText(modifyAppointment.this, "Fill the store field ", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (!newDate.equals("")){
                                    dataSnapshot2.getRef().child("dateAppointment").setValue(newDate);
                                }
                                else {
                                    getDate.setError("Fill this field");
                                    getDate.requestFocus();
                                    return;
                                }
                                if (!newTime.equals("")){
                                    dataSnapshot2.getRef().child("timeAppointment").setValue(newTime);
                                }
                                else {
                                    getTime.setError("Fill this field");
                                    getTime.requestFocus();
                                    return;
                                }
                                if (!newReason.equals("Select the reason for the appointment")) {
                                    dataSnapshot2.getRef().child("reasenOfAppointment").setValue(newReason);
                                }
                                else {
                                    Toast.makeText(modifyAppointment.this, "Fill the reason of appointment field ", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                dataSnapshot2.getRef().child("description").setValue(newDescription);

                            }
                        }                Toast.makeText(modifyAppointment.this, "appointment updated ", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });

        ImageView backBTN = findViewById(R.id.backArrow);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(modifyAppointment.this, manageMyApp.class);
                startActivity(intent);
            }
        });







    }
}