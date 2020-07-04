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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nightonke.jellytogglebutton.JellyToggleButton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import controllers.HomeController;
import custom_class.CSVFile;
import custom_class.Place;
import custom_class.UserProfile;
import models.HomeModel;

import static custom_class.HelperFunctions.distanceAway;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    //TODO if asks for location user needs to say always need to change this
    private static final String COMMA_DELIMITER = ",";
    private JellyToggleButton toggle;
    private LinearLayout messageBtn;
    private TextView textLeft;
    private TextView textRight;
    private TextView localLocationText;
    private int local_city;

    private Button searchTabBtn;
    private Button directMessageTabBtn;
    private Button profileTabBtn;
    private Button notificationTabBtn;
    private Button homeTabBtn;

    private HomeController controller;
    private HomeModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private boolean test = true;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected Location userLocation;

    private UserProfile user;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private ArrayList<Place> universityPlaces = new ArrayList<>();
    private ArrayList<Place> cityPlaces = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Create Map
         */
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        //Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.

        setContentView(R.layout.activity_main);

        loadUniversityLocations();
        loadCityLocations();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
  

        auth = FirebaseAuth.getInstance();

        controller = new HomeController(auth, this);
        model = new HomeModel(auth, db);

        notificationTabBtn = findViewById(R.id.notification_tab);
        directMessageTabBtn = findViewById(R.id.direct_message_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        profileTabBtn = findViewById(R.id.profile_tab);
        homeTabBtn = findViewById(R.id.home_tab);
        localLocationText = findViewById(R.id.localLocationText);

        localLocationText.setText("\uD83D\uDD25");



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        double buttonWidth = heightScreen * .1;

        //ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        //messageBtnLayoutParams.width = (int) buttonWidth;
        //messageBtnLayoutParams.height = (int) buttonWidth;

        //messageBtn.setLayoutParams(messageBtnLayoutParams);

        //messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));


        //Request Location

        find_location();

        System.out.println("BoolPucs");

        System.out.println(user.getBoolPictures());


        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        homeTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.homeTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        profileTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.profileTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        directMessageTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.directMessageTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });
        notificationTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.notificationTabBtn(localUniversityPlaces,localCityPlaces,user);
            }
        });


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
    public void onBackPressed() {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (test) {
            userLocation = location;
            userLocation.setLongitude(-89.408054);
            userLocation.setLatitude(43.077293);
            localCityLocations(userLocation, 5.0);
            localUniversityLocations(userLocation, 5.0);
        } else {
            userLocation = location;
            localCityLocations(userLocation, 5.0);
            localUniversityLocations(userLocation, 5.0);
        }
        /*
        try {

            model.findCityLocation(new HomeActivity.FirestoreCallBackFirst(){


                @Override
                public void onCallback(Map<String,Object> locationData) {

                    if (locationData != null) {
                        cityLocation = (String) locationData.get("Name");
                        cityCoordinates = (ArrayList<PointMap>) locationData.get("Coordinates");
                        cityLocationKey = (String) locationData.get("LocationKey");

                        localLocationText.setText(cityLocation);

                        //find local Location
                        model.findLocalLocation(new HomeActivity.FirestoreCallBackSecond() {

                            @Override
                            public void onCallback(Map<String, Object> locationLocalData) {
                                localLocation = (String) locationLocalData.get("Name");
                                localCoordinates = (ArrayList<PointMap>) locationLocalData.get("Coordinates");
                                localLocationKey = (String) locationLocalData.get("LocationKey");

                                localLocationText.setText(localLocation);

                            }
                        }, userLocation, cityLocationKey);
                    }
                }


            },userLocation);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

    }

    public interface FirestoreCallBackFirst {
        void onCallback(Map<String, Object> locationData);
    }

    public interface FirestoreCallBackSecond {
        void onCallback(Map<String, Object> locationLocalData);
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

    public void localCityLocations(Location location, double milesAway) {
        System.out.println("Lat");
        System.out.println(location.getLatitude());
        System.out.println("Long");
        System.out.println(location.getLongitude());
        localCityPlaces = new ArrayList<>();
        for (Place place : cityPlaces) {
            LatLng local = place.getLocation();
            double miles = distanceAway(location.getLatitude(), local.latitude, location.getLongitude(), local.longitude);
            if (miles <= milesAway) {
                localCityPlaces.add(place);
            }
        }
    }

    public void localUniversityLocations(Location location, double milesAway) {
        localUniversityPlaces = new ArrayList<>();
        for (Place place : universityPlaces) {
            LatLng local = place.getLocation();
            double miles = distanceAway(location.getLatitude(), local.latitude, location.getLongitude(), local.longitude);
            if (miles <= milesAway && place.getPopulation()>666) {
                System.out.println(miles);
                System.out.println(place.getName());
                localUniversityPlaces.add(place);
            }
        }
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
                LatLng locationLocation = new LatLng(Double.parseDouble(universityDataSplit[5]),Double.parseDouble(universityDataSplit[6]));
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
                cityPlaces.add(new Place(locationLocation, cityDataSplit[0], pop, cityDataSplit[7], "Cities"));
            } else {
                firstLine = false;
            }
        }
        System.out.println(System.currentTimeMillis());
    }
}

