package models;

import androidx.annotation.NonNull;

import com.example.jab.ChatActivity;
import com.example.jab.DirectMessageSearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import custom_class.Chat;
import custom_class.Place;
import custom_class.UserDirectMessages;
import custom_class.UserProfile;

public class DirectMessageSearchModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "DirectMessageSearchModel";
    public DirectMessageSearchModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadPaths(final DirectMessageSearchActivity.FirestoreCallBackPaths callback, UserProfile user) {

        CollectionReference docChats = db.collection("Users").document(user.getUID()).collection("DirectMessages");

        docChats.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<UserDirectMessages> paths = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userUID = document.getId();
                        String sharedPath = (String) document.get("Location");
                        Timestamp lastMessage = (Timestamp) document.get("Timestamp");
                        String messageType = (String) document.get("MessageType");
                        String name = (String) document.get("Name");

                        paths.add(new UserDirectMessages(sharedPath,userUID,name,messageType,lastMessage));

                    }
                    callback.onCallback(paths);
                }
            }
        });
    }


}
