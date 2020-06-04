package com.example.jab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

import adapaters.ChatAdapter;
import adapaters.SearchAdapter;
import controllers.ChatController;
import custom_class.Chat;
import custom_class.MapTab;
import custom_class.Place;
import custom_class.PointMap;
import custom_class.SearchRow;
import custom_class.SearchTab;
import custom_class.User;
import models.ChatModel;

import static custom_class.HelperFunctions.distanceAway;
import static custom_class.HelperFunctions.random_color;

public class ChatActivity extends AppCompatActivity implements LocationListener {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private ChatController controller;
    private ChatModel model;
    private RecyclerView chatView;
    private ChatAdapter chatAdapter;

    private static Context instance;
    private TextView textLeft;
    private TextView textRight;
    private TextView localLocationText;
    private int local_city;
    private String[] randomColor;

    private Place place;
    private LinearLayout messageBtn;
    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;

    protected Location userLocation;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private boolean test = true;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private ArrayList<Place> universityPlaces = new ArrayList<>();
    private ArrayList<Place> cityPlaces = new ArrayList<>();

    private User user;

    @Override
    protected void onResume(){
        super.onResume();
        controller = new ChatController(auth, this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        System.out.println("On Resume On Resume");
        place = bundle.getParcelable("Place");
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.createMsgBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        randomColor = random_color(11);

        instance = this;

        chatView = findViewById(R.id.rc_messages);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        db = FirebaseFirestore.getInstance();
        //storage = FirebaseStorage.getInstance("gs://jabdatabase.appspot.com/MessageFolder");

        chatTabBtn = findViewById(R.id.chat_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        storiesTabBtn = findViewById(R.id.stories_tab);
        localLocationText = findViewById(R.id.localLocationText);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        double buttonWidth = heightScreen * .1;
        messageBtn = findViewById(R.id.create_post);
        ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        messageBtnLayoutParams.width = (int) buttonWidth;
        messageBtnLayoutParams.height = (int) buttonWidth;

        messageBtn.setLayoutParams(messageBtnLayoutParams);

        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        place = bundle.getParcelable("Place");
        user = bundle.getParcelable("User");

        localLocationText.setText(place.getName());


        controller = new ChatController(auth, this);
        model = new ChatModel(auth, db);

        model.loadChat(new ChatActivity.FirestoreCallBack(){

            @Override
            public void onCallback(ArrayList<Chat> chats) {

                chatView.setLayoutManager(new LinearLayoutManager(getContext()));
                /*
                chatView.setNestedScrollingEnabled(false);
                RecyclerView.ItemAnimator animator = chatView.getItemAnimator();
                if (animator instanceof SimpleItemAnimator) {
                    ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
                }
                chatAdapter.connectView(chatView);
                 */
                chatAdapter = new ChatAdapter(user,getContext(),widthScreen,heightScreen,controller);
                chatAdapter.update(chats);
                chatView.setAdapter(chatAdapter);


            }
        },place, user);

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.createMsgBtn(localUniversityPlaces,localCityPlaces,user,place);
            }
        });

    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    public interface FirestoreCallBack{
        void onCallback(ArrayList<Chat> chats);
    }

    private void find_location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);

        }

    }



    @Override
    public void onLocationChanged(Location location) {

        if(test){
            userLocation = location;
            userLocation.setLatitude(-89.408054);
            userLocation.setLongitude(43.077293);
            localCityLocations(location,5.0);
            localUniversityLocations(location,5.0);
        }
        else {
            userLocation = location;
            localCityLocations(location,5.0);
            localUniversityLocations(location,5.0);
        }

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

    public void localCityLocations(Location location, double milesAway){
        localCityPlaces = new ArrayList<>();
        for(Place place: cityPlaces) {
            LatLng local = place.getLocation();
            double miles = distanceAway(location.getLatitude(),local.latitude,location.getLongitude(),local.longitude,0.0,0.0);
            if (miles<=milesAway){
                localCityPlaces.add(place);
            }
        }
    }

    public void localUniversityLocations(Location location, double milesAway){
        localUniversityPlaces = new ArrayList<>();
        for(Place place: universityPlaces) {
            LatLng local = place.getLocation();
            double miles = distanceAway(location.getLatitude(),local.latitude,location.getLongitude(),local.longitude,0.0,0.0);
            if (miles<=milesAway){
                localUniversityPlaces.add(place);
            }
        }
    }

    @Override
    public void onBackPressed() {
        controller.goBack(localUniversityPlaces,localCityPlaces,user,place);
    }
}
