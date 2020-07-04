package models;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.jab.ChatActivity;
import com.example.jab.CommentActivity;
import com.example.jab.DirectMessageActivity;
import com.giphy.sdk.core.models.Media;
import com.google.android.gms.common.util.ScopeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import custom_class.Chat;
import custom_class.DirectMessage;
import custom_class.Place;
import custom_class.UserDirectMessages;
import custom_class.UserProfile;

public class DirectMessageModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String TAG = "DirectMessageModel";
    public DirectMessageModel(FirebaseAuth auth, FirebaseFirestore db) {
        this.auth = auth;
        this.db = db;
        storage = FirebaseStorage.getInstance();
    }

    public void loadCheckMessage(final DirectMessageActivity.FirestoreCallBackFirst callback, UserProfile myUser, UserProfile friendUser) {


        DocumentReference docMessagePath = db.collection("Users").document(myUser.getUID()).collection("DirectMessages").document(friendUser.getUID());

        docMessagePath.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String userUID = documentSnapshot.getId();
                    Object sharedPath = documentSnapshot.get("Location");



                    if (sharedPath != null){
                        Timestamp lastMessage = (Timestamp) documentSnapshot.get("Timestamp");
                        String messageType = (String) documentSnapshot.get("MessageType");
                        String name = (String) documentSnapshot.get("Name");
                        UserDirectMessages userDirectMessages = new UserDirectMessages((String)sharedPath,userUID,name,messageType,lastMessage);
                        callback.onCallback(userDirectMessages);
                    }
                    else{
                        Timestamp lastMessage = Timestamp.now();
                        String messageType = "";
                        String name = "";
                        UserDirectMessages userDirectMessages = new UserDirectMessages("",userUID,name,messageType,lastMessage);
                        callback.onCallback(userDirectMessages);
                    }
                }
            }
        });
    }

    public void createSharedPath(final DirectMessageActivity.FirestoreCallBackCreate callback, UserProfile myUser, UserProfile friendUser, String UUID) {


        DocumentReference docMessagePathMy = db.collection("Users").document(myUser.getUID()).collection("DirectMessages").document(friendUser.getUID());
        DocumentReference docMessagePathFriend = db.collection("Users").document(friendUser.getUID()).collection("DirectMessages").document(myUser.getUID());

        Map<String,Object> locationMyUser = new HashMap<>();
        locationMyUser.put("Location",UUID);
        locationMyUser.put("Name",friendUser.getProfileName());
        locationMyUser.put("MessageType","UnreadSent");
        locationMyUser.put("Timestamp",Timestamp.now());

        docMessagePathMy.set(locationMyUser).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Map<String,Object> locationFriendUser = new HashMap<>();
                locationMyUser.put("Location",UUID);
                locationMyUser.put("Name",friendUser.getProfileName());
                locationMyUser.put("MessageType","UnreadReceived");
                locationMyUser.put("Timestamp",Timestamp.now());

                docMessagePathFriend.set(locationFriendUser).addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        callback.onCallback();
                    }
                });
            }
        });


    }

    public void updateSharedPath(final DirectMessageActivity.FirestoreCallBackCreate callback, UserProfile myUser, UserProfile friendUser, String UUID) {


        DocumentReference docMessagePathMy = db.collection("Users").document(myUser.getUID()).collection("DirectMessages").document(friendUser.getUID());
        DocumentReference docMessagePathFriend = db.collection("Users").document(friendUser.getUID()).collection("DirectMessages").document(myUser.getUID());

        Map<String,Object> locationMyUser = new HashMap<>();
        locationMyUser.put("Location",UUID);
        locationMyUser.put("Name",friendUser.getProfileName());
        locationMyUser.put("MessageType","UnreadSent");
        locationMyUser.put("Timestamp",Timestamp.now());

        docMessagePathMy.set(locationMyUser).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Map<String,Object> locationFriendUser = new HashMap<>();
                locationMyUser.put("Location",UUID);
                locationMyUser.put("Name",friendUser.getProfileName());
                locationMyUser.put("MessageType","UnreadReceived");
                locationMyUser.put("Timestamp",Timestamp.now());

                docMessagePathFriend.set(locationFriendUser).addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        callback.onCallback();
                    }
                });
            }
        });


    }

    public void loadDirectMessages(final DirectMessageActivity.FirestoreCallBackSecond callback, String path, UserProfile myUser) {

        CollectionReference colMessagePath = db.collection("DirectMessages").document("Data").collection(path);

        colMessagePath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<DirectMessage> directMessages = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String content = (String) document.get("Content");
                        String photo = (String) document.get("Photo");
                        String gifURL = (String) document.get("GifURL");
                        Boolean boolContent = (Boolean) document.get("BoolContent");
                        Boolean boolPhoto = (Boolean) document.get("BoolPhoto");
                        Boolean boolGifURL = (Boolean) document.get("BoolGifURL");
                        String sender = (String) document.get("User");
                        Timestamp timestamp = (Timestamp) document.get("Timestamp");

                        if (sender.equals(myUser.getUID())){
                            DirectMessage directMessage = new DirectMessage(content,photo,gifURL,boolContent,boolPhoto,boolGifURL,sender,timestamp);
                            directMessages.add(directMessage);
                        }
                        else{
                            DirectMessage directMessage = new DirectMessage(content,photo,gifURL,boolContent,boolPhoto,boolGifURL,sender,timestamp);
                            directMessages.add(directMessage);
                        }
                    }
                    callback.onCallback(directMessages);
                }
                else {
                    callback.onCallback(null);
                }
            }
        });
    }


    public void send_gif(DirectMessageActivity.FirestoreCallBackMessage callBack, Media media, UserProfile myUser,String sharedPath) {
        String content = "";
        if (media.getUrl().lastIndexOf("-") != -1) {
            media.getUrl().substring(media.getUrl().lastIndexOf("-") + 1);
        } else{
            media.getUrl().substring(media.getUrl().lastIndexOf("gifs/") + 5);
        }
        String url = "https://media.giphy.com/media/" + content + "/giphy.gif";
        String photo = "";
        Boolean boolContent = false;
        Boolean boolPhoto = false;
        Boolean boolGifURL = true;
        String sender = myUser.getUID();
        Timestamp timestamp = Timestamp.now();

        Map<String,Object> directMessage = new HashMap<>();
        directMessage.put("Content",content);
        directMessage.put("GifURL",url);
        directMessage.put("Photo",photo);
        directMessage.put("BoolContent",boolContent);
        directMessage.put("BoolPhoto",boolPhoto);
        directMessage.put("BoolGifURL",boolGifURL);
        directMessage.put("User",sender);
        directMessage.put("Timestamp",timestamp);

        DocumentReference documentReference = db.collection("DirectMessages").document(sharedPath);

        documentReference.set(directMessage);

        DirectMessage dm = new DirectMessage(content,photo,url,boolContent,boolPhoto,boolGifURL,sender,timestamp);

        callBack.onCallback(dm);
    }

    public void send_message(DirectMessageActivity.FirestoreCallBackMessage callBack, String text, UserProfile myUser,String sharedPath) {
        String content = text;
        String url = "";
        String photo = "";
        Boolean boolContent = true;
        Boolean boolPhoto = false;
        Boolean boolGifURL = false;
        String sender = myUser.getUID();
        Timestamp timestamp = Timestamp.now();

        Map<String,Object> directMessage = new HashMap<>();
        directMessage.put("Content",content);
        directMessage.put("GifURL",url);
        directMessage.put("Photo",photo);
        directMessage.put("BoolContent",boolContent);
        directMessage.put("BoolPhoto",boolPhoto);
        directMessage.put("BoolGifURL",boolGifURL);
        directMessage.put("User",sender);
        directMessage.put("Timestamp",timestamp);

        DocumentReference documentReference = db.collection("DirectMessages").document(sharedPath);

        documentReference.set(directMessage);

        DirectMessage dm = new DirectMessage(content,photo,url,boolContent,boolPhoto,boolGifURL,sender,timestamp);

        callBack.onCallback(dm);
    }

    public void send_photo(DirectMessageActivity.FirestoreCallBackMessage callBack, Bitmap bitmap, UserProfile myUser, String sharedPath) {
        String content = "";
        String url = "";
        String uuid = UUID.randomUUID().toString();
        String photo = uuid;
        Boolean boolContent = false;
        Boolean boolPhoto = true;
        Boolean boolGifURL = false;
        String sender = myUser.getUID();
        Timestamp timestamp = Timestamp.now();

        Map<String,Object> directMessage = new HashMap<>();
        directMessage.put("Content",content);
        directMessage.put("GifURL",url);
        directMessage.put("Photo",photo);
        directMessage.put("BoolContent",boolContent);
        directMessage.put("BoolPhoto",boolPhoto);
        directMessage.put("BoolGifURL",boolGifURL);
        directMessage.put("User",sender);
        directMessage.put("Timestamp",timestamp);

        DocumentReference documentReference = db.collection("DirectMessages").document(sharedPath);

        documentReference.set(directMessage);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/DirectMessages/"+uuid);
        ref.putBytes(byteArray);

        DirectMessage dm = new DirectMessage(content,photo,url,boolContent,boolPhoto,boolGifURL,sender,timestamp);

        callBack.onCallback(dm);
    }

}
