package com.example.jab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;

import controllers.WelcomeController;

public class WelcomeActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener,View.OnClickListener {

    NumberPicker np_Age;
    Button b_Login;
    Button tv_Select_age;
    Button loaddata;
    private WelcomeController controller;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d("Life Cycle Event: ", "In onCreate()");

        np_Age = findViewById(R.id.np_Age);
        b_Login = findViewById(R.id.b_Login);
        loaddata = findViewById(R.id.auth_load);

        tv_Select_age = findViewById(R.id.tv_Select_age);
        tv_Select_age.setEnabled(false);

        b_Login.setOnClickListener(this);
        tv_Select_age.setOnClickListener(this);
        loaddata.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
        controller = new WelcomeController(this,auth);

        final NumberPicker np = np_Age;
        np.setMaxValue(101);
        np.setMinValue(1);
        np.setValue(16);
        np.setWrapSelectorWheel(false);
        //np.setTextColor(0);
        np.setOnValueChangedListener(this);
        //setNumberPickerTextColor(np_Age,Integer.parseInt("f2e9eb",16));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_Login:
                controller.clickLogin();
                break;
            case R.id.tv_Select_age:
                controller.clickCreate();
                break;
            case R.id.auth_load:
                controller.loadDataBase();
                break;

        }
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
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(newVal >= 21 && oldVal < 21){
            tv_Select_age.setEnabled(true);
            tv_Select_age.setText("Create Account");
            tv_Select_age.setBackground(getResources().getDrawable(R.drawable.round_button_roy));
        }

        if(newVal < 21 && oldVal >= 21){
            tv_Select_age.setEnabled(false);
            tv_Select_age.setText("Select your age");
            tv_Select_age.setBackgroundColor(Integer.parseInt("004f4f4f",16));
        }
    }
}
