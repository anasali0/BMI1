package com.anasbanat.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_form extends AppCompatActivity {
    EditText bEmail, bPassword;
    Button bLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("BMI Analyzer");
        bEmail = findViewById(R.id.email);
        bPassword = findViewById(R.id.password);
        bEmail = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        bLogin = findViewById(R.id.login_btn);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = bEmail.getText().toString().trim();
                String password = bPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    bEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    bPassword.setError("Password is required.");
                    return;
                }
                if(password.length() < 6) {
                    bPassword.setError("Password must be >= 6 characters");
                    return;
                }
                //Authenticate the user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login_form.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), CreateInfo.class));
                        }else {
                            Toast.makeText(Login_form.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), Signup_form.class));
    }
}