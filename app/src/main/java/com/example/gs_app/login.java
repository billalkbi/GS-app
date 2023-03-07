package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;
    int attempts=1 ;
    Button submitButton;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null){
            finish();
            return;
        }
        submitButton = findViewById(R.id.submitLoginView);
        submitButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                authenticateUser();
            }
        });

        TextView forgotPWD = findViewById(R.id.forgotPWDButtonView);
        forgotPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {methodeSwitchToForgotPWD();}
        });

        TextView txtSwitchToRegister = findViewById(R.id.registerButtonView);
        txtSwitchToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){methodeSwitchToRegister();
            }
        });


    }

    public void authenticateUser(){
        EditText getEmail = findViewById(R.id.emailInput);
        EditText getPWD = findViewById(R.id.pwdInput);

        String email = getEmail.getText().toString();
        String pwd =getPWD.getText().toString().trim();

        if(email.isEmpty()||pwd.isEmpty()){
            Toast.makeText(this, "veuillez remplir tout les champs svp.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (attempts <4) {
                            if (task.isSuccessful()) {
                                showMainActivity();
                            } else {
                                Toast.makeText(login.this, "echec d'authentification " + email + " " + pwd, Toast.LENGTH_SHORT).show();
                                attempts++;
                            }
                        }else{ Toast.makeText(login.this, "nombre de tentatives limites atteint, veuillez reeseyer plutard ", Toast.LENGTH_SHORT).show();
                            //editAttempsUser(email);
                        }
                    }
                });


    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void methodeSwitchToRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    private void methodeSwitchToForgotPWD(){
        Intent intent = new Intent(this, forgotPWD.class);
        startActivity(intent);
        finish();
    }

    /*private void editAttempsUser(String email){
         reference.child(email).child("attemp").setValue(attempts);
        Toast.makeText(login.this, "vous avez atteint le nombre limite de tentatives, votre compte a ete verouiller  ", Toast.LENGTH_SHORT).show();
    }*/
}