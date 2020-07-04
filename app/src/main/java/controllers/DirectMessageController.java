package controllers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class DirectMessageController {
    private FirebaseAuth auth;
    private Context context;

    public DirectMessageController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }
}
