package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView tvfullName,  tvemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser= auth.getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent ( this, login.class);
            startActivity(intent);
            finish();
            return;
        }

        tvfullName = findViewById(R.id.TVfullName);


        Button btnLogout= findViewById(R.id.logButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        Button btnBook_management= findViewById(R.id.btnBook);
        btnBook_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainToBook();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null)
                {
                    tvfullName.setText(user.fullName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    private void mainToBook(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, addAppointment.class);
        startActivity(intent);
        finish();
    }




}