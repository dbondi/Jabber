package com.example.jab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import controllers.FirstNameController;
import custom_class.UserProfile;

public class FirstNameActivity extends AppCompatActivity {
    private FirstNameController controller;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private final String TAG = "FirstNameActivity";
    private EditText firstName;
    private Button continueBtn;
    private TextView incorrectName;
    private UserProfile user;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_name);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new FirstNameController(this, auth);

        firstName = findViewById(R.id.first_name_register);
        continueBtn = findViewById(R.id.button_continue);
        incorrectName = findViewById(R.id.incorrect_code);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");

        loadingBar = findViewById(R.id.loading_phone);
        incorrectName = findViewById(R.id.textIncorrectName);

        loadingBar.setVisibility(View.INVISIBLE);
        incorrectName.setVisibility(View.INVISIBLE);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uFirst = firstName.getText().toString();
                if (uFirst.equals("")){
                    incorrectName.setVisibility(View.VISIBLE);
                }
                else{
                    user.setProfileName(firstName.getText().toString());
                    controller.addNameContinue(user);
                }
            }



        });


    }


}
