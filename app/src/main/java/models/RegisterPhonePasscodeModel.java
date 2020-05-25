package models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import custom_class.Users;

public class RegisterPhonePasscodeModel {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private final String TAG = "RegisterPhoneModel";

    public RegisterPhonePasscodeModel(FirebaseFirestore db, FirebaseAuth auth) {
        this.db = db;
        this.auth = auth;
    }
}
