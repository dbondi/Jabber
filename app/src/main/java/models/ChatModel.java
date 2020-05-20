package models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.ChatActivity;
import com.example.jab.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import custom_class.Chat;
import custom_class.HelperFunctions;

public class ChatModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String TAG = "ChatModel";
    public ChatModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
    }

    public void loadLocalChats(String cityLocation, HelperFunctions.Point[] cityCoordinates, String cityLocationKey, String localLocation, HelperFunctions.Point[] localCoordinates, String localLocationKey) {
        db.collection("Chats").document("Local");
    }

    public void loadCityChats(final ChatActivity.FirestoreCallBack callback, String cityLocation, HelperFunctions.Point[] cityCoordinates, String cityLocationKey, String localLocation, HelperFunctions.Point[] localCoordinates, String localLocationKey) {
        CollectionReference docChats = db.collection("Chats").document("Cities").collection(cityLocationKey);
        List<String> listOfDocuments = new ArrayList<>();
        docChats.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Chat> chats = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listOfDocuments.add(document.getId());
                        String content = (String) document.get("Content");
                        String imageID = (String) document.get("Image");
                        GeoPoint location = (GeoPoint) document.get("Location");
                        Timestamp timestamp = (Timestamp) document.get("Timestamp");
                        String userUID = (String) document.get("User");
                        Integer commentNumber = (Integer) document.get("CommentNumber");
                        Integer likeNumber = (Integer) document.get("likeNumber");
                        Chat chat = new Chat(content,imageID,location,timestamp,userUID,commentNumber,likeNumber);
                        chats.add(chat);
                    }
                    callback.onCallback(chats);
                    Log.d(TAG, listOfDocuments.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                        callback.onCallback(null);
                }
            }
        });

    }
}
