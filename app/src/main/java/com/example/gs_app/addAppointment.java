package com.example.gs_app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class addAppointment extends AppCompatActivity {

    Spinner storeSpinner, reasenSprinner ;
    private String reasenChoice;
    private String dateChoice;
    private String timeChoice;
    private String storeChoice;

    DatabaseReference GS_DbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment2);

        GS_DbRef = FirebaseDatabase.getInstance().getReference().child("appointment");



//date picker
        EditText datePicked = findViewById(R.id.datePicker);
        datePicked.setInputType(InputType.TYPE_NULL);

        datePicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = new GregorianCalendar();
                mCalendar.setTime(new Date());

                new DatePickerDialog(addAppointment.this, R.style.AppTheme_DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear= monthOfYear+1;
                        dateChoice = monthOfYear + "/" + dayOfMonth + "/" + year;
                        datePicked.setText(dateChoice);
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



// store picker
        storeSpinner = findViewById(R.id.spinnerStorePicker);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        //drop down menu store picker
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //liste stores
        String[] items = new String[]{"LOCATION","anjou 084", "marcher central 992", "centre ville 651"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        storeSpinner.setAdapter(adapter);

        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                storeChoice = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                //mettr en rouge le picker en cas
            }
        });



//reasen picker
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



//time picker
        // on below line we are initializing our variables.
        Button pickTimeBtn;
        pickTimeBtn = findViewById(R.id.idBtnPickTime);
        // action listner
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(addAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                if(minute<10){pickTimeBtn.setText(hourOfDay + ":0" + minute);
                                    timeChoice= hourOfDay + ":0" + minute;

                                }else{pickTimeBtn.setText(hourOfDay + ":" + minute);
                                    timeChoice = hourOfDay + ":" + minute;
                                }
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });



//on submit
        Button submitBTN = findViewById(R.id.saveBTN);
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAppointmentData();

            }
        });

    }



    private void insertAppointmentData(){
        EditText getFullName = findViewById(R.id.fullNameInput);
        EditText getDescription = findViewById(R.id.description);
        String fullName = getFullName.getText().toString();
        String description = getDescription.getText().toString();
        Appointment appointment = new Appointment(fullName, storeChoice, dateChoice, timeChoice,reasenChoice,description);

        GS_DbRef.push().setValue(appointment);
        Toast.makeText(addAppointment.this, "appointment added succefuly", Toast.LENGTH_SHORT).show();
    }


}