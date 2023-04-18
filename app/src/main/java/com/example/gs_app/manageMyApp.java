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

public class manageMyApp extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    AppointmentAdapter appointmentAdapter;

    ArrayList<Appointment> list;

    ImageView btnModify;

    Appointment apptstocked;
    private String typeUser = globalVar.currentUser.typeUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_app);

        ImageView backBTN = findViewById(R.id.backArrow);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manageMyApp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.appointmentList);
        databaseReference = FirebaseDatabase.getInstance().getReference("appointment");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("appointment");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Appointment>();
        appointmentAdapter = new AppointmentAdapter(this, list);
        recyclerView.setAdapter(appointmentAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean appointmentsFound = false;
                //list.clear(); // Clear the list before populating it
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    if (globalVar.currentUser.fullName.equals(appointment.getFullName())) {
                        list.add(appointment);
                        appointmentsFound = true;
                    }
                }
                appointmentAdapter.notifyDataSetChanged();
                if (!appointmentsFound) {
                    TextView noAppForToDay;
                    noAppForToDay = findViewById(R.id.TVNoAppForToDay);
                    noAppForToDay.setText("THERE IS NO APPOINTMENTS FOR TODAY FOR " + globalVar.currentUser.fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        appointmentAdapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    Appointment appointment = list.get(position);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                                Appointment appointmentDB = dataSnapshot.getValue(Appointment.class);
                                if ((appointment.dateAppointment.equals(appointmentDB.getDateAppointment())) && (appointment.timeAppointment.equals(appointmentDB.getTimeAppointment()))) {
                                    dataSnapshot.getRef().removeValue();
                                    list.remove(position);
                                    appointmentAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onModifyClick(int position) {

                if (position != RecyclerView.NO_POSITION) {
                    Appointment appointment = list.get(position);
                    String description = appointment.description;
                    String fullname = appointment.fullName;
                    String date = appointment.dateAppointment;
                    String time = appointment.timeAppointment;
                    String store = appointment.store;
                    String reason = appointment.reasenOfAppointment;



                    Intent intent = new Intent(manageMyApp.this, modifyAppointment.class);
                    intent.putExtra("description",description);
                    intent.putExtra("fullname",fullname);
                    intent.putExtra("date",date);
                    intent.putExtra("time",time);
                    intent.putExtra("store",store);
                    intent.putExtra("reason",reason);

                    startActivity(intent);
                    finish();
                }

            }



            public void onDeleteButtonClick(Appointment appointment) {

            }
        });
        /*Button cancelBTN = findViewById(R.id.cancelBTN);
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });*/
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
            Toast.makeText(manageMyApp.this, "erreur", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMainActivityAdmin() {
        Intent intent = new Intent(manageMyApp.this, MainActivity_admin.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivity(){
        Intent intent = new Intent(manageMyApp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivityTech(){
        Intent intent = new Intent(manageMyApp.this, MainActivity_tech.class);
        startActivity(intent);
        finish();
    }
}
