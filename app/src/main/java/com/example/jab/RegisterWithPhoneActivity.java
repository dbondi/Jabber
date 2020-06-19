package com.example.jab;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.sapereaude.maskedEditText.MaskedEditText;

import java.util.concurrent.TimeUnit;

import controllers.RegisterPhoneController;
import controllers.RegisterWithPhoneController;
import custom_class.NoPhoneUser;
import custom_class.Users;
import models.RegisterPhoneModel;
import models.RegisterWithPhoneModel;

public class RegisterWithPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterWithPhoneController controller;
    private RegisterWithPhoneModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String TAG = "RegisterWithPhoneActivity";

    private MaskedEditText phone;
    private Boolean moveToValidation;
    private Boolean sendCodeClick;
    private String validateID;
    private PhoneAuthProvider.ForceResendingToken phoneResendToken;

    private String phoneNumber;

    private TextView invPhone;

    private Button sendCode;

    private ProgressBar loadingBar;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_phone);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new RegisterWithPhoneController(this,auth);
        model = new RegisterWithPhoneModel(db, auth);


        phone = findViewById(R.id.phone_register);

        moveToValidation = false;
        sendCodeClick = false;

        invPhone = findViewById(R.id.textIncorrectPhone);

        sendCode = findViewById(R.id.btn_send_cord);

        sendCode.setOnClickListener(this);

        loadingBar = findViewById(R.id.loading_phone);

        invPhone.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            //Only sometimes happens
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                System.out.println("Phone verification complete");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                System.out.println("Phone verification failed");

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                }
            }


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                moveToValidation = true;
                validateID = verificationId;
                phoneResendToken = token;
                phoneNumber = phone.getText().toString();
                System.out.println("Phone code sent");
                System.out.println(phoneNumber);
                System.out.println(validateID);

                if(sendCodeClick){
                    controller.startPhoneNumberVerification(phoneNumber,validateID,phoneResendToken);
                }


            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_cord:

                sendCodeClick = true;

                phoneNumber = phone.getText().toString();
                System.out.println("Send code click");

                startPhoneNumberVerification("+1"+phoneNumber);

                //I dont think you can go this way
                if(moveToValidation){
                    controller.startPhoneNumberVerification("+1"+phoneNumber,validateID,phoneResendToken);
                }

                break;
        }

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }


    private boolean validateUSPhoneNumber() {
        String phoneNumber = phone.getText().toString();
        //TODO
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        else return phoneNumber.length() == 12;
    }

    private boolean testPhone(){
        String phoneNumber = phone.getText().toString();
        return phoneNumber.equals("13022990143");
    }



}