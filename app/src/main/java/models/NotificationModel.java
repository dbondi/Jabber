package models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jab.ChatActivity;
import com.example.jab.NotificationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controllers.CommentController;
import custom_class.Chat;
import custom_class.Notification;
import custom_class.NotificationEvent;
import custom_class.Place;
import custom_class.UserProfile;

public class NotificationModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "NotificationModel";
    public NotificationModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadNotifications(final NotificationActivity.FirestoreCallBack callback, UserProfile user) {

        CollectionReference likeCollection = db.collection("Users").document(user.getUID()).collection("Notifications");

        likeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<NotificationEvent> notifications = new ArrayList<>();
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String content = (String) document.get("Content");
                        String chatID = (String) document.get("ChatID");
                        String commentID = (String) document.get("CommentID");
                        String responseID = (String) document.get("ResponseID");
                        Boolean privateMessage = (Boolean) document.get("PrivateMessage");
                        Boolean chatLike = (Boolean) document.get("ChatLike");
                        Boolean commentLike = (Boolean) document.get("CommentLike");
                        Boolean responseLike = (Boolean) document.get("ResponseLike");
                        Boolean commentMessage = (Boolean) document.get("CommentMessage");
                        Boolean responseMessage = (Boolean) document.get("ResponseMessage");
                        Boolean friendRequest = (Boolean) document.get("FriendRequest");
                        Boolean eventInvite = (Boolean) document.get("EventInvite");
                        Boolean eventReminder = (Boolean) document.get("EventReminder");
                        Boolean otherNotification = (Boolean) document.get("OtherNotification");
                        String userId = (String) document.get("UserId");
                        StorageReference userPic = storage.getReferenceFromUrl((String) document.get("UserPic"));
                        String userName = (String) document.get("UserName");
                        Timestamp userTime = (Timestamp) document.get("UserTime");

                        GeoPoint placeLocation = (GeoPoint) document.get("PlaceLocation");
                        String placeName = (String) document.get("PlaceName");
                        String placeIPEDSID = (String) document.get("PlaceIPEDSID");
                        String placeType = (String) document.get("PlaceType");
                        Place place = new Place(placeLocation,placeName,0,placeIPEDSID,placeType);


                        NotificationEvent notificationEvent = new NotificationEvent(content,chatID,commentID,responseID,privateMessage,chatLike,commentLike,responseLike,commentMessage,responseMessage,friendRequest,eventInvite,eventReminder,otherNotification,userId,userPic,userName,userTime,place);
                        notifications.add(notificationEvent);

                    }
                    callback.onCallback(notifications);

                }
                else
                {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    callback.onCallback(null);
                }
            }
        });

    }

}
