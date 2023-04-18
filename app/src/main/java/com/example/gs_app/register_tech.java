package com.example.gs_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class register_tech extends AppCompatActivity {

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;

    private EditText inputPasswordConfirm;
    private Button btnSignUp;

    private static final String typeUser = "tech";

    private Button Already;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    TextView tvfullName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvfullName = findViewById(R.id.TVfullName);



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
            public void onClick(View view) {    }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String passwordConfirm = inputPasswordConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    inputFullName.setError("Enter full name");
                    inputFullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter email address");
                    inputEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Password Empty");
                    inputPassword.requestFocus();
                    return;
                }

                if (password.length() < 8) {
                    inputPassword.setError("Password too short (>8)");
                    inputPassword.requestFocus();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    inputPasswordConfirm.setError("Passwords do not match");
                    inputPasswordConfirm.requestFocus();
                    return;
                }

                auth.fetchSignInMethodsForEmail(inputEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult().getSignInMethods().isEmpty();

                        if (!check){
                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                                            Toast.makeText(register_tech.this, "User had been added succesfully", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();

                                                        } else {
                                                            Toast.makeText(register_tech.this, "Failed to register, try again", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                        else
                        {
                            inputEmail.setError("Email already used");
                            inputEmail.requestFocus();
                        }

                    }
                });



            }
        });

    }}