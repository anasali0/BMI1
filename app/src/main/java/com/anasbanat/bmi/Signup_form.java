package com.anasbanat.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Signup_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        getSupportActionBar().setTitle("BMI Analyzer");
    }
    public void btn_loginForm(View view) {
        startActivity(new Intent(getApplicationContext(), Login_form.class));
    }
}