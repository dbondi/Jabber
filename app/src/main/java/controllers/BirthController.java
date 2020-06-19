package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.FirstNameActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.UserProfile;

public class BirthController {
    private Context context;
    private FirebaseAuth auth;

    public BirthController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void addAgeContinue(UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, FirstNameActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
