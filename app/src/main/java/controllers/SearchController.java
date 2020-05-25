package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.HomeActivity;
import com.example.jab.MapActivity;
import com.example.jab.ProfileActivity;
import com.example.jab.SearchActivity;
import com.example.jab.StoriesActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.SearchTab;

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

    public void goToMap() {
        Intent intent = new Intent(context, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void goToTab(SearchTab tab,Bundle savedInstanceState) {
        if(tab.getID()=="LocalChat"){


            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtras(savedInstanceState);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
            context.startActivity(intent);
        }
        else if(tab.getID()=="CityChat"){

        }
        else if(tab.getID()=="Deals"){

        }
        else if(tab.getID()=="Connect"){

        }
        else if(tab.getID()=="LocalEvents"){

        }
        else if(tab.getID()=="Trending"){

        }
    }
}
