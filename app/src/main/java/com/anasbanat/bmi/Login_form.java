package com.anasbanat.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("BMI Analyzer");
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), Signup_form.class));
    }
}