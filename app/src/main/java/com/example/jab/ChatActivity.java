package com.example.jab;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import adapaters.ChatAdapter;
import adapaters.SearchAdapter;
import controllers.ChatController;
import controllers.HomeController;
import custom_class.Chat;
import custom_class.HelperFunctions;
import custom_class.User;
import models.ChatModel;
import models.HomeModel;

public class ChatActivity extends AppCompatActivity implements LocationListener {
    protected Location userLocation;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ChatController controller;
    private ChatModel model;
    private RecyclerView chatView;
    private ChatAdapter chatAdapter;

    private static Context instance;
    private User user;

    //locationData
    private String cityLocation = null;
    private HelperFunctions.Point[] cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private HelperFunctions.Point[] localCoordinates = null;
    private String localLocationKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_overlay);
        auth = FirebaseAuth.getInstance();

        instance = this;

        user = getIntent().getParcelableExtra("User");

        chatView = findViewById(R.id.rc_messages);

        controller = new ChatController(auth, this);
        model = new ChatModel(auth, db);
        model.loadCityChats(new ChatActivity.FirestoreCallBack(){

            @Override
            public void onCallback(ArrayList<Chat> chats) {
                chatAdapter = new ChatAdapter(user);
                chatView.setAdapter(chatAdapter);
                chatView.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        },cityLocation, cityCoordinates, cityLocationKey, localLocation, localCoordinates, localLocationKey);

    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    public interface FirestoreCallBack{
        void onCallback(ArrayList<Chat> chats);
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
