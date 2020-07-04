package com.example.jab;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import adapaters.ChatAdapter;
import adapaters.DirectMessageSearchAdapter;
import controllers.ChatController;
import controllers.DirectMessageSearchController;
import custom_class.Chat;
import custom_class.Place;
import custom_class.UserDirectMessages;
import custom_class.UserProfile;
import models.ChatModel;
import models.DirectMessageSearchModel;

public class DirectMessageSearchActivity extends AppCompatActivity implements LocationListener {

    private DirectMessageSearchController controller;
    private DirectMessageSearchModel model;
    private RecyclerView directMessagesView;
    private DirectMessageSearchAdapter directMessageSearchAdapter;

    private Button searchTabBtn;
    private Button profileTabBtn;
    private Button notificationTabBtn;
    private Button homeTabBtn;
    private Button directMessageTabBtn;

    private static Context instance;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private UserProfile user;
    private Place place;
    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_direct_messages);
        instance = this;

        searchTabBtn = findViewById(R.id.search_tab);
        profileTabBtn = findViewById(R.id.profile_tab);
        notificationTabBtn = findViewById(R.id.notification_tab);
        homeTabBtn = findViewById(R.id.home_tab);
        directMessageTabBtn = findViewById(R.id.direct_message_tab);


        auth = FirebaseAuth.getInstance();
        controller = new DirectMessageSearchController(auth, this);
        db = FirebaseFirestore.getInstance();
        model = new DirectMessageSearchModel(auth, db);

        directMessagesView = findViewById(R.id.direct_message_search);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("User");
        }
        if (bundle != null) {
            localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
            localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
            place = bundle.getParcelable("Place");
        }

        model.loadPaths(new DirectMessageSearchActivity.FirestoreCallBackPaths(){
            @Override
            public void onCallback(ArrayList<UserDirectMessages> userDirectMessages) {
                directMessagesView.setLayoutManager(new LinearLayoutManager(getContext()));
                directMessageSearchAdapter = new DirectMessageSearchAdapter(getContext(),bundle,controller);
                directMessageSearchAdapter.update(userDirectMessages);
                directMessagesView.setAdapter(directMessageSearchAdapter);
            }
        },user);

        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });

        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });
        notificationTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.notificationTabBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });
        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchTabBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });
        directMessageTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.directMessageTabBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });
    }


    public interface FirestoreCallBackPaths{
        void onCallback(ArrayList<UserDirectMessages> paths);
    }

    public static Context getContext(){
        return instance;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
