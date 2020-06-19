package models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.Date;

import java.util.ArrayList;

import custom_class.UserProfile;

public class WelcomeModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "EditProfileModel";
    public WelcomeModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadMyData(WelcomeActivity.FirestoreCallBack callback) {
        DocumentReference userDoc = db.collection("Users").document("hYCu9VzlvQMnY0QkIKMNZQ32ylC3");

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<Boolean> boolPhotos = (ArrayList<Boolean>) document.get("PhotoBooleans");
                    ArrayList<String> storageReferences = new ArrayList<>();
                    int i = 0;
                    for(Boolean boolPhoto: boolPhotos){
                        i=i+1;
                        if (boolPhoto){
                            String gsReference = "gs://jabdatabase.appspot.com/UserData/hYCu9VzlvQMnY0QkIKMNZQ32ylC3/PhotoReferences/pic" + i;
                            storageReferences.add(gsReference);
                        }
                    }
                    String about = (String) document.get("About");
                    Timestamp age = (Timestamp) document.get("Age");
                    String gender = (String) document.get("Gender");
                    String name = (String) document.get("Name");
                    ArrayList<DocumentReference> pastEvents = (ArrayList<DocumentReference>) document.get("PastEvents");
                    ArrayList<DocumentReference> currentEvents = (ArrayList<DocumentReference>) document.get("CurrentEvents");

                    UserProfile user = new UserProfile();
                    user.setStorageReferences(storageReferences);
                    user.setAbout(about);
                    user.setAge((int) age.getSeconds());
                    user.setGender(gender);
                    user.setProfileName(name);
                    user.setUID("hYCu9VzlvQMnY0QkIKMNZQ32ylC3");
                    user.setBoolPictures(boolPhotos);
                    callback.onCallback(user);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    UserProfile user = new UserProfile();
                    callback.onCallback(user);
                }
            }
        });
    }
}
