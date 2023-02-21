package com.example.gs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;

    private EditText inputPasswordConfirm;
    private Button btnSignUp;

    private static final String typeUser = "client";

    private Button Already;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        inputFullName = (EditText) findViewById(R.id.fullName);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPasswordConfirm = (EditText) findViewById(R.id.passwordConf);
        btnSignUp = (Button) findViewById(R.id.save);
        progressDialog = new ProgressDialog(this);
        Already = (Button) findViewById(R.id.already);

        Already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  txtSwitchToRegister();  }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String passwordConfirm = inputPasswordConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(getApplicationContext(), "Enter full name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(fullName, email, typeUser);
                                    progressDialog.show();
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "User had been added succesfully", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();

                                                    } else {
                                                        Toast.makeText(Register.this, "Failed to register, try again", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                }
                                            });
                                }
                            }
                        });


            };
        });
    }
    private void txtSwitchToRegister(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}
