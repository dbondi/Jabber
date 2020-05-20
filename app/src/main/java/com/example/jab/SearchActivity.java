package com.example.jab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.ArrayList;
import java.util.Map;

import adapaters.MapTabAdapter;
import adapaters.SearchAdapter;
import adapaters.SearchColumnAdapter;
import controllers.SearchController;
import custom_class.HelperFunctions;
import custom_class.MapTab;
import custom_class.SearchRow;
import custom_class.SearchTab;
import models.SearchModel;

public class SearchActivity extends AppCompatActivity implements LocationListener{
    private SearchController controller;
    private SearchModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView searchView;
    private SearchAdapter searchColumnAdapter;
    private static final String TAG = "SearchActivity";

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;
    private boolean test = true;

    protected Location userLocation;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private Bundle saveInstanceState;
    private static Context instance;

    //locationData
    private String cityLocation = null;
    private HelperFunctions.Point[] cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private HelperFunctions.Point[] localCoordinates = null;
    private String localLocationKey = null;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.saveInstanceState = saveInstanceState;
        instance = this;
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_search);

        Intent intent = getIntent();

        cityLocation = intent.getStringExtra("CityLocation");
        cityCoordinates = (HelperFunctions.Point[]) intent.getParcelableArrayExtra("CityCoordinates");
        cityLocationKey = intent.getStringExtra("CityLocationKey");

        localLocation = intent.getStringExtra("LocalLocation");
        localCoordinates = (HelperFunctions.Point[]) intent.getParcelableArrayExtra("LocalCoordinates");
        localLocationKey = intent.getStringExtra("LocalLocationKey");

        if(cityLocationKey == null || localLocationKey == null){
            find_location();
        }

        // Mapbox access token is configured here. This needs to be called either in your application

        // This contains the MapView in XML and needs to be called after the access token is configured.


        controller = new SearchController(auth, this);
        model = new SearchModel(auth);

        chatTabBtn = findViewById(R.id.chat_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        storiesTabBtn = findViewById(R.id.stories_tab);

        searchView = findViewById(R.id.searchRec);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        chatTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.chatBtn();
            }
        });

        storiesTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.storiesBtn();
            }
        });


    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
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
        System.out.println("This thing is working");
        if(test){
            userLocation = new Location("");
            userLocation.setLatitude(-89.408054);
            userLocation.setLongitude(43.077293);
        }
        else {
            userLocation = location;
        }
        try {

            model.findCityLocation(new SearchActivity.FirestoreCallBack(){

                @Override
                public void onCallback(Map<String,Object> locationData) {
                    cityLocation = (String) locationData.get("Name");
                    cityCoordinates = (HelperFunctions.Point[]) locationData.get("Coordinates");
                    cityLocationKey = (String) locationData.get("LocationKey");

                    //find local Location
                    model.findLocalLocation(new SearchActivity.FirestoreCallBack(){

                        @Override
                        public void onCallback(Map<String,Object> locationData) {
                            localLocation = (String) locationData.get("Name");
                            localCoordinates = (HelperFunctions.Point[]) locationData.get("Coordinates");
                            localLocationKey = (String) locationData.get("LocationKey");


                            //loadTiles

                            ArrayList<String> tabStrings = new ArrayList<>();
                            ArrayList<SearchRow> searchTabs = new ArrayList<>();

                            ArrayList<Object> columnTabs = new ArrayList<Object>();

                            MapTab mTab = new MapTab();

                            columnTabs.add(mTab);

                            for(int i = 0; i < Math.floor(searchTabs.size()/2); i++){
                                SearchTab leftTab = new SearchTab();
                                SearchTab rightTab = new SearchTab();

                                leftTab.setName(tabStrings.get(2*i));

                                if (2*i + 1 < searchTabs.size()){
                                    rightTab.setName(tabStrings.get(2*i + 1));
                                }

                                SearchRow column = new SearchRow(leftTab, rightTab);
                                searchTabs.add(column);

                            }
                            searchColumnAdapter = new SearchAdapter(columnTabs,saveInstanceState,getContext());
                            searchView.setAdapter(searchColumnAdapter);
                            searchView.setLayoutManager(new LinearLayoutManager(getContext()));

                            //end loadTiles



                        }
                    },userLocation, cityLocationKey);
                }

            },userLocation);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }


    public interface FirestoreCallBack{
        void onCallback(Map<String,Object> locationData);
    }


    @Override
    public void onBackPressed() {
    }



}
