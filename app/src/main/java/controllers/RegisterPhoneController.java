package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.HomeActivity;
import com.giphy.sdk.core.models.User;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.UserProfile;


public class RegisterPhoneController {
    private Context context;
    private FirebaseAuth auth;



    public RegisterPhoneController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void clickVerify(UserProfile user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
