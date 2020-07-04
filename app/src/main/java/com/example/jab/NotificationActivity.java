package com.example.jab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapaters.ChatAdapter;
import adapaters.NotificationAdapter;
import controllers.ChatController;
import controllers.NotificationController;
import custom_class.Chat;
import custom_class.Notification;
import custom_class.NotificationEvent;
import custom_class.Place;
import custom_class.UserProfile;
import models.ChatModel;
import models.NotificationModel;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.ScrollableLayout;

import static custom_class.HelperFunctions.distanceAway;
import static custom_class.HelperFunctions.random_color;

public class NotificationActivity extends AppCompatActivity implements LocationListener {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private NotificationController controller;
    private NotificationModel model;
    private RecyclerView notificationView;
    private NotificationAdapter notificationAdapter;

    private static Context instance;
    private TextView textLeft;
    private TextView textRight;
    private TextView placeText;
    private int local_city;
    private ArrayList<String> randomColor;

    private Place place;
    private LinearLayout messageBtn;

    private Button searchTabBtn;
    private Button directMessageTabBtn;
    private Button notificationTabBtn;
    private Button profileTabBtn;
    private Button homeTabBtn;

    protected Location userLocation;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private boolean test = true;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private ArrayList<Place> universityPlaces = new ArrayList<>();
    private ArrayList<Place> cityPlaces = new ArrayList<>();

    private UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        auth = FirebaseAuth.getInstance();

        randomColor = random_color(11);

        instance = this;

        notificationView = findViewById(R.id.notification_boxes);
        notificationView.setNestedScrollingEnabled(false);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        db = FirebaseFirestore.getInstance();

        searchTabBtn = findViewById(R.id.search_tab);
        directMessageTabBtn = findViewById(R.id.direct_message_tab);
        homeTabBtn = findViewById(R.id.home_tab);
        profileTabBtn = findViewById(R.id.profile_tab);
        notificationTabBtn = findViewById(R.id.notification_tab);

        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        place = bundle.getParcelable("Place");
        user = bundle.getParcelable("User");



        controller = new NotificationController(auth, this);
        model = new NotificationModel(auth, db);

