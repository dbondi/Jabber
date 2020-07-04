package com.example.jab;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.util.ArrayList;

import adapaters.ProfilePictureAdapter;
import controllers.MyProfileController;
import custom_class.Place;
import custom_class.UserProfile;
import models.MyProfileModel;

public class MyProfileActivity extends AppCompatActivity{

    private MyProfileController controller;
    private FirebaseAuth auth;

    private Button homeTabBtn;
    private Button directMessageTabBtn;
    private Button searchTabBtn;
    private Button notificationTabBtn;

    private LinearLayout editProfile;

    private TextView bio;
    private TextView profileName;
    private TextView ageOnProfile;

    private RecyclerView recyclerView;
    private ProfilePictureAdapter profilePictureAdapter;

    private MyProfileModel model;
    private FirebaseFirestore db;

    private static Context instance;

    private Place place;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();


    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.profilePictures);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        controller = new MyProfileController(auth, this);

        homeTabBtn = findViewById(R.id.home_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        directMessageTabBtn =findViewById(R.id.direct_message_tab);
        notificationTabBtn = findViewById(R.id.notification_tab);


        profileName = findViewById(R.id.profile_name);
        bio = findViewById(R.id.bio);
        ageOnProfile = findViewById(R.id.age_on_profile);

        editProfile = findViewById(R.id.edit_profile);

        model = new MyProfileModel(auth, db);

        instance = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        place = bundle.getParcelable("Place");

        System.out.println("BoolPics");

        System.out.println(user.getBoolPictures());

        model.loadUserData(new MyProfileActivity.FirestoreCallBack(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCallback(UserProfile userEditInfo) {
                String name = userEditInfo.getProfileName();
                String aboutInfo = userEditInfo.getAbout();
                String age = String.valueOf(calculateAge(userEditInfo.getAge(), Instant.now().toEpochMilli()));

                profileName.setText(name);
                bio.setText(aboutInfo);
                ageOnProfile.setText(age);



                ArrayList<String> storageReferences = userEditInfo.getStorageReferences();
                ProfilePictureAdapter profilePictureAdapter =  new ProfilePictureAdapter(storageReferences,getContext());


                recyclerView.setAdapter(profilePictureAdapter);


            }
        },user);

        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

        notificationTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.notificationTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

        directMessageTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.directMessageTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.editProfile(localUniversityPlaces,localCityPlaces,user);
            }
        });
    }
    public static Context getContext(){
        return instance;
    }

    public interface FirestoreCallBack{
        void onCallback(UserProfile userEditInfo);
    }

    public static int calculateAge(Integer birthDate, long currentDate) {

        if ((birthDate != 0)) {
            return (int) Math.floor((currentDate/1000-birthDate)/31556952.0);
        } else {
            return 0;
        }
    }


}
