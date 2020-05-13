package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jab.RegisterPhoneActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import custom_class.NoPhoneUser;


public class RegisterController {
    private Context context;
    private FirebaseAuth auth;

    public RegisterController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    public void askUserForPhone(NoPhoneUser noPhoneUser) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("NoPhoneUser",noPhoneUser);
        Intent intent = new Intent(context, RegisterPhoneActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
