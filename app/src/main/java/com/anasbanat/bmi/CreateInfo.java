package com.anasbanat.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class CreateInfo extends AppCompatActivity{

    private EditText selectDate;
    private ElegantNumberButton weight, length;
    private Button saveData;
    private String gender;
    private DatabaseReference recordsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_info);

        selectDate=(EditText)findViewById(R.id.date);
        recordsRef = FirebaseDatabase.getInstance().getReference().child("Records");
        weight = findViewById(R.id.info_weight);
        length = findViewById(R.id.info_length);

        saveData = findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String dateOfBirth = selectDate.getText().toString();
        if (TextUtils.isEmpty(dateOfBirth))
        {
            Toast.makeText(CreateInfo.this, getString(R.string.add_date), Toast.LENGTH_SHORT).show();
        }
        else
        {
            String[] items1 = dateOfBirth.split("/");
            String d1=items1[0];
            String m1=items1[1];
            String y1=items1[2];
            int d = Integer.parseInt(d1);
            int m = Integer.parseInt(m1);
            int y = Integer.parseInt(y1);

            String age = getAge(y,m,d);
            calcAgePercent(age);

            saveDataToDatabase();

        }
    }

    private void saveDataToDatabase() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String recordDate = currentDate.format(calendar.getTime());


        double bmi = CalculateBMI.CalcBMI(weight.getNumber(),length.getNumber());
        String bmiStatus = CalculateBMI.CalcBMIStatus(bmi);

        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("weight", weight.getNumber());
        itemMap.put("length", length.getNumber());
        itemMap.put("date", recordDate);
        itemMap.put("status", bmiStatus);
        itemMap.put("bmi", String.valueOf(bmi));

        recordsRef.child(UserPrevalent.email).updateChildren(itemMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful()) {} else {}
                    }
                });
    }

    private void calcAgePercent(String age) {
        int userAge = Integer.parseInt(age);
        if(userAge >=2 && userAge<=10)
        {
            UserPrevalent.agePercent = 70;
        }
        else if(userAge >10 && userAge<=20)
        {
            if (gender.equals("male"))
                UserPrevalent.agePercent = 90;
            else if (gender.equals("female"))
                UserPrevalent.agePercent = 80;
        }
        else if(userAge >20)
        {
            UserPrevalent.agePercent = 100;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender = "male";
                break;
            case R.id.female:
                if (checked)
                    gender = "female";
                break;
        }
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void saveData(View view) {
        startActivity(new Intent(getApplicationContext(), SaveData.class));
    }
}