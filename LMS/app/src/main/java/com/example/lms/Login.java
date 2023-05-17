package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth;
    TextView txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onStart() {
        super.onStart();

        SessionManager sessionManager = new SessionManager(Login.this);

        if (sessionManager.getStudentId() != 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    txtUsername.setError("Please enter your email");
                    txtPassword.setError("Please enter your password");
                } else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                if (snapshot.child(username).child("password").getValue().toString().equals(password)) {
                                    Student student = new Student(username, snapshot.child(username).child("email").getValue().toString(), password, snapshot.child(username).child("phone").getValue().toString(), snapshot.child(username).child("name").getValue().toString(), snapshot.child(username).child("degree").getValue().toString(), ((Long) snapshot.child(username).child("student_id").getValue()).intValue());

                                    SessionManager sessionManager = new SessionManager(Login.this);
                                    sessionManager.saveSession(student);

                                    Log.d("Student", student.getUsername());
                                    Log.d("Student", student.getEmail());
                                    Log.d("Student", student.getPhone());
                                    Log.d("Student", student.getFullname());
                                    Log.d("Student", student.getDegree());
                                    Log.d("Student", String.valueOf(student.getStudent_id()));

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    txtPassword.setError("Password is incorrect");
                                }
                            } else {
                                txtUsername.setError("Username is incorrect");
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