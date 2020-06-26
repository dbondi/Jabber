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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.mapboxsdk.Mapbox;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapaters.SearchAdapter;
import adapaters.SearchTabAdapter;
import adapaters.TopTabAdapter;
import controllers.SearchController;
import controllers.SearchTabController;
import custom_class.CSVFile;
import custom_class.MapTab;
import custom_class.Place;
import custom_class.SearchRow;
import custom_class.SearchTab;
import custom_class.TabInfo;
import custom_class.UserProfile;
import models.SearchModel;
import models.SearchTabModel;

import static custom_class.HelperFunctions.isInside;

public class SearchTabActivity extends AppCompatActivity implements LocationListener {
    private SearchTabController controller;
    private SearchTabModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView searchTabs;
    private SearchTabAdapter searchAdapter;
    private RecyclerView topTabs;
    private TopTabAdapter topTabAdapter;
    private static final String TAG = "SearchTabActivity";

    private Button homeTabBtn;
    private Button chatTabBtn;
    private Button notificationTabBtn;
    private Button profileTabBtn;
    private boolean test = true;

    protected Location userLocation;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private Bundle saveInstanceState;
    private static Context instance;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private ArrayList<Place> universityPlaces = new ArrayList<>();
    private ArrayList<Place> cityPlaces = new ArrayList<>();

    private UserProfile user = null;
    private boolean autoUpdate = false;
    Bundle bundle = null;
    Intent intent = null;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.saveInstanceState = saveInstanceState;
        instance = this;

        setContentView(R.layout.activity_search_tab);

        intent = getIntent();
        bundle = intent.getExtras();

        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");


        autoUpdate = true;
        find_location();


        controller = new SearchTabController(auth, this);
        model = new SearchTabModel(auth);

        homeTabBtn = findViewById(R.id.home_tab);
        chatTabBtn = findViewById(R.id.chat_tab);
        notificationTabBtn = findViewById(R.id.notification_tab);
        profileTabBtn = findViewById(R.id.profile_tab);

        searchTabs = findViewById(R.id.small_buttons);
        topTabs = findViewById(R.id.big_button);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        updateTabs();

        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileTabBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });

        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeTabBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });

        chatTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.chatTabBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });



    }

    public static Context getContext() {
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
        if (test) {
            userLocation = new Location("");
            userLocation.setLatitude(-89.408054);
            userLocation.setLongitude(43.077293);
        } else {
            userLocation = location;
        }

        if (needUpdated()) {
            //updateTabs();
        }

    }

    private boolean needUpdated() {
        if (autoUpdate) {
            return true;
        }
        return true;//!(isInside(localCoordinates,userLocation) && isInside(cityCoordinates,userLocation));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }


    @Override
    public void onBackPressed() {
    }

    private void loadUniversityLocations() {
        universityPlaces = new ArrayList<>();
        boolean firstLine = true;
        System.out.println(System.currentTimeMillis());
        InputStream inputStream = getResources().openRawResource(R.raw.college_acred);
        CSVFile csvFile = new CSVFile(inputStream);
        List universityList = csvFile.read();
        for (Object universityData : universityList) {
            String concatUniversityDataFull = Arrays.toString((String[]) universityData);
            String concatUniversityData = concatUniversityDataFull.substring(1, concatUniversityDataFull.length() - 1).replace(", ", ",");
            String[] universityDataSplit = concatUniversityData.split(",");
            if (!firstLine) {
                LatLng locationLocation = new LatLng(Double.parseDouble(universityDataSplit[5]), Double.parseDouble(universityDataSplit[6]));
                int pop = Integer.parseInt(universityDataSplit[4]);
                universityPlaces.add(new Place(locationLocation, universityDataSplit[1].replace(";", ","), pop, universityDataSplit[0], "Universities"));
            } else {
                firstLine = false;
            }
        }
        System.out.println(System.currentTimeMillis());
    }


    private void loadCityLocations() {
        cityPlaces = new ArrayList<>();
        boolean firstLine = true;
        System.out.println(System.currentTimeMillis());
        InputStream inputStream = getResources().openRawResource(R.raw.largest_cities);
        CSVFile csvFile = new CSVFile(inputStream);
        List cityList = csvFile.read();
        for (Object cityData : cityList) {
            String concatCityDataFull = Arrays.toString((String[]) cityData);
            String concatCityData = concatCityDataFull.substring(1, concatCityDataFull.length() - 1).replace(", ", ",");
            String[] cityDataSplit = concatCityData.split(",");
            if (!firstLine) {
                LatLng locationLocation = new LatLng(Double.parseDouble(cityDataSplit[5]), Double.parseDouble(cityDataSplit[6]));
                int pop = Integer.parseInt(cityDataSplit[4]);
                cityPlaces.add(new Place(locationLocation, cityDataSplit[1], pop, cityDataSplit[7], "Cities"));
            } else {
                firstLine = false;
            }
        }
        System.out.println(System.currentTimeMillis());
    }

    private void updateTabs(){

        ArrayList<TabInfo> tabInfos = new ArrayList<>();

        tabInfos.add(new TabInfo("106"));
        tabInfos.add(new TabInfo("104"));
        tabInfos.add(new TabInfo("105"));
        tabInfos.add(new TabInfo("100"));
        tabInfos.add(new TabInfo("101"));
        tabInfos.add(new TabInfo("102"));
        tabInfos.add(new TabInfo("103"));


        bundle.putParcelable("User", user);
        intent.putExtras(bundle);

        searchAdapter = new SearchTabAdapter(tabInfos, bundle, getContext());
        searchTabs.setAdapter(searchAdapter);
        searchTabs.setLayoutManager(new GridLayoutManager(getContext(),2));

        ArrayList<TabInfo> topInfos = new ArrayList<>();

        topInfos.add(new TabInfo("100"));


        topTabAdapter = new TopTabAdapter(topInfos, bundle, getContext());
        topTabs.setAdapter(topTabAdapter);
        topTabs.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
