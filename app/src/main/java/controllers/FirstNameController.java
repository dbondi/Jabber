package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.GenderActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.UserProfile;

public class FirstNameController {
    private Context context;
    private FirebaseAuth auth;

    public FirstNameController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void addNameContinue(UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, GenderActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
