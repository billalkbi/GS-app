package com.example.gs_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity_tech extends AppCompatActivity {

    TextView fullNametv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tech);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser= auth.getCurrentUser();

        if ( globalVar.currentUser.fullName == null ){
            Intent intent = new Intent ( this, login.class);
            startActivity(intent);
            finish();
            return;
        }


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

        Button btnManage_appointment = findViewById(R.id.btnManageApp);
        btnManage_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainToManage();
            }
        });

        fullNametv = findViewById(R.id.TVfullName);
        fullNametv.setText(globalVar.currentUser.fullName);

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

    private void mainToManage(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, manageAppByTech.class);
        startActivity(intent);
        finish();
    }
}
