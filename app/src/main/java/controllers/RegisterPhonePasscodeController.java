package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.BirthActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.UserProfile;

public class RegisterPhonePasscodeController {
    private Context context;
    private FirebaseAuth auth;

    public RegisterPhonePasscodeController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public void continueToApp(UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, BirthActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
