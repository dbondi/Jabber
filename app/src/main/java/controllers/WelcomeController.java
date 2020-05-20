package controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.jab.LoginActivity;
import com.example.jab.RegisterActivity;
import com.example.jab.RegisterPhoneActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.RegisterModel;

public class WelcomeController {
    private Context context;
    private FirebaseAuth auth;


    public WelcomeController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public void clickLogin() {
        Intent login = new Intent(context, LoginActivity.class);
        context.startActivity(login);
    }

    public void clickCreate() {
        Intent register = new Intent(context, RegisterActivity.class);
        context.startActivity(register);
    }

    public boolean tryAutoLogin() {
        return false;//auth.getCurrentUser() != null;
    }

    public void loadDataBase() throws FileNotFoundException {
        FirebaseFirestore db;
        Map<String, Object> citiesData = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            //TODO change path if on new device
            Object obj = parser.parse(new FileReader("JabberName\\Locations\\Cities\\cities.json"));
            System.out.println(obj.toString());
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray features = (JSONArray) jsonObject.get("features");


            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            for(int i = 0; i < features.size(); i++){
                Map<String, Object> cityData = new HashMap<>();
                JSONObject childJSON = (JSONObject) features.get(i);
                JSONObject propChildJSON = (JSONObject)childJSON.get("properties");
                cityData.put("Name",propChildJSON.get("Name").toString());
                String id = propChildJSON.get("ID").toString();
                JSONObject geoChildJSON = (JSONObject)childJSON.get("geometry");
                JSONArray coordinatesArray = ((JSONArray)((JSONArray)geoChildJSON.get("coordinates")).get(0));

                ArrayList<GeoPoint> geoPointArrayList = new ArrayList<>();
                for(int j = 0; j < coordinatesArray.size(); j++){
                    ArrayList<Double> coordinates = (ArrayList)coordinatesArray.get(j);
                    GeoPoint geoPoint = new GeoPoint(coordinates.get(0),coordinates.get(1));
                    geoPointArrayList.add(geoPoint);
                }
                cityData.put("Coordinates",geoPointArrayList);
                citiesData.put(id,cityData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db = FirebaseFirestore.getInstance();

        db.collection("Locations").document("Cities").set(citiesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Did work");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Didnt work");
                    }
                });


    }
}
