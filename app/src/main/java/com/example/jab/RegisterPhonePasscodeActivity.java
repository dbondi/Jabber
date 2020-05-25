package com.example.jab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import controllers.RegisterPhonePasscodeController;
import controllers.RegisterWithPhoneController;
import custom_class.User;
import custom_class.Users;
import models.RegisterPhonePasscodeModel;
import models.RegisterWithPhoneModel;

public class RegisterPhonePasscodeActivity extends Activity implements View.OnClickListener{

    private RegisterPhonePasscodeController controller;
    private RegisterPhonePasscodeModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String TAG = "RegisterPhonePasscodeActivity";

    private String phoneNumber;

    //user info
    private User user;
    private String UID;

    private String typedCode;
    private String validationID;

    private EditText code_1;
    private EditText code_2;
    private EditText code_3;
    private EditText code_4;
    private EditText code_5;
    private EditText code_6;

    private TextView invCode;

    private Boolean verified;


    private Button verify;

    private ProgressBar loadingBar;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone_passcode);

        invCode = findViewById(R.id.incorrect_code);
        loadingBar = findViewById(R.id.loading_phone);

        validationID = getIntent().getExtras().getString("ValidationID");

        verified = false;
        user = new User();

        invCode.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);

        verify = findViewById(R.id.btn_verify);
        verify.setOnClickListener(this);

        code_1 = findViewById(R.id.code_1);
        code_2 = findViewById(R.id.code_2);
        code_3 = findViewById(R.id.code_3);
        code_4 = findViewById(R.id.code_4);
        code_5 = findViewById(R.id.code_5);
        code_6 = findViewById(R.id.code_6);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new RegisterPhonePasscodeController(this,auth);
        model = new RegisterPhonePasscodeModel(db, auth);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            //Only sometimes happens
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);

                mVerificationInProgress = false;

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);

                mVerificationInProgress = false;

                invCode.setVisibility(View.VISIBLE);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                //TODO phone should have already been sent use for resend
            }
        };

    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser userFire = task.getResult().getUser();
                            UID = userFire.getUid();
                            System.out.println("UID");
                            System.out.println(UID);
                            user.setUID(UID);
                            user.setPhone_number(phoneNumber);
                            controller.continueToApp(user);
                            //TODO
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                invCode.setVisibility(View.VISIBLE);
                            }
                            //TODO
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                typedCode = code_1.getText().toString() + code_2.getText().toString() + code_3.getText().toString() + code_4.getText().toString() + code_5.getText().toString() + code_6.getText().toString();
                System.out.println(typedCode);
                if (TextUtils.isEmpty(typedCode)) {
                    invCode.setVisibility(View.VISIBLE);
                    return;
                }
                System.out.println(validationID);

                verifyPhoneNumberWithCode(validationID, typedCode);

                //TODO Im not gonna touch because i dont think it executes
                if(verified){

                    controller.continueToApp(user);
                }

                break;
        }

    }


    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        loadingBar.setVisibility(View.VISIBLE);
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
        loadingBar.setVisibility(View.INVISIBLE);
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


}
