package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.DirectMessageSearchActivity;
import com.example.jab.FindChatActivity;
import com.example.jab.MyProfileActivity;
import com.example.jab.HomeActivity;
import com.example.jab.MapActivity;
import com.example.jab.NotificationActivity;
import com.example.jab.SearchActivity;
import com.example.jab.SearchTabActivity;
import com.example.jab.StoriesActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.SearchTab;
import custom_class.TabInfo;
import custom_class.UserProfile;

public class SearchTabController {
    private FirebaseAuth auth;
    private Context context;

    public SearchTabController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void goToTab(TabInfo tab, Bundle savedInstanceState,ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
        bundle.putParcelable("User",user);

        if(tab.getId().equals("100")){
            bundle.putParcelable("Tab",tab);

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("102")){
            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("103")){
            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("104")){
            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("105")){
            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("106")){
            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, FindChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
        else if(tab.getId().equals("107")){

            bundle.putParcelable("Tab",tab);
            Intent intent = new Intent(context, ChatActivity.class);

            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }
    }

    public void profileTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
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
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void directMessageTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, DirectMessageSearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void homeTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void searchTabBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putString("caller", "com.example.jab.SearchTabActivity");
        bundle.putParcelable("User",user);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;


        Intent intent = new Intent(context, SearchTabActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

}
