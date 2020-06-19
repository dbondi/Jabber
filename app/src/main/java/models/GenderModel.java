package models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import custom_class.UserProfile;

public class GenderModel {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String TAG = "GenderModel";

    public GenderModel(FirebaseFirestore db, FirebaseAuth auth) {
        this.db = db;
        this.auth = auth;
    }

    public void postUser(UserProfile user){
        Map<String,Object> userMap = new HashMap<String,Object>();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(1000*user.getAge());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        userMap.put("PhotoBooleans",user.getBoolPictures());
        userMap.put("Age",new Timestamp(user.getAge(),0));
        userMap.put("Name",user.getProfileName());
        userMap.put("About",user.getAbout());
        userMap.put("Gender",user.getGender());

        DocumentReference docReference = db.collection("Users").document(user.getUID());
        docReference.update(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        });
    }
}
