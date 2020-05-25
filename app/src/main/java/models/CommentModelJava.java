package models;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.CommentActivityJava;
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

import custom_class.Chat;
import custom_class.Comment;
import custom_class.PointMap;
import custom_class.User;

public class CommentModelJava {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "CommentModel";

    private static Context instance;

    public CommentModelJava(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }
    public void loadComments(final CommentActivityJava.FirestoreCallBack callback, String cityLocation, ArrayList<PointMap> cityCoordinates, String cityLocationKey, String localLocation, ArrayList<PointMap> localCoordinates, String localLocationKey, String messageID, User user) {
        System.out.println(cityLocationKey);
        System.out.println(messageID);
        CollectionReference docChats = db.collection("Chats").document("Cities").collection(cityLocationKey).document(messageID).collection("Comments");

        List<String> listOfDocuments = new ArrayList<>();

        docChats.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listOfDocuments.add(document.getId());
                        String commentID = document.getId();

                        String content = (String) document.get("Content");
                        String imageID = (String) document.get("Image");
                        GeoPoint location = (GeoPoint) document.get("Location");
                        Timestamp timestamp = (Timestamp) document.get("Timestamp");
                        String userUID = (String) document.get("User");
                        String userName = (String) document.get("UserName");
                        Integer likeNumber = ((Number) document.get("LikeNumber")).intValue();

                        Integer imageWidth = ((Number) document.get("ImageWidth")).intValue();
                        Integer imageHeight = ((Number) document.get("ImageHeight")).intValue();
                        Boolean imageBoolean = (Boolean) document.get("ImageBoolean");
                        Integer columnNumber = ((Number) document.get("ColumnNumber")).intValue();

                        ArrayList<String> likeList = (ArrayList<String>) document.get("LikeList");

                        //ArrayList<String> commentList = (ArrayList<String>) document.get("CommentList");

                        StorageReference gsReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/Comments/Cities/" + cityLocationKey + "/" + messageID + "/" + commentID + "/" + "photo.jpg");

                        StorageReference profPicReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/" + userUID + "/" + "profilePic.jpg");

                        Comment comment = new Comment(content,imageID,location,timestamp,userUID,userName,likeNumber,likeList,gsReference,profPicReference,imageWidth,imageHeight,imageBoolean,columnNumber);
                        comments.add(comment);
                    }
                    callback.onCallback(comments);
                    Log.d(TAG, listOfDocuments.toString());

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    callback.onCallback(null);
                }
            }
        });

    }
    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

}
