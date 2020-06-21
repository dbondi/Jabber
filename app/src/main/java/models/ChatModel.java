package models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import custom_class.Chat;
import custom_class.Place;
import custom_class.UserProfile;

public class ChatModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "ChatModel";
    public ChatModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadChat(final ChatActivity.FirestoreCallBack callback, Place place, UserProfile user) {


        CollectionReference docChats = db.collection("Chats").document(place.getType()).collection(place.getIPEDSID());

        List<String> listOfDocuments = new ArrayList<>();

        docChats.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Chat> chats = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listOfDocuments.add(document.getId());
                        String messageID = document.getId();

                        String content = (String) document.get("Content");
                        String imageID = (String) document.get("Image");
                        GeoPoint location = (GeoPoint) document.get("Location");
                        Timestamp timestamp = (Timestamp) document.get("Timestamp");
                        String userUID = (String) document.get("User");
                        String userName = (String) document.get("UserName");
                        Integer commentNumber = ((Number) document.get("CommentNumber")).intValue();
                        Integer likeNumber = ((Number) document.get("LikeNumber")).intValue();

                        Integer imageWidth = ((Number) document.get("ImageWidth")).intValue();
                        Integer imageHeight = ((Number) document.get("ImageHeight")).intValue();

                        Boolean imageBoolean = ((Boolean) document.get("ImageBoolean"));
                        Boolean gifBoolean = ((Boolean) document.get("GifBoolean"));
                        Boolean stringBoolean = ((Boolean) document.get("StringBoolean"));
                        Boolean pollBoolean = ((Boolean) document.get("PollBoolean"));
                        String gifUrl = ((String) document.get("GifURL"));

                        ArrayList<String> color = (ArrayList<String>) document.get("Color");
                        ArrayList<String> pollValues = (ArrayList<String>) document.get("PollValues");
                        ArrayList<String> likeList = (ArrayList<String>) document.get("LikeList");
                        Map<String,Integer> pollVoteList = (Map<String,Integer>) document.get("PollVoteList");
                        ArrayList<Long> pollVotes = (ArrayList<Long>) document.get("PollVotes");

                        StorageReference gsReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/Chats/"+place.getType()+"/" + place.getIPEDSID() + "/" + messageID );


                        StorageReference profPicReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/" + userUID + "/" + "profilePic.jpg");

                        Chat chat = new Chat(content,imageID,location,timestamp,userUID,userName,likeList,pollVoteList,commentNumber,likeNumber,gifUrl,pollValues,pollVotes,stringBoolean,pollBoolean,imageBoolean,gifBoolean,gsReference,profPicReference,imageWidth,imageHeight,messageID,place,color,"a");
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
