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
import controllers.ProfileController;
import custom_class.Place;
import custom_class.UserProfile;
import models.MyProfileModel;

public class ProfileActivity extends AppCompatActivity{

    private ProfileController controller;
    private FirebaseAuth auth;

    private Button homeTabBtn;
    private Button chatTabBtn;
    private Button searchTabBtn;
    private Button notificationTabBtn;
    private Button profileTabBtn;

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
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.profilePictures);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        controller = new ProfileController(auth, this);

        homeTabBtn = findViewById(R.id.home_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        chatTabBtn = findViewById(R.id.chat_tab);
        notificationTabBtn = findViewById(R.id.notification_tab);
        profileTabBtn = findViewById(R.id.profile_tab);


        profileName = findViewById(R.id.profile_name);
        bio = findViewById(R.id.bio);
        ageOnProfile = findViewById(R.id.age_on_profile);
        model = new MyProfileModel(auth, db);

        instance = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer highlightNum = bundle.getInt("HighlightNumber");
        System.out.println(highlightNum);

        loadHighlightTab(highlightNum);


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
                controller.homeBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });

    }

    private void loadHighlightTab(Integer highlightNum) {
        if(highlightNum==1){
            homeTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.home_icon));
            searchTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.radar_trans));
            chatTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.message_chat_trans));
            notificationTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.bells_trans));
            profileTabBtn.setBackground(getContext().getResources().getDrawable(R.drawable.profile_icon_new_what_trans));
        }
        if(highlightNum==2){
            System.out.println("Load Highlighter2");
            homeTabBtn.setBackground(getResources().getDrawable(R.drawable.home_icon_trans));
            searchTabBtn.setBackground(getResources().getDrawable(R.drawable.radar));
            chatTabBtn.setBackground(getResources().getDrawable(R.drawable.message_chat_trans));
            notificationTabBtn.setBackground(getResources().getDrawable(R.drawable.bells_trans));
            profileTabBtn.setBackground(getResources().getDrawable(R.drawable.profile_icon_new_what_trans));
        }
        if(highlightNum==3){
            homeTabBtn.setBackground(getResources().getDrawable(R.drawable.home_icon_trans));
            searchTabBtn.setBackground(getResources().getDrawable(R.drawable.radar_trans));
            chatTabBtn.setBackground(getResources().getDrawable(R.drawable.message_chat));
            notificationTabBtn.setBackground(getResources().getDrawable(R.drawable.bells_trans));
            profileTabBtn.setBackground(getResources().getDrawable(R.drawable.profile_icon_new_what_trans));
        }
        if(highlightNum==4){
            homeTabBtn.setBackground(getResources().getDrawable(R.drawable.home_icon_trans));
            searchTabBtn.setBackground(getResources().getDrawable(R.drawable.radar_trans));
            chatTabBtn.setBackground(getResources().getDrawable(R.drawable.message_chat_trans));
            notificationTabBtn.setBackground(getResources().getDrawable(R.drawable.bells));
            profileTabBtn.setBackground(getResources().getDrawable(R.drawable.profile_icon_new_what_trans));
        }
        if(highlightNum==5){
            homeTabBtn.setBackground(getResources().getDrawable(R.drawable.home_icon_trans));
            searchTabBtn.setBackground(getResources().getDrawable(R.drawable.radar_trans));
            chatTabBtn.setBackground(getResources().getDrawable(R.drawable.message_chat_trans));
            notificationTabBtn.setBackground(getResources().getDrawable(R.drawable.bells_trans));
            profileTabBtn.setBackground(getResources().getDrawable(R.drawable.profile_icon_new_what));
        }
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
