package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.HomeActivity;
import com.example.jab.RegisterPhonePasscodeActivity;
import com.example.jab.RegisterWithPhoneActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class RegisterWithPhoneController {
    private Context context;
    private FirebaseAuth auth;

    public RegisterWithPhoneController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public void startPhoneNumberVerification(String phoneNumber, String validateID, PhoneAuthProvider.ForceResendingToken token) {
        Bundle bundle = new Bundle();
        bundle.putString("PhoneNumber", phoneNumber);
        bundle.putString("ValidationID", validateID);
        //TODO handle resend token
        Intent intent = new Intent(context, RegisterPhonePasscodeActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
