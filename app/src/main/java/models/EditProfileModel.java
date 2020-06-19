package models;

import android.text.Editable;

import androidx.annotation.NonNull;

import com.example.jab.EditProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_class.UserProfile;

public class EditProfileModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "EditProfileModel";
    public EditProfileModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }


    public void saveInfo(String about,UserProfile user) {
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("About",about);
        DocumentReference docReference = db.collection("Users").document(user.getUID());
        docReference.update(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        });
    }

    public void addBoolInfo(ArrayList<Boolean> boolInfo, UserProfile user) {
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("PhotoBooleans",boolInfo);
        DocumentReference docReference = db.collection("Users").document(user.getUID());
        docReference.update(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        });
    }

    public void loadUserData(EditProfileActivity.FirestoreCallBack firestoreCallBack) {
    }
}