        model.loadNotifications(new NotificationActivity.FirestoreCallBack(){

            @Override
            public void onCallback(ArrayList<NotificationEvent> notificationEvents) {
                System.out.println(notificationEvents.size());
                ArrayList<Notification> notifications = eventsToNotifications(notificationEvents);
                System.out.println("NotificaitonNoc");
                System.out.println(notifications.size());
                notificationView.setLayoutManager(new LinearLayoutManager(getContext()));
                notificationAdapter = new NotificationAdapter(getContext(),bundle,controller);
                notificationAdapter.update(notifications);

                notificationView.setAdapter(notificationAdapter);

            }
        }, user);

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


    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    public interface FirestoreCallBack{
        void onCallback(ArrayList<NotificationEvent> notifications);
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
            userLocation.setLongitude(-89.408054);
            userLocation.setLatitude(43.077293);
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
            double miles = distanceAway(location.getLatitude(),local.latitude,location.getLongitude(),local.longitude);
            if (miles<=milesAway){
                localCityPlaces.add(place);
            }
        }
    }

    public void localUniversityLocations(Location location, double milesAway){
        localUniversityPlaces = new ArrayList<>();
        for(Place place: universityPlaces) {
            LatLng local = place.getLocation();
            double miles = distanceAway(location.getLatitude(),local.latitude,location.getLongitude(),local.longitude);
            if (miles <= milesAway && place.getPopulation()>2666) {
                localUniversityPlaces.add(place);
            }
        }
    }

    @Override
    public void onBackPressed() {
        try {
            controller.goBack(localUniversityPlaces,localCityPlaces,user,place);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    public ArrayList<Notification> eventsToNotifications(ArrayList<NotificationEvent> notificationEvents){
        ArrayList<Notification> notifications = new ArrayList<>();
        for(NotificationEvent notificationEvent:notificationEvents){
            int i = 0;
            Boolean addBool = true;
            for(Notification notification: notifications){
                if(notification.getChatID().equals(notificationEvent.getChatID())&&notification.getChatLike()&&notificationEvent.getChatLike()){
                    ArrayList<String> userIds = notification.getUserIds();
                    ArrayList<StorageReference> userPics = notification.getUserPics();
                    ArrayList<String> userNames = notification.getUserNames();
                    ArrayList<Timestamp> userTimes = notification.getUserTimes();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    ArrayList<Timestamp> times = notification.getUserTimes();
                    Timestamp timeMax = null;
                    int max = 0;
                    for(Timestamp time :times){
                        if(time.getNanoseconds()>max){
                            max = time.getNanoseconds();
                            timeMax = time;
                        }
                    }
                    not.setLastNotification(timeMax);
                    notifications.set(i,not);
                    addBool = false;
                    break;
                }
                else if(notification.getCommentID().equals(notificationEvent.getCommentID())&&notification.getCommentLike()&&notificationEvent.getCommentLike()){
                    ArrayList<String> userIds = notification.getUserIds();
                    ArrayList<StorageReference> userPics = notification.getUserPics();
                    ArrayList<String> userNames = notification.getUserNames();
                    ArrayList<Timestamp> userTimes = notification.getUserTimes();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    ArrayList<Timestamp> times = notification.getUserTimes();
                    Timestamp timeMax = null;
                    int max = 0;
                    for(Timestamp time :times){
                        if(time.getNanoseconds()>max){
                            max = time.getNanoseconds();
                            timeMax = time;
                        }
                    }
                    not.setLastNotification(timeMax);
                    notifications.set(i,not);
                    addBool = false;
                    break;
                }
                else if(notification.getResponseID().equals(notificationEvent.getResponseID())&&notification.getResponseLike()&&notificationEvent.getResponseLike()){
                    ArrayList<String> userIds = notification.getUserIds();
                    ArrayList<StorageReference> userPics = notification.getUserPics();
                    ArrayList<String> userNames = notification.getUserNames();
                    ArrayList<Timestamp> userTimes = notification.getUserTimes();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    ArrayList<Timestamp> times = notification.getUserTimes();
                    Timestamp timeMax = null;
                    int max = 0;
                    for(Timestamp time :times){
                        if(time.getNanoseconds()>max){
                            max = time.getNanoseconds();
                            timeMax = time;
                        }
                    }
                    not.setLastNotification(timeMax);
                    notifications.set(i,not);
                    addBool = false;
                    break;
                }
                else if(notification.getCommentID().equals(notificationEvent.getCommentID())&&notification.getCommentMessage()&&notificationEvent.getCommentMessage()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;
                }
                else if(notification.getResponseID().equals(notificationEvent.getResponseID())&&notification.getResponseMessage()&&notificationEvent.getResponseMessage()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;
                }
                else if(notification.getFriendRequest()&&notificationEvent.getFriendRequest()){
                    ArrayList<String> userIds = notification.getUserIds();
                    ArrayList<StorageReference> userPics = notification.getUserPics();
                    ArrayList<String> userNames = notification.getUserNames();
                    ArrayList<Timestamp> userTimes = notification.getUserTimes();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    ArrayList<Timestamp> times = notification.getUserTimes();
                    Timestamp timeMax = null;
                    int max = 0;
                    for(Timestamp time :times){
                        if(time.getNanoseconds()>max){
                            max = time.getNanoseconds();
                            timeMax = time;
                        }
                    }
                    not.setLastNotification(timeMax);
                    notifications.set(i,not);
                    addBool = false;
                    break;
                }
                else if(notificationEvent.getEventInvite()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;
                }
                else if(notificationEvent.getEventReminder()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;
                }
                else if(notificationEvent.getPrivateMessage()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;
                }
                else if(notificationEvent.getOtherNotification()){
                    ArrayList<String> userIds = new ArrayList<>();
                    ArrayList<StorageReference> userPics = new ArrayList<>();
                    ArrayList<String> userNames = new ArrayList<>();
                    ArrayList<Timestamp> userTimes = new ArrayList<>();

                    userIds.add(notificationEvent.getUserIds());
                    userPics.add(notificationEvent.getUserPics());
                    userNames.add(notificationEvent.getUserNames());
                    userTimes.add(notificationEvent.getUserTimes());

                    Notification not = new Notification(notificationEvent.getContent(),notificationEvent.getChatID(),notificationEvent.getCommentID(),notificationEvent.getResponseID(),notificationEvent.getPrivateMessage(),notificationEvent.getChatLike(),notificationEvent.getCommentLike(),notificationEvent.getResponseLike(),notificationEvent.getCommentMessage(),notificationEvent.getResponseMessage(),notificationEvent.getFriendRequest(),notificationEvent.getEventInvite(),notificationEvent.getEventReminder(),notificationEvent.getOtherNotification(),userIds,userPics,userNames,userTimes,notificationEvent.getPlace());
                    not.setLastNotification(notificationEvent.getUserTimes());
                    notifications.add(not);
                    addBool = false;
                    break;

                }

                i++;
            }
            if(addBool) {
                ArrayList<String> userIds = new ArrayList<>();
                ArrayList<StorageReference> userPics = new ArrayList<>();
                ArrayList<String> userNames = new ArrayList<>();
                ArrayList<Timestamp> userTimes = new ArrayList<>();

                userIds.add(notificationEvent.getUserIds());
                userPics.add(notificationEvent.getUserPics());
                userNames.add(notificationEvent.getUserNames());
                userTimes.add(notificationEvent.getUserTimes());

                Notification not = new Notification(notificationEvent.getContent(), notificationEvent.getChatID(), notificationEvent.getCommentID(), notificationEvent.getResponseID(), notificationEvent.getPrivateMessage(), notificationEvent.getChatLike(), notificationEvent.getCommentLike(), notificationEvent.getResponseLike(), notificationEvent.getCommentMessage(), notificationEvent.getResponseMessage(), notificationEvent.getFriendRequest(), notificationEvent.getEventInvite(), notificationEvent.getEventReminder(), notificationEvent.getOtherNotification(), userIds, userPics, userNames, userTimes, notificationEvent.getPlace());
                not.setLastNotification(notificationEvent.getUserTimes());
                notifications.add(not);
            }

        }
        return notifications;
    }


}
