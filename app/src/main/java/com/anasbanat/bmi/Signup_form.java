package com.anasbanat.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Signup_form extends AppCompatActivity{

    EditText bFullName, bEmail, bPassword, bRePassword;
    Button bRegisterBTN;
    TextView login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        getSupportActionBar().setTitle("BMI Analyzer");

        mAuth = FirebaseAuth.getInstance();
        bFullName = findViewById(R.id.fullName);
        bEmail = findViewById(R.id.email);
        bPassword = findViewById(R.id.password);
        bRePassword = findViewById(R.id.rePassword);
        bRegisterBTN = (Button) findViewById(R.id.register_btn);

        bRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bFullName.getText().toString().trim();
                String email = bEmail.getText().toString().trim();
                String password = bPassword.getText().toString().trim();
                String rePassword = bRePassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    bFullName.setError("Full Name is required");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    bEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    bPassword.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(rePassword)) {
                    bRePassword.setError("Password is required again!");
                    return;
                }
                if(password.length() < 6 && rePassword.length() < 6) {
                    bPassword.setError("Password must be >= 6 characters");
                    return;
                }
                if(!password.equals(rePassword)) {
                    bRePassword.setError("The password is not equals!");
                    return;
                }
                // Register the user in firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Signup_form.this, "User created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Login_form.class));
                        }else {
                            Toast.makeText(Signup_form.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                if(mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), Login_form.class));
                    finish();
                }
            }
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_form.this, Login_form.class);
                finish();
                startActivity(intent);
            }
        });
    }
}