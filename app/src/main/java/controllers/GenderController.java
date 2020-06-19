package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.UserProfile;

public class GenderController {
    private Context context;
    private FirebaseAuth auth;

    public GenderController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void addGenderContinue(UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
