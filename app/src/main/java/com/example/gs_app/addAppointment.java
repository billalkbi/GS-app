package com.example.gs_app;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class addAppointment extends AppCompatActivity {

    private Spinner spinner;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        EditText  datePicked = findViewById(R.id.datePicker);
        datePicked.setInputType(InputType.TYPE_NULL);

        //store picker
        spinner = (Spinner)findViewById(R.id.spinnerStorePicker);
        //date picker
        datePicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = new GregorianCalendar();
                mCalendar.setTime(new Date());

                new DatePickerDialog(addAppointment.this, R.style.AppTheme_DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //do something with the date
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        //drop down menu store picker
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //liste stores
        String[] items = new String[] { "anjou 084", "marcher central 992", "centre ville 651" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String storeChoiced = (String) parent.getItemAtPosition(position);
                Toast.makeText(addAppointment.this, "your choice is "+(String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



    }

}