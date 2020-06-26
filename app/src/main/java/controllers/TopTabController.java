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
import custom_class.TabInfo;
import custom_class.UserProfile;

public class TopTabController {
    private FirebaseAuth auth;
    private Context context;

    public TopTabController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void goToTab(TabInfo tab, Bundle savedInstanceState) {
        if(tab.getId().equals("100")){
            savedInstanceState.putParcelable("Tab",tab);

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(savedInstanceState);
            context.startActivity(intent);
        }

    }
}
