package models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.RegisterPhoneActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import custom_class.NoPhoneUser;

public class RegisterModel {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public RegisterModel(FirebaseFirestore db, FirebaseAuth auth) {
        this.db = db;
        this.auth = auth;
    }

}
