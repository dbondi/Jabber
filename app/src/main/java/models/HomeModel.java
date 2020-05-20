package models;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_class.HelperFunctions;

import static custom_class.HelperFunctions.isInside;

public class HomeModel implements OnMapReadyCallback {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String TAG = "ChatModel";

    public HomeModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
    }


    public void findCityLocation(final HomeActivity.FirestoreCallBack callback, Location userLocation) throws InterruptedException {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations/Cities");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UIs
                Map<String, Object> post = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println(post);
                for (String key : post.keySet()) {
                    System.out.println(key);
                    Map<String, Object> secondMap = (Map<String, Object>) post.get(key);
                    ArrayList<Object> coordinates = (ArrayList<Object>) secondMap.get("Coordinates");
                    System.out.println(coordinates);
                    HelperFunctions.Point[] points = new HelperFunctions.Point[coordinates.size()];
                    for (int i = 0; i < coordinates.size(); i++) {
                        Map<String, Double> coordinate = (Map<String, Double>) coordinates.get(i);
                        points[i] = new HelperFunctions.Point(coordinate.get("latitude"), coordinate.get("longitude"));
                    }
                    HelperFunctions.Point pointLocation = new HelperFunctions.Point(userLocation.getLatitude(), userLocation.getLongitude());
                    boolean inside = isInside(points, pointLocation);
                    if (inside) {
                        String loc = (String) secondMap.get("Name");
                        Map<String, Object> locationData = new HashMap<>();
                        locationData.put("Name", loc);
                        locationData.put("Coordinates", points);
                        locationData.put("LocationKey", key);
                        callback.onCallback(locationData);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        callback.onCallback(null);
    }

    public void findLocalLocation(final HomeActivity.FirestoreCallBack callback, Location userLocation, String keyCityLocation) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations/Local/" + keyCityLocation + "/features");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UIs
                Map<String, Object> post = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println(post);
                for (String key : post.keySet()) {
                    System.out.println(key);
                    Map<String, Object> secondMap = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) post.get(key)).get("geometry")).get("coordinates");
                    ArrayList<Object> coordinates = (ArrayList<Object>) secondMap.get("0");
                    System.out.println(coordinates);
                    HelperFunctions.Point[] points = new HelperFunctions.Point[coordinates.size()];
                    for (int i = 0; i < coordinates.size(); i++) {
                        Map<String, Double> coordinate = (Map<String, Double>) coordinates.get(i);
                        points[i] = new HelperFunctions.Point(coordinate.get("latitude"), coordinate.get("longitude"));
                    }
                    HelperFunctions.Point pointLocation = new HelperFunctions.Point(userLocation.getLatitude(), userLocation.getLongitude());
                    boolean inside = isInside(points, pointLocation);
                    if (inside) {
                        String loc = (String) ((Map<String, Object>) secondMap.get("properties")).get("Name");
                        Map<String, Object> locationData = new HashMap<>();
                        locationData.put("Name", loc);
                        locationData.put("Coordinates", points);
                        locationData.put("LocationKey", secondMap.get("id"));
                        callback.onCallback(locationData);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        callback.onCallback(null);
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }

}
