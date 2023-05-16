package com.example.lms;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class login extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    TextInputEditText txtEmail, txtPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();

        SessionManager sessionManager = new SessionManager(login.this);

        if (sessionManager.getEmail() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.loginEmail);
        txtPassword = findViewById(R.id.password);

        buttonLogin = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // progressBar.setVisibility(View.VISIBLE);

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    txtEmail.setError("Please enter your email");
                    txtPassword.setError("Please enter your password");
                } else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email)) {
                                if (snapshot.child(email).child("password").getValue().toString().equals(password)) {
                                    Email email1 = new Email(email, snapshot.child(email).child("email").getValue().toString(), password, snapshot.child(email).child("phone").getValue().toString());

                                    SessionManager sessionManager = new SessionManager(login.this);
                                    sessionManager.saveSession(email);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    txtPassword.setError("Password is incorrect");
                                }
                            } else {
                                txtEmail.setError("Email is incorrect");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


}