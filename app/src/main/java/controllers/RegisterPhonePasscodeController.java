package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.HomeActivity;
import com.example.jab.RegisterPhonePasscodeActivity;
import com.google.firebase.auth.FirebaseAuth;

import custom_class.User;

public class RegisterPhonePasscodeController {
    private Context context;
    private FirebaseAuth auth;

    public RegisterPhonePasscodeController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public void continueToApp(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
