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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;
    int attempts=1 ;
    public String typeUser ;

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
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = auth.getCurrentUser();

                                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users");

                                Query query = database.orderByChild("email").equalTo(currentUser.getEmail());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                 typeUser = userSnapshot.child("typeUser").getValue(String.class);
                                                if (typeUser.equals("client")){
                                                    FirebaseDatabase.getInstance().getReference("users")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    globalVar.currentUser =  snapshot.getValue(User.class);

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });

                                                    try {
                                                        Thread.sleep(300);
                                                        showMainActivity();
                                                    } catch (InterruptedException e) {

                                                    }
                                                }
                                                else if (typeUser.equals("tech")){
                                                    FirebaseDatabase.getInstance().getReference("users")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    globalVar.currentUser =  snapshot.getValue(User.class);

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });

                                                    try {
                                                        Thread.sleep(300);
                                                        showMainActivityTech();
                                                    } catch (InterruptedException e) {

                                                    }
                                                }else if(typeUser.equals("admin")){
                                                    FirebaseDatabase.getInstance().getReference("users")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    globalVar.currentUser =  snapshot.getValue(User.class);

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });

                                                    try {
                                                        Thread.sleep(300);
                                                        showMainActivityAdmin();
                                                    } catch (InterruptedException e) {

                                                    }
                                                }
                                            }


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        // Handle the error
                                    }
                                });

                            } else {
                                Toast.makeText(login.this, "echec d'authentification ", Toast.LENGTH_SHORT).show();
                                attempts++;
                            }
                        }else{ Toast.makeText(login.this, "nombre de tentatives limites atteint, veuillez reeseyer plutard ", Toast.LENGTH_SHORT).show();
                            //editAttempsUser(email);
                        }
                    }
                });


    }
    private void showMainActivity(){
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMainActivityTech(){
        Intent intent = new Intent(login.this, MainActivity_tech.class);
        startActivity(intent);
        finish();
    }
    private void showMainActivityAdmin(){
        Intent intent = new Intent(login.this, MainActivity_admin.class);
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