package models;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.ProfileActivity;
import com.giphy.sdk.core.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import adapaters.ChatAdapter;
import custom_class.Place;
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

    public void loadUserData(final ProfileActivity.FirestoreCallBackProfile callback, UserProfile user) {
        DocumentReference userDoc = db.collection("Users").document(user.getUID());

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<Boolean> boolPhotos = (ArrayList<Boolean>) document.get("PhotoBooleans");
                    ArrayList<String> storageReferences = new ArrayList<String>();
                    int i = 0;
                    for(Boolean boolPhoto: boolPhotos){
                        i=i+1;
                        if (boolPhoto){
                            String gsReference = "gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + i;
                            storageReferences.add(gsReference);
                        }
                    }
                    String about = (String) document.get("About");
                    Timestamp age = (Timestamp) document.get("Age");
                    String gender = (String) document.get("Gender");
                    String name = (String) document.get("Name");
                    ArrayList<DocumentReference> pastEvents = (ArrayList<DocumentReference>) document.get("PastEvents");
                    ArrayList<DocumentReference> currentEvents = (ArrayList<DocumentReference>) document.get("CurrentEvents");

                    UserProfile userNew = new UserProfile();
                    userNew.setStorageReferences(storageReferences);
                    userNew.setUID(user.getUID());
                    userNew.setAbout(about);
                    userNew.setAge((int) age.getSeconds());
                    userNew.setGender(gender);
                    userNew.setProfileName(name);
                    callback.onCallback(userNew);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    UserProfile user = new UserProfile();
                    callback.onCallback(user);
                }
            }
        });
    }

    public void friendRequestNotification(ProfileActivity.FirestoreCallBack callback, UserProfile user, String userUID) {

        String userID = user.getUID();

        CollectionReference colRef = db.collection("Users").document(userUID).collection("Notifications");

        Map<String, Object> notif = new HashMap();
        String profPicReference = "gs://jabdatabase.appspot.com/UserData/"+userID+"/PhotoReferences/pic1";

        notif.put("Content","");
        notif.put("ChatID","");
        notif.put("CommentID", "");
        notif.put("ResponseID", "");
        notif.put("PrivateMessage", false);
        notif.put("ChatLike", false);
        notif.put("CommentLike", false);
        notif.put("ResponseLike", false);
        notif.put("CommentMessage", false);
        notif.put("ResponseMessage", false);
        notif.put("FriendRequest", true);
        notif.put("EventInvite", false);
        notif.put("EventReminder", false);
        notif.put("OtherNotification", false);
        notif.put("UserId", userID);
        notif.put("UserPic", profPicReference);
        notif.put("UserName", user.getProfileName());
        notif.put("UserTime", Timestamp.now());


        colRef.document(UUID.randomUUID().toString()).set(notif);

    }
}