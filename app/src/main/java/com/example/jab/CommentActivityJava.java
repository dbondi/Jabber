package com.example.jab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import adapaters.CommentAdapter;
import controllers.CommentControllerJava;
import custom_class.Comment;
import custom_class.PointMap;
import custom_class.User;
import models.CommentModelJava;

public class CommentActivityJava extends AppCompatActivity implements LocationListener {
    protected Location userLocation;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private CommentControllerJava controller;
    private CommentModelJava model;
    private RecyclerView commentView_1;
    private RecyclerView commentView_2;
    private RecyclerView commentView_3;
    private CommentAdapter commentAdapter_1;
    private CommentAdapter commentAdapter_2;
    private CommentAdapter commentAdapter_3;

    private static Context instance;

    private Button messageBtn;
    private TextView textLeft;
    private TextView textRight;
    private TextView localLocationText;
    private int local_city;

    private Button searchTabBtn;
    private Button chatTabBtn;
    private Button storiesTabBtn;
    private Button profileTabBtn;


    //locationData
    private String cityLocation = null;
    private ArrayList<PointMap> cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private ArrayList<PointMap> localCoordinates = null;
    private String localLocationKey = null;

    private String messageID;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        auth = FirebaseAuth.getInstance();

        instance = this;

        commentView_1 = findViewById(R.id.comments_1);
        commentView_2 = findViewById(R.id.comments_2);
        commentView_3 = findViewById(R.id.comments_3);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        db = FirebaseFirestore.getInstance();

        cityLocation = bundle.getString("CityLocation");
        cityCoordinates = bundle.getParcelableArrayList("CityCoordinates");
        cityLocationKey = bundle.getString("CityLocationKey");

        localLocation = bundle.getString("LocalLocation");
        localCoordinates = bundle.getParcelableArrayList("LocalCoordinates");
        localLocationKey = bundle.getString("LocalLocationKey");

        messageID = bundle.getString("MessageID");

        user = bundle.getParcelable("User");

        //ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        //messageBtnLayoutParams.width = (int) buttonWidth;

        //messageBtn.setLayoutParams(messageBtnLayoutParams);


        //ViewGroup.LayoutParams textLeftLayoutParams = textLeft.getLayoutParams();
        //textLeftLayoutParams.width = widthScreen - ((int) buttonWidth) - 30;

        //textLeft.setLayoutParams(textLeftLayoutParams);


        //ViewGroup.LayoutParams textRightLayoutParams = textRight.getLayoutParams();
        //textRightLayoutParams.width = 30;

        //textRight.setLayoutParams(textRightLayoutParams);

        local_city = getIntent().getIntExtra("LocalCity", 0);

        //messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        controller = new CommentControllerJava(auth, this);
        model = new CommentModelJava(auth, db);
        model.loadComments(new CommentActivityJava.FirestoreCallBack(){

            @Override
            public void onCallback(ArrayList<Comment> comments) {

                commentAdapter_1 = new CommentAdapter(user,getContext());
                commentAdapter_2 = new CommentAdapter(user,getContext());
                commentAdapter_3 = new CommentAdapter(user,getContext());

                ArrayList<Comment> comments_1 = getCommentsPart(comments,1);
                ArrayList<Comment> comments_2 = getCommentsPart(comments,2);
                ArrayList<Comment> comments_3 = getCommentsPart(comments,3);

                commentAdapter_1.update(comments_1);
                commentAdapter_2.update(comments_2);
                commentAdapter_3.update(comments_3);

                commentView_1.setAdapter(commentAdapter_1);
                commentView_2.setAdapter(commentAdapter_2);
                commentView_3.setAdapter(commentAdapter_3);

                commentView_1.setLayoutManager(new LinearLayoutManager(getContext()));
                commentView_2.setLayoutManager(new LinearLayoutManager(getContext()));
                commentView_3.setLayoutManager(new LinearLayoutManager(getContext()));


            }


        },cityLocation, cityCoordinates, cityLocationKey, localLocation, localCoordinates, localLocationKey, messageID, user);


    }

    private ArrayList<Comment> getCommentsPart(ArrayList<Comment> comments, int i) {
        ArrayList<Comment> columnComments = new ArrayList<>();
        for(Comment comment: comments){
            if (comment.getColumnNumber()==i){
                columnComments.add(comment);
            }
        }
        return columnComments;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    public interface FirestoreCallBack{
        void onCallback(ArrayList<Comment> chats);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
