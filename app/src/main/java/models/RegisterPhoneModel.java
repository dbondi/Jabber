package models;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import custom_class.NoPhoneUser;
import custom_class.Users;

public class RegisterPhoneModel {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private final String TAG = "RegisterPhoneModel";

    public RegisterPhoneModel(FirebaseFirestore db, FirebaseAuth auth) {
        this.db = db;
        this.auth = auth;
    }

    public Task<Void> publishUser(Users user) {
        FirebaseUser currUser = auth.getCurrentUser();
        return currUser.updateProfile(buildUserProfile(user.getFirstName() + " " + user.getLastName()));
    }

    private UserProfileChangeRequest buildUserProfile(String name) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(name);
        return builder.build();
    }

}
