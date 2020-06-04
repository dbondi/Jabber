package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.CommentActivity;
import com.example.jab.NewChatActivity;
import com.example.jab.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.PointMap;
import custom_class.User;

public class ChatController {

    private FirebaseAuth auth;
    private Context context;

    public ChatController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void commentSection(Place place, String postKey, User user) {
        Bundle bundle = new Bundle();

        bundle.putString("MessageID",postKey);
        bundle.putParcelable("User",user);
        bundle.putParcelable("Place",place);

        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void createMsgBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces,  User user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NewChatActivity.class);
        intent.putExtras(bundle);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void goBack(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, User user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }
}
