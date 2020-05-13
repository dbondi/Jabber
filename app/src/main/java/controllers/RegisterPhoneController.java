package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jab.HomeActivity;
import com.example.jab.LoginActivity;
import com.example.jab.RegisterPhoneActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import custom_class.Users;


public class RegisterPhoneController {
    private Context context;
    private FirebaseAuth auth;


    public RegisterPhoneController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void clickVerify(Users user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("User",user);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
