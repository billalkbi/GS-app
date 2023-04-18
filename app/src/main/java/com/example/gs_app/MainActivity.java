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

    TextView tvfullName;
    String fullName = globalVar.currentUser.fullName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser= auth.getCurrentUser();

        if (currentUser == null & globalVar.currentUser.typeUser == null ){
            Intent intent = new Intent ( this, login.class);
            startActivity(intent);
            finish();
            return;
        }

        tvfullName = findViewById(R.id.TVfullName);
        tvfullName.setText( globalVar.currentUser.fullName);


        Button btnLogout= findViewById(R.id.logButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        Button btnBook_management= findViewById(R.id.btnManageMyApp);
        btnBook_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, manageMyApp.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnBookApp = findViewById(R.id.btnBook);
        btnBookApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainToBook();
            }
        });

    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    private void mainToBook() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, addAppointment.class);
        startActivity(intent);
        finish();
    }
    private void mainToManageMyApp(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, manageMyApp.class);
        startActivity(intent);
        finish();
    }
}