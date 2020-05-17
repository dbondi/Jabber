package controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jab.ChatActivity;
import com.example.jab.NewMessageActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NewMessageController {
    private FirebaseAuth auth;
    private Context context;

    public NewMessageController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public void goBackToHome(int local_city) {
        Bundle bundle = new Bundle();
        bundle.putInt("LocalCity",local_city);
        Intent intent = new Intent(context, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
