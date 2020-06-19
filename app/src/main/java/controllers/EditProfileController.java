package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import custom_class.Place;
import custom_class.UserProfile;

public class EditProfileController {

    private FirebaseAuth auth;
    private Context context;

    public EditProfileController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void doneBtn(Intent intent, ArrayList<Place> localUniversityPlaces, ArrayList<Place> localCityPlaces, UserProfile user, Place place) throws ClassNotFoundException {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LocalUniversityPlaces",localUniversityPlaces);
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces);
        bundle.putParcelable("Place", place);
        String caller = intent.getStringExtra("caller");
        Class callerClass = Class.forName(caller);
        bundle.putParcelable("User",user);

        Intent newIntent = new Intent(context, callerClass);
        newIntent.putExtras(bundle);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        context.startActivity(newIntent);
    }
}
