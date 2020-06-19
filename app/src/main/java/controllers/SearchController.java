package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.MyProfileActivity;
import com.example.jab.HomeActivity;
import com.example.jab.MapActivity;
import com.example.jab.SearchActivity;
import com.example.jab.StoriesActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.SearchTab;
import custom_class.UserProfile;

public class SearchController {
    private FirebaseAuth auth;
    private Context context;

    public SearchController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void searchBtn() {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }


    public void storiesBtn() {
        Intent intent = new Intent(context, StoriesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void profileBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchActivty");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, MyProfileActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void homeBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }
    public void chatBtn() {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void goToMap() {
        Intent intent = new Intent(context, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void goToTab(SearchTab tab, Bundle savedInstanceState, Place place) {
        if(tab.getType().equals("CityTab")){
            savedInstanceState.putParcelable("Place",place);

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(savedInstanceState);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
            context.startActivity(intent);
        }
        else if(tab.getType().equals("UniversityTab")){
            savedInstanceState.putParcelable("Place",place);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
            context.startActivity(intent);
        }
        else if(tab.getType().equals("Deals")){

        }
        else if(tab.getType().equals("Connect")){

        }
        else if(tab.getType().equals("LocalEvents")){

        }
        else if(tab.getType().equals("Trending")){

        }
    }
}
