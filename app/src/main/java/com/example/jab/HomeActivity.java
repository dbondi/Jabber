package com.example.jab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nightonke.jellytogglebutton.JellyToggleButton;

import java.util.Map;

import controllers.HomeController;
import custom_class.HelperFunctions;
import models.HomeModel;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    private JellyToggleButton toggle;
    private Button messageBtn;
    private TextView textLeft;
    private TextView textRight;
    private TextView localLocationText;
    private int local_city;

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;

    private HomeController controller;
    private HomeModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private boolean test = true;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected Location userLocation;

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
        /**
         * Create Map
         */
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        //Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.

        setContentView(R.layout.local_overlay);
        auth = FirebaseAuth.getInstance();

        controller = new HomeController(auth, this);
        model = new HomeModel(auth, db);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        double buttonWidth = heightScreen * .1;
        textLeft = findViewById(R.id.newMessageTextLeft);
        textRight = findViewById(R.id.newMessageTextRight);
        messageBtn = findViewById(R.id.new_message);

        chatTabBtn = findViewById(R.id.chat_tab);
        searchTabBtn = findViewById(R.id.search_tab);
        storiesTabBtn = findViewById(R.id.stories_tab);
        localLocationText = findViewById(R.id.localLocationText);


        ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        messageBtnLayoutParams.width = (int) buttonWidth;

        messageBtn.setLayoutParams(messageBtnLayoutParams);


        ViewGroup.LayoutParams textLeftLayoutParams = textLeft.getLayoutParams();
        textLeftLayoutParams.width = widthScreen - ((int) buttonWidth) - 30;

        textLeft.setLayoutParams(textLeftLayoutParams);


        ViewGroup.LayoutParams textRightLayoutParams = textRight.getLayoutParams();
        textRightLayoutParams.width = 30;

        textRight.setLayoutParams(textRightLayoutParams);

        local_city = getIntent().getIntExtra("LocalCity", 0);

        messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));


        //Request Location

        find_location();



        storiesTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.storiesBtn();
            }
        });

        searchTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.searchBtn(cityLocation,cityCoordinates,cityLocationKey,localLocation,localCoordinates,localLocationKey);
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(local_city);
                controller.createMessage(local_city);
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

            model.findCityLocation(new HomeActivity.FirestoreCallBack(){

                @Override
                public void onCallback(Map<String,Object> locationData) {
                    cityLocation = (String) locationData.get("Name");
                    cityCoordinates = (HelperFunctions.Point[]) locationData.get("Coordinates");
                    cityLocationKey = (String) locationData.get("LocationKey");

                    localLocationText.setText(cityLocation);

                    //find local Location
                    model.findLocalLocation(new HomeActivity.FirestoreCallBack(){

                        @Override
                        public void onCallback(Map<String,Object> locationData) {
                            localLocation = (String) locationData.get("Name");
                            localCoordinates = (HelperFunctions.Point[]) locationData.get("Coordinates");
                            localLocationKey = (String) locationData.get("LocationKey");

                            localLocationText.setText(localLocation);

                        }
                    },userLocation, cityLocationKey);
                }

            },userLocation);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface FirestoreCallBack{
        void onCallback(Map<String,Object> locationData);
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
}