package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.CommentActivity;
import com.example.jab.HomeActivity;
import com.example.jab.MyProfileActivity;
import com.example.jab.NewChatActivity;
import com.example.jab.ProfileActivity;
import com.example.jab.SearchActivity;
import com.example.jab.SearchTabActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.UserProfile;

public class ChatController {

    private FirebaseAuth auth;
    private Context context;

    public ChatController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void commentSection(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces,Place place, String postKey, UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putString("MessageID",postKey);
        bundle.putParcelable("User",user);
        bundle.putParcelable("Place",place);


        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void createMsgBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces,  UserProfile user, Place place) {
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

    public void chatBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, NewChatActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);

    }

    public void profileBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putInt("HighlightNumber", 5);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, MyProfileActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void userProfileBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putInt("HighlightNumber", 2);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }

    public void homeBtn(ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        bundle.putString("caller", "com.example.jab.ChatActivity");
        bundle.putParcelable("User",user);

        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(intent);
    }
}
