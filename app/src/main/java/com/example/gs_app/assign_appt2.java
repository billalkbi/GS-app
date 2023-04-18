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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class assign_appt2 extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    AssignAdapter appointmentAdapter;

    ArrayList<User> list;

    ImageView btnModify;

    Appointment apptstocked;
    private String typeUser = globalVar.currentUser.typeUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_appt2);

        ImageView backBTN = findViewById(R.id.backArrow);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(assign_appt2.this, MainActivity_admin.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.appointmentList);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<User>();
        appointmentAdapter = new AssignAdapter(this, list);
        recyclerView.setAdapter(appointmentAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean appointmentsFound = false;
                list.clear(); // Clear the list before populating it
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User tech = dataSnapshot.getValue(User.class);
                    if (tech.getTypeUser().equals("tech")) {
                        list.add(tech);
                        appointmentsFound = true;
                    }
                }
                appointmentAdapter.notifyDataSetChanged();
                if (!appointmentsFound) {
                    TextView noAppForToDay;
                    noAppForToDay = findViewById(R.id.TVNoAppForToDay);
                    noAppForToDay.setText("no tech available");
                }
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
            Toast.makeText(assign_appt2.this, "erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMainActivityAdmin() {
        Intent intent = new Intent(assign_appt2.this, MainActivity_admin.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivity(){
        Intent intent = new Intent(assign_appt2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivityTech(){
        Intent intent = new Intent(assign_appt2.this, MainActivity_tech.class);
        startActivity(intent);
        finish();
    }
}
