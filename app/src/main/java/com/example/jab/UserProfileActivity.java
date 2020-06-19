package com.example.jab;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import controllers.UserProfileController;
import custom_class.Place;
import custom_class.UserProfile;

public class UserProfileActivity extends AppCompatActivity {

    private UserProfileController controller;
    private FirebaseAuth auth;
    private Button homeTabBtn;
    private Button chatTabBtn;
    private Button searchTabBtn;

    private UserProfile user;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private ArrayList<Place> universityPlaces = new ArrayList<>();
    private ArrayList<Place> cityPlaces = new ArrayList<>();

    Bundle bundle = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        controller = new UserProfileController(auth, this);
        homeTabBtn = findViewById(R.id.home_tab);
        searchTabBtn = findViewById(R.id.search_tab);

        intent = getIntent();
        bundle = intent.getExtras();

        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");


        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });

        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });

    }

}
