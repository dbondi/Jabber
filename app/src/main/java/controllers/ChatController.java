package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.CommentActivity;
import com.example.jab.HomeActivity;
import com.example.jab.NewMessageActivity;
import com.example.jab.ProfileActivity;
import com.example.jab.SearchActivity;
import com.example.jab.StoriesActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.PointMap;
import custom_class.User;

public class ChatController {

    private FirebaseAuth auth;
    private Context context;

    public ChatController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void commentSection(String cityLocation, ArrayList<PointMap> cityCoordinates, String cityLocationKey, String localLocation, ArrayList<PointMap> localCoordinates, String localLocationKey, String postKey, User user) {
        Bundle bundle = new Bundle();
        bundle.putString("CityLocation",cityLocation);
        bundle.putParcelableArrayList("CityCoordinates", cityCoordinates);
        bundle.putString("CityLocationKey",cityLocationKey);

        bundle.putString("LocalLocation",localLocation);
        bundle.putParcelableArrayList("LocalCoordinates",localCoordinates);
        bundle.putString("LocalLocationKey",localLocationKey);

        bundle.putString("MessageID",postKey);
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }


    public void createMessage(int local_city) {
        Bundle bundle = new Bundle();
        bundle.putInt("LocalCity",local_city);
        Intent intent = new Intent(context, NewMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
