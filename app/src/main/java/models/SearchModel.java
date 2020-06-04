package models;

import android.location.Location;
import android.util.Log;

import com.example.jab.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_class.PointMap;

import static custom_class.HelperFunctions.isInside;

public class SearchModel {
    private FirebaseAuth auth;
    private String TAG = "SearchModel";

    public SearchModel( FirebaseAuth auth) {
        this.auth = auth;
    }

    /*
    public void findCityLocation(final SearchActivity.FirestoreCallBackFirst callback, Location userLocation) throws InterruptedException {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations/Cities");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UIs
                Map<String, Object> post = (Map<String, Object>) dataSnapshot.getValue();
                for (String key : post.keySet()) {
                    Map<String, Object> secondMap = (Map<String, Object>) post.get(key);
                    ArrayList<Object> coordinates = (ArrayList<Object>) secondMap.get("Coordinates");
                    ArrayList<PointMap> points = new ArrayList<>();
                    for (int i = 0; i < coordinates.size(); i++) {
                        Map<String, Double> coordinate = (Map<String, Double>) coordinates.get(i);
                        points.add(new PointMap(coordinate.get("latitude"), coordinate.get("longitude")));
                    }
                    PointMap pointLocation = new PointMap(userLocation.getLatitude(), userLocation.getLongitude());
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

                callback.onCallback(null);
            }
        });
    }

    public void findLocalLocation(final SearchActivity.FirestoreCallBackSecond callback, Location userLocation, String keyCityLocation) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations/Local/" + keyCityLocation + "/features");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UIs
                ArrayList<Object> post = (ArrayList<Object>) dataSnapshot.getValue();
                int key = -1;
                for (int j = 0; j < post.size(); j++) {
                    Map<String, Object> main = ((Map<String, Object>) post.get(j));
                    key = j;
                    ArrayList<Object> secondMap = (ArrayList<Object>) ((Map<String, Object>) ((Map<String, Object>) post.get(key)).get("geometry")).get("coordinates");
                    ArrayList<Object> coordinates = (ArrayList<Object>) secondMap.get(0);
                    ArrayList<PointMap> points = new ArrayList<>();
                    for (int i = 0; i < coordinates.size(); i++) {
                        ArrayList<Object> coordinate = (ArrayList<Object>) coordinates.get(i);
                        points.add(new PointMap((double)coordinate.get(0), (double)coordinate.get(1)));
                    }
                    PointMap pointLocation = new PointMap(userLocation.getLatitude(), userLocation.getLongitude());
                    boolean inside = isInside(points, pointLocation);
                    if (inside) {
                        String loc = (String) ((Map<String, Object>) main.get("properties")).get("Name");
                        Map<String, Object> locationData = new HashMap<>();
                        locationData.put("Name", loc);
                        locationData.put("Coordinates", points);
                        locationData.put("LocationKey", main.get("id"));
                        callback.onCallback(locationData);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("Whoops");
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                System.out.println(databaseError.toException());
                callback.onCallback(null);

            }
        });

    }
    */


}
