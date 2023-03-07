package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPWD extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button cancelButton;
    Button saveButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null){
            finish();
            return;
        }
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { methodeSwitchToLogin(); }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { methodeSendMail();  }
        });

    }

    private void methodeSwitchToLogin(){
        Intent intent= new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    private void methodeSendMail(){
        EditText getEmail = findViewById(R.id.emailInput);
        String email = getEmail.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "Enter your registred email please.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(forgotPWD.this, "check your emails.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(forgotPWD.this, "unable to send, enter a correct email please.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}