package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.HomeActivity;
import com.example.jab.NewChatActivity;
import com.example.jab.ProfileActivity;
import com.example.jab.SearchActivity;
import com.example.jab.StoriesActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.PointMap;
import custom_class.User;

public class HomeController {

    private FirebaseAuth auth;
    private Context context;

    public HomeController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void searchBtn(String cityLocation, ArrayList<PointMap> cityCoordinates, String cityLocationKey, String localLocation, ArrayList<PointMap> localCoordinates, String localLocationKey, User user) {
        Bundle bundle = new Bundle();
        bundle.putString("CityLocation",cityLocation);
        bundle.putParcelableArrayList("CityCoordinates", cityCoordinates);
        bundle.putString("CityLocationKey",cityLocationKey);

        bundle.putString("LocalLocation",localLocation);
        bundle.putParcelableArrayList("LocalCoordinates",localCoordinates);
        bundle.putString("LocalLocationKey",localLocationKey);

        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void profileBtn() {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void storiesBtn() {
        Intent intent = new Intent(context, StoriesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void chatBtn() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void createMessage(int local_city) {
        Bundle bundle = new Bundle();
        bundle.putInt("LocalCity",local_city);
        Intent intent = new Intent(context, NewChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void createMsgBtn(String cityLocation, ArrayList<PointMap> cityCoordinates, String cityLocationKey, String localLocation, ArrayList<PointMap> localCoordinates, String localLocationKey, User user) {
        Bundle bundle = new Bundle();
        bundle.putString("CityLocation",cityLocation);
        bundle.putParcelableArrayList("CityCoordinates", cityCoordinates);
        bundle.putString("CityLocationKey",cityLocationKey);

        bundle.putString("LocalLocation",localLocation);
        bundle.putParcelableArrayList("LocalCoordinates",localCoordinates);
        bundle.putString("LocalLocationKey",localLocationKey);
        bundle.putString("caller", "com.example.jab.HomeActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NewChatActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void createMsgBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.HomeActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NewChatActivity.class);
        intent.putExtras(bundle);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void searchBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.HomeActivity");
        bundle.putParcelable("User",user);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;


        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }
}
