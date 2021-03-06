package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.DirectMessageSearchActivity;
import com.example.jab.HomeActivity;
import com.example.jab.MyProfileActivity;
import com.example.jab.NotificationActivity;
import com.example.jab.SearchTabActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.UserProfile;

public class NotificationController {
    private FirebaseAuth auth;
    private Context context;

    public NotificationController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void friendRequestButton() {
    }

    public void profileTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, MyProfileActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void notificationTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void homeTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void directMessageTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, DirectMessageSearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }


    public void chatTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void searchTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.NotificationActivity");
        bundle.putParcelable("User",user);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;


        Intent intent = new Intent(context, SearchTabActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void goBack(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) throws ClassNotFoundException {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putParcelable("User",user);
        String classCalled = bundle.getParcelable("caller");
        Intent intent = new Intent(context, Class.forName(classCalled));
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

}
