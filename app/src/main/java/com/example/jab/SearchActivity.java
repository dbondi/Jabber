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

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.mapboxsdk.Mapbox;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapaters.SearchAdapter;
import controllers.SearchController;
import custom_class.CSVFile;
import custom_class.MapTab;
import custom_class.Place;
import custom_class.SearchRow;
import custom_class.SearchTab;
import custom_class.UserProfile;
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

    private Button homeTabBtn;
    private Button chatTabBtn;
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
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_search);

        intent = getIntent();
        bundle = intent.getExtras();

        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");

        //if(cityLocationKey == null || localLocationKey == null){
        autoUpdate = true;
        //}
        find_location();

        // Mapbox access token is configured here. This needs to be called either in your application

        // This contains the MapView in XML and needs to be called after the access token is configured.



        controller = new SearchController(auth, this);
        model = new SearchModel(auth);

        homeTabBtn = findViewById(R.id.home_tab);
        chatTabBtn = findViewById(R.id.chat_tab);
        profileTabBtn = findViewById(R.id.profile_tab);

        searchView = findViewById(R.id.searchRec);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeBtn(localUniversityPlaces, localCityPlaces, user);
            }
        });

        chatTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.chatBtn();
            }
        });

        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileBtn(localUniversityPlaces, localCityPlaces, user);
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

        if(needUpdated()) {


            SearchTab dealsChat = new SearchTab();
            dealsChat.setName("Deals");
            dealsChat.setID("Deals");

            ArrayList<SearchTab> universityTabs = new ArrayList<>();
            for(Place place: localUniversityPlaces){
                SearchTab universityTab = new SearchTab();
                universityTab.setName(place.getName());
                universityTab.setType("UniversityTab");
                universityTab.setPlace(place);
                universityTabs.add(universityTab);
                System.out.println(place.getName());
            }
            ArrayList<SearchTab> cityTabs = new ArrayList<>();
            for(Place place: localCityPlaces){
                SearchTab cityTab = new SearchTab();
                cityTab.setName(place.getName());
                cityTab.setPlace(place);
                cityTab.setType("CityTab");
                cityTabs.add(cityTab);
                System.out.println(place.getName());
            }
            SearchTab connectChat = new SearchTab();
            connectChat.setName("Connect");
            connectChat.setID("Connect");

            SearchTab localEventsChat = new SearchTab();
            localEventsChat.setName("Local Events");
            localEventsChat.setID("LocalEvents");

            SearchTab trendingChat = new SearchTab();
            trendingChat.setName("Trending");
            trendingChat.setID("Trending");

            ArrayList<SearchTab> allTabs = new ArrayList<>();
            allTabs.addAll(universityTabs);
            allTabs.addAll(cityTabs);
            allTabs.add(connectChat);
            allTabs.add(localEventsChat);
            allTabs.add(trendingChat);


            ArrayList<SearchRow> arrayRows = new ArrayList<>();

            ArrayList<Object> rowTabs = new ArrayList<Object>();

            MapTab mTab = new MapTab();

            for(int i = 0; i < Math.ceil(allTabs.size()/2); i++){
                System.out.println(i);
                SearchTab leftTab = allTabs.get(2*i);
                SearchTab rightTab = new SearchTab();

                if (2*i + 1 < allTabs.size()){
                    rightTab = allTabs.get(2*i + 1);
                }

                SearchRow column = new SearchRow(leftTab, rightTab);
                arrayRows.add(column);

            }
            rowTabs.add(mTab);
            rowTabs.addAll(arrayRows);



            bundle.putParcelable("User",user);
            intent.putExtras(bundle);

            System.out.println("This far?");

            searchColumnAdapter = new SearchAdapter(rowTabs, bundle, getContext());
            searchView.setAdapter(searchColumnAdapter);
            searchView.setLayoutManager(new LinearLayoutManager(getContext()));

            //end loadTiles

        }

    }

    private boolean needUpdated() {
        if (autoUpdate){
            return true;
        }
        return true;//!(isInside(localCoordinates,userLocation) && isInside(cityCoordinates,userLocation));
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
            String concatUniversityData = concatUniversityDataFull.substring(1,concatUniversityDataFull.length()-1).replace(", ",",");
            String[] universityDataSplit = concatUniversityData.split(",");
            if (!firstLine) {
                LatLng locationLocation = new LatLng(Double.parseDouble(universityDataSplit[5]), Double.parseDouble(universityDataSplit[6]));
                int pop = Integer.parseInt(universityDataSplit[4]);
                universityPlaces.add(new Place(locationLocation, universityDataSplit[1].replace(";",","), pop, universityDataSplit[0], "Universities"));
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
            String concatCityData = concatCityDataFull.substring(1,concatCityDataFull.length()-1).replace(", ",",");
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

}
