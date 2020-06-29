package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.Place;
import custom_class.SearchTab;

public class SearchChatController {

    private FirebaseAuth auth;
    private Context context;

    public SearchChatController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
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
