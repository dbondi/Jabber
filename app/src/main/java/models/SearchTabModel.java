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

public class SearchTabModel {
    private FirebaseAuth auth;
    private String TAG = "SearchTabModel";

    public SearchTabModel(FirebaseAuth auth) {
        this.auth = auth;
    }
}