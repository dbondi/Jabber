package com.example.jab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import controllers.GenderController;
import custom_class.UserProfile;
import models.GenderModel;

public class GenderActivity extends AppCompatActivity {
    private GenderController controller;
    private GenderModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private final String TAG = "FirstNameActivity";
    private EditText firstName;
    private Button continueBtn;

    private TextView male;
    private TextView female;
    private TextView nonbinary;

    private UserProfile user;
    private ProgressBar loadingBar;
    private String gender = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new GenderController(this, auth);
        model = new GenderModel(db, auth);

        firstName = findViewById(R.id.first_name_register);
        continueBtn = findViewById(R.id.button_continue);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");

        loadingBar = findViewById(R.id.loading_phone);

        loadingBar.setVisibility(View.INVISIBLE);

        male = findViewById(R.id.male_gender);
        female = findViewById(R.id.female_gender);
        nonbinary = findViewById(R.id.nonbinary_gender);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                male.setTextColor(Color.parseColor("#44000000"));
                male.setBackgroundColor(Color.parseColor("#FFFFFF"));
                female.setTextColor(Color.parseColor("#FFFFFF"));
                female.setBackgroundColor(Color.parseColor("#44000000"));
                nonbinary.setTextColor(Color.parseColor("#FFFFFF"));
                nonbinary.setBackgroundColor(Color.parseColor("#44000000"));
            }
        });
        nonbinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "nonbinary";
                nonbinary.setTextColor(Color.parseColor("#44000000"));
                nonbinary.setBackgroundColor(Color.parseColor("#FFFFFF"));
                female.setTextColor(Color.parseColor("#FFFFFF"));
                female.setBackgroundColor(Color.parseColor("#44000000"));
                male.setTextColor(Color.parseColor("#FFFFFF"));
                male.setBackgroundColor(Color.parseColor("#44000000"));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                female.setTextColor(Color.parseColor("#44000000"));
                female.setBackgroundColor(Color.parseColor("#FFFFFF"));
                male.setTextColor(Color.parseColor("#FFFFFF"));
                male.setBackgroundColor(Color.parseColor("#44000000"));
                nonbinary.setTextColor(Color.parseColor("#FFFFFF"));
                nonbinary.setBackgroundColor(Color.parseColor("#44000000"));
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.equals("")){
                }
                else{
                    ArrayList<Boolean> photoBooleans = new ArrayList<>();
                    for(int i = 0; i < 9; i++){
                        photoBooleans.add(false);
                    }
                    user.setBoolPictures(photoBooleans);
                    user.setAbout("");
                    user.setGender(gender);
                    model.postUser(user);
                    controller.addGenderContinue(user);
                }
            }

        });



    }




}
