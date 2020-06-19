package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.EditProfileActivity;
import com.example.jab.HomeActivity;
import com.example.jab.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.UserProfile;

public class MyController {

    private FirebaseAuth auth;
    private Context context;

    public MyController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void searchBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.EditProfileActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void homeBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.EditProfileActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void chatBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.EditProfileActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void editProfile(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.MyProfileActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }
}
