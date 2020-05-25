package com.example.jab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import adapaters.ChatAdapter;
import controllers.ChatController;
import custom_class.Chat;
import custom_class.PointMap;
import custom_class.User;
import models.ChatModel;

public class ChatActivity extends AppCompatActivity implements LocationListener {
    protected Location userLocation;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private ChatController controller;
    private ChatModel model;
    private RecyclerView chatView;
    private ChatAdapter chatAdapter;

    private static Context instance;

    private Button messageBtn;
    private TextView textLeft;
    private TextView textRight;
    private TextView localLocationText;
    private int local_city;

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;


    //locationData
    private String cityLocation = null;
    private ArrayList<PointMap> cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private ArrayList<PointMap> localCoordinates = null;
    private String localLocationKey = null;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        instance = this;

        chatView = findViewById(R.id.rc_messages);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        db = FirebaseFirestore.getInstance();
        //storage = FirebaseStorage.getInstance("gs://jabdatabase.appspot.com/MessageFolder");

        //textLeft = findViewById(R.id.newMessageTextLeft);
        //textRight = findViewById(R.id.newMessageTextRight);
        //messageBtn = findViewById(R.id.new_message);

        chatTabBtn = findViewById(R.id.chat_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        storiesTabBtn = findViewById(R.id.stories_tab);
        localLocationText = findViewById(R.id.localLocationText);

        cityLocation = bundle.getString("CityLocation");
        cityCoordinates = bundle.getParcelableArrayList("CityCoordinates");
        cityLocationKey = bundle.getString("CityLocationKey");

        localLocation = bundle.getString("LocalLocation");
        localCoordinates = bundle.getParcelableArrayList("LocalCoordinates");
        localLocationKey = bundle.getString("LocalLocationKey");

        user = bundle.getParcelable("User");

        //ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        //messageBtnLayoutParams.width = (int) buttonWidth;

        //messageBtn.setLayoutParams(messageBtnLayoutParams);


        //ViewGroup.LayoutParams textLeftLayoutParams = textLeft.getLayoutParams();
        //textLeftLayoutParams.width = widthScreen - ((int) buttonWidth) - 30;

        //textLeft.setLayoutParams(textLeftLayoutParams);


        //ViewGroup.LayoutParams textRightLayoutParams = textRight.getLayoutParams();
        //textRightLayoutParams.width = 30;

        //textRight.setLayoutParams(textRightLayoutParams);

        local_city = getIntent().getIntExtra("LocalCity", 0);

        //messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        controller = new ChatController(auth, this);
        model = new ChatModel(auth, db);
        model.loadCityChats(new ChatActivity.FirestoreCallBack(){

            @Override
            public void onCallback(ArrayList<Chat> chats) {

                chatAdapter = new ChatAdapter(user,getContext(),widthScreen,heightScreen,controller,cityLocation,cityCoordinates,cityLocationKey,localLocation,localCoordinates,localLocationKey);
                chatAdapter.update(chats);
                chatView.setAdapter(chatAdapter);
                chatView.setLayoutManager(new LinearLayoutManager(getContext()));


            }
        },cityLocation, cityCoordinates, cityLocationKey, localLocation, localCoordinates, localLocationKey, user);


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
