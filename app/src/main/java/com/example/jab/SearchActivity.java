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

import adapaters.SearchAdapter;
import controllers.SearchController;
import custom_class.MapTab;
import custom_class.PointMap;
import custom_class.SearchRow;
import custom_class.SearchTab;
import custom_class.User;
import models.SearchModel;

import static custom_class.HelperFunctions.isInside;

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
    private ArrayList<PointMap> cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private ArrayList<PointMap> localCoordinates = null;
    private String localLocationKey = null;

    private User user = null;
    private boolean autoUpdate = false;
    Bundle bundle = null;
    Intent intent = null;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.saveInstanceState = saveInstanceState;
        instance = this;
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_search);

        intent = getIntent();
        bundle = intent.getExtras();

        cityLocation = bundle.getString("CityLocation");
        cityCoordinates = bundle.getParcelableArrayList("CityCoordinates");
        cityLocationKey = bundle.getString("CityLocationKey");

        localLocation = bundle.getString("LocalLocation");
        localCoordinates = bundle.getParcelableArrayList("LocalCoordinates");
        localLocationKey = bundle.getString("LocalLocationKey");

        user = bundle.getParcelable("User");


        if(cityLocationKey == null || localLocationKey == null){
            autoUpdate = true;
        }
        find_location();

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

            if(needUpdated()) {

                model.findCityLocation(new SearchActivity.FirestoreCallBackFirst() {

                    @Override
                    public void onCallback(Map<String, Object> locationData) {
                        cityLocation = (String) locationData.get("Name");
                        cityCoordinates = (ArrayList<PointMap>) locationData.get("Coordinates");
                        cityLocationKey = (String) locationData.get("LocationKey");

                        //find local Location
                        model.findLocalLocation(new SearchActivity.FirestoreCallBackSecond() {

                            @Override
                            public void onCallback(Map<String, Object> locationData) {
                                localLocation = (String) locationData.get("Name");
                                localCoordinates = (ArrayList<PointMap>) locationData.get("Coordinates");
                                localLocationKey = (String) locationData.get("LocationKey");


                                //loadTiles

                                ArrayList<String> tabStrings = new ArrayList<>();
                                ArrayList<SearchRow> searchRows = new ArrayList<>();

                                SearchTab dealsChat = new SearchTab();
                                dealsChat.setName("Deals");
                                dealsChat.setID("Deals");

                                SearchTab localChat = new SearchTab();
                                localChat.setName(localLocation);
                                localChat.setID("LocalChat");

                                SearchTab cityChat = new SearchTab();
                                cityChat.setName(cityLocation);
                                cityChat.setID("CityChat");

                                SearchTab connectChat = new SearchTab();
                                connectChat.setName("Connect");
                                connectChat.setID("Connect");

                                SearchTab localEventsChat = new SearchTab();
                                localEventsChat.setName("Local Events");
                                localEventsChat.setID("LocalEvents");

                                SearchTab trendingChat = new SearchTab();
                                trendingChat.setName("Trending");
                                trendingChat.setID("Trending");

                                SearchRow row1 = new SearchRow(dealsChat, localChat);
                                SearchRow row2 = new SearchRow(cityChat, connectChat);
                                SearchRow row3 = new SearchRow(localEventsChat, trendingChat);

                                searchRows.add(row1);
                                searchRows.add(row2);
                                searchRows.add(row3);

                                ArrayList<Object> columnTabs = new ArrayList<Object>();

                                MapTab mTab = new MapTab();

                                columnTabs.add(mTab);

                                columnTabs.add(row1);
                                columnTabs.add(row2);
                                columnTabs.add(row3);

                            /*
                            for(int i = 0; i < Math.floor(searchRows.size()/2); i++){
                                System.out.println(i);
                                SearchTab leftTab = new SearchTab();
                                SearchTab rightTab = new SearchTab();

                                leftTab.setName(tabStrings.get(2*i));

                                if (2*i + 1 < searchRows.size()){
                                    rightTab.setName(tabStrings.get(2*i + 1));
                                }

                                SearchRow column = new SearchRow(leftTab, rightTab);
                                searchRows.add(column);

                            }
                            */
                                bundle.putString("CityLocation",cityLocation);
                                bundle.putParcelableArrayList("CityCoordinates", cityCoordinates);
                                bundle.putString("CityLocationKey",cityLocationKey);

                                bundle.putString("LocalLocation",localLocation);
                                bundle.putParcelableArrayList("LocalCoordinates",localCoordinates);
                                bundle.putString("LocalLocationKey",localLocationKey);

                                bundle.putParcelable("User",user);
                                intent.putExtras(bundle);

                                System.out.println("This far?");

                                searchColumnAdapter = new SearchAdapter(columnTabs, bundle, getContext());
                                searchView.setAdapter(searchColumnAdapter);
                                searchView.setLayoutManager(new LinearLayoutManager(getContext()));

                                //end loadTiles


                            }
                        }, userLocation, cityLocationKey);
                    }

                }, userLocation);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean needUpdated() {
        if (autoUpdate){
            return true;
        }
        return !(isInside(localCoordinates,userLocation) && isInside(cityCoordinates,userLocation));
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


    public interface FirestoreCallBackFirst{
        void onCallback(Map<String,Object> locationData);
    }
    public interface FirestoreCallBackSecond{
        void onCallback(Map<String,Object> locationData);
    }



    @Override
    public void onBackPressed() {
    }



}
