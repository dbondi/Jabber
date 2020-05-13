package controllers;

import android.content.Context;
import android.content.Intent;

import com.example.jab.LoginActivity;
import com.example.jab.RegisterActivity;
import com.example.jab.RegisterPhoneActivity;
import com.google.firebase.auth.FirebaseAuth;

import models.RegisterModel;

public class WelcomeController {
    private Context context;
    private FirebaseAuth auth;


    public WelcomeController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public void clickLogin() {
        Intent login = new Intent(context, LoginActivity.class);
        context.startActivity(login);
    }

    public void clickCreate() {
        Intent register = new Intent(context, RegisterActivity.class);
        context.startActivity(register);
    }

    public boolean tryAutoLogin() {
        return auth.getCurrentUser() != null;
    }
}
