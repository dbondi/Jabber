package com.example.jab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import controllers.NewMessageController;
import custom_class.NoPhoneUser;

public class NewMessageActivity extends AppCompatActivity {
    private Button backBtn;
    private NewMessageController controller;
    private FirebaseAuth auth;
    private int local_city;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        auth = FirebaseAuth.getInstance();

        controller = new NewMessageController(auth,this);

        backBtn = findViewById(R.id.back_btn);

        local_city = getIntent().getIntExtra("LocalCity",0);
        System.out.println("Local City");
        System.out.println(local_city);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                controller.goBackToHome(local_city);
            }
        });
    }




}
