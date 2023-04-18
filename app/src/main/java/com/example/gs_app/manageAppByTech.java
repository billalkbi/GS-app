package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class manageAppByTech extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AppointmentAdapter appointmentAdapter;
    ArrayList<Appointment> list;
    private String typeUser = globalVar.currentUser.typeUser ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_app_by_tech);

        ImageView backBTN = findViewById(R.id.backArrow);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Create a Date object with the current date
        Date getCurrentDate = new Date();

        // Format the date using the SimpleDateFormat object
        String currentDate = sdf.format(getCurrentDate);

        recyclerView = findViewById(R.id.appointmentList);
        databaseReference = FirebaseDatabase.getInstance().getReference("appointment");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Appointment>();
        appointmentAdapter = new AppointmentAdapter(this, list);
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.setAdapter(appointmentAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   Appointment appointment = dataSnapshot.getValue(Appointment.class);
                   if (appointment.dateAppointment.equals(currentDate) )
                   {list.add(appointment);}
                   else {
                       TextView noAppForToDay ;
                       noAppForToDay = findViewById(R.id.TVNoAppForToDay);
                       noAppForToDay.setText("THERE IS NO APPOINTMENTS FOR TODAY"+currentDate);

                   }

                }
                appointmentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
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
            Toast.makeText(manageAppByTech.this, "erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMainActivityAdmin() {
        Intent intent = new Intent(manageAppByTech.this, MainActivity_admin.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivity(){
        Intent intent = new Intent(manageAppByTech.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivityTech(){
        Intent intent = new Intent(manageAppByTech.this, MainActivity_tech.class);
        startActivity(intent);
        finish();
    }

}