package com.example.gs_app;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.os.Build;
import android.os.Looper;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class addAppointment extends AppCompatActivity  {

    Spinner storeSpinner, reasenSprinner ;
    private String reasenChoice;
    private String dateChoice;
    private String timeChoice;
    private String storeChoice;
    private String month;
    private double longitude, latitude ;
    private static double latAnjour = 45.60153354672869;
    private static double longAnjou=  -73.56056094231259;

    private static double latCentreVille = 45.5049746339881 ;
    private static double longCentreVille= -73.56787897446151 ;

    private static double latMarcheCentral = 45.532144628788885 ;
    private static double longMarcheCentral = -73.65077328464031 ;

    TextView AddressText ;
    private LocationRequest locationRequest;

    private String typeUser = globalVar.currentUser.typeUser ;

    DatabaseReference GS_DbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment2);

        ImageView backBTN = findViewById(R.id.backArrow);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        AddressText = findViewById(R.id.addressText);

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
                        if (monthOfYear<10)
                        { month = "0"+monthOfYear;}
                        else
                        { month = Integer.toString(monthOfYear);}
                        dateChoice = month + "/" + dayOfMonth + "/" + year;
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
        String[] items = new String[]{"location","anjou 084", "marcher central 992", "centre ville 651"};

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
//get location
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        Button getLocation = findViewById(R.id.getBTN);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentLocation();
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
        EditText getDate = findViewById(R.id.datePicker);
        String fullName = getFullName.getText().toString();
        String description = getDescription.getText().toString();
        String dateApp = getDate.getText().toString();
        Appointment appointment = new Appointment(fullName, storeChoice, dateApp, timeChoice,reasenChoice,description);

        GS_DbRef.push().setValue(appointment);
        Toast.makeText(addAppointment.this, "appointment added succesfully", Toast.LENGTH_SHORT).show();
    }


    //manage go back
    private void goBack()
    {
        if (typeUser.equals("tech")){
            showMainActivityTech();
        }
        else if (typeUser.equals("client")) {
            showMainActivity();
        }
        else if (typeUser.equals("admin")) {
            showMainActivityAdmin();
        }else {
            Toast.makeText(addAppointment.this, "erreur", Toast.LENGTH_SHORT).show();
        }

    }

    private void showMainActivityAdmin() {
        Intent intent = new Intent(addAppointment.this, MainActivity_admin.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivity(){
        Intent intent = new Intent(addAppointment.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivityTech(){
        Intent intent = new Intent(addAppointment.this, MainActivity_tech.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(addAppointment.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(addAppointment.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(addAppointment.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        latitude = locationResult.getLocations().get(index).getLatitude();
                                        longitude = locationResult.getLocations().get(index).getLongitude();

                                       /*float[] results = new float[3];
                                        Location.distanceBetween(latitude,longitude,latMarcheCentral,longMarcheCentral,results);
                                        Location.distanceBetween(latitude,longitude,latCentreVille, longCentreVille,results);
                                        Location.distanceBetween(latitude,longitude,latMarcheCentral,longMarcheCentral,results);
                                        float distanceAnjou = results[0]/1000;
                                        float distnaceMarcher = results[1]/1000;
                                        float distanceCentreville = results[2]/1000;
                                        */
                                        storeSpinner.setEnabled(false);storeSpinner.setSelection(3);

                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(addAppointment.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(addAppointment.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }





}