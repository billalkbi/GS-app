package com.example.gs_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity_admin extends AppCompatActivity {

    Button create_tech;
    Button book_appt;
    Button assign,logout;
    TextView tvfullName;
    String fullName = globalVar.currentUser.fullName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ( globalVar.currentUser.fullName == ""){
            Intent intent = new Intent ( this, login.class);
            startActivity(intent);
            finish();
            return;
        }


        logout = findViewById(R.id.logButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                logoutUser();
            }
        });



        create_tech= findViewById(R.id.btnCreateTech);
        create_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity_admin.this, register_tech.class);
                startActivity(intent);
                finish();
            }
        });
        book_appt = findViewById(R.id.btnBook);
        book_appt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity_admin.this, addAppointment.class);
                startActivity(intent);
                finish();
            }
        });
        assign = findViewById(R.id.btnassign);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity_admin.this, assign_appt.class);
                startActivity(intent);
                finish();
            }
        });

        tvfullName = findViewById(R.id.fullNametv);
        tvfullName.setText(fullName);

    }
    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }


}
