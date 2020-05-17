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
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import controllers.ChatController;
import models.ChatModel;

public class ChatActivity extends AppCompatActivity implements LocationListener {
    private JellyToggleButton toggle;
    private Button messageBtn;
    private TextView textLeft;
    private TextView textRight;
    private int local_city;

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;

    private ChatController controller;
    private ChatModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Create Map
         */
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.



        setContentView(R.layout.local_overlay);
        auth = FirebaseAuth.getInstance();

        controller = new ChatController(auth, this);
        model = new ChatModel(auth, db);
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
        if (local_city == 0) {
            messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));
        } else if (local_city == 1) {
            messageBtn.setBackground(getResources().getDrawable(R.drawable.round_button_purple));
            toggle.toggleImmediately();
        }

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
                controller.searchBtn();
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }



    @Override
    public void onBackPressed() {
    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
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