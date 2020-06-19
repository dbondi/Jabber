package models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.ProfileActivity;
import com.giphy.sdk.core.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.Date;

import java.util.ArrayList;

import custom_class.UserProfile;

public class ProfileModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "ProfileModel";

    public ProfileModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadUserData(final ProfileActivity.FirestoreCallBack callback, UserProfile user) {
        DocumentReference userDoc = db.collection("Users").document(user.getUID());

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<Boolean> boolPhotos = (ArrayList<Boolean>) document.get("BooleanPhotos");
                    ArrayList<StorageReference> storageReferences = new ArrayList<StorageReference>();
                    int i = 0;
                    for(Boolean boolPhoto: boolPhotos){
                        i=i+1;
                        if (boolPhoto){
                            StorageReference gsReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + i);
                            storageReferences.add(gsReference);
                        }
                    }
                    String about = (String) document.get("About");
                    Date age = (Date) document.get("Age");
                    ArrayList<DocumentReference> pastEvents = (ArrayList<DocumentReference>) document.get("PastEvents");
                    ArrayList<DocumentReference> currentEvents = (ArrayList<DocumentReference>) document.get("CurrentEvents");
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}