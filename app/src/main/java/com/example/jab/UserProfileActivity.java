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

    private Button searchTabBtn;
    private Button directMessageTabBtn;
    private Button profileTabBtn;
    private Button notificationTabBtn;
    private Button homeTabBtn;

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

        notificationTabBtn = findViewById(R.id.notification_tab);
        directMessageTabBtn = findViewById(R.id.direct_message_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        profileTabBtn = findViewById(R.id.profile_tab);
        homeTabBtn = findViewById(R.id.home_tab);

        intent = getIntent();
        bundle = intent.getExtras();

        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");

        directMessageTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.directMessageTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        notificationTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.notificationTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

    }

}
