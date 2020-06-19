package com.example.jab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.util.ArrayList;

import adapaters.ProfilePictureAdapter;
import controllers.WelcomeController;
import custom_class.UserProfile;
import models.WelcomeModel;

import static custom_class.HelperFunctions.calculateAge;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    //NumberPicker np_Age;
    Button b_Login;
    Button b_login_phone;
    Button tv_Select_age;
    Button skip;
    private WelcomeController controller;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private WelcomeModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d("Life Cycle Event: ", "In onCreate()");

        b_login_phone = findViewById(R.id.connect_with_phone);
        b_Login = findViewById(R.id.b_Login);
        skip = findViewById(R.id.skip);

        //tv_Select_age = findViewById(R.id.tv_Select_age);
        //tv_Select_age.setEnabled(false);

        b_Login.setOnClickListener(this);
        skip.setOnClickListener(this);
        //tv_Select_age.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new WelcomeController(this,auth);
        model = new WelcomeModel(auth,db);

        b_login_phone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_with_phone:
                controller.clickCreateWithPhone();
                break;
            case R.id.skip:
                model.loadMyData(new WelcomeActivity.FirestoreCallBack(){

                    @Override
                    public void onCallback(UserProfile userEditInfo) {
                        controller.skipCreatePhone(userEditInfo);
                    }
                });
                break;

        }
    }

    public interface FirestoreCallBack{
        void onCallback(UserProfile userEditInfo);
    }

    @Override
    protected  void onStart(){
        super.onStart();
        Log.d("Life Cycle Event: ", "In onStart()");
    }

    @Override
    protected  void onPause(){
        super.onPause();
        Log.d("Life Cycle Event: ", "In onPause()");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        //Get rid of memory?
        Log.d("Life Cycle Event: ", "In onStop()");
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        //Get rid of memory
        Log.d("Life Cycle Event: ", "In onDestroy()");
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Log.d("Life Cycle Event: ", "In onResume()");
    }

    /*
    //Creating onActivityResult and checking response code to make sure we get right request.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Checking for request code
        if(requestCode==12345){
            //Successfully signed-in
            if(resultCode==RESULT_OK){
                //Getting the current user
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                //Showing toast
                Toast.makeText(getApplicationContext(),"Successfully signed-in",Toast.LENGTH_SHORT).show();
            }
        }else{
            //Signed-in failed. If response is null the user canceled the sign-in flow using the back button.
            //respose.getError().getErrorCode() handle the error.
            Toast.makeText(getApplicationContext(),"Unabled to sign in",Toast.LENGTH_SHORT).show();
        }
    }
    //Creating sign-in function which is called on button sign-in button click
    public void sign_in(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(getApplicationContext(), "User already sign in signout first", Toast.LENGTH_SHORT).show();
        }else{
            //Choosing Authentication provider
            List<AuthUI.IdpConfig> providers= Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            //Creating and launching sign-in intent
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),12345);
        }
    }
    //Creating sign-out function which is called on button sign-out button click
    public void sign_out(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //User is now signed out
                Toast.makeText(getApplicationContext(),"User is signed out",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void delete_user(View view) {
        AuthUI.getInstance().delete(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User is deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    */
}
