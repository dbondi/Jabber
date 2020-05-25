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

import java.util.concurrent.TimeUnit;

import controllers.RegisterPhoneController;
import custom_class.NoPhoneUser;
import custom_class.Users;
import models.RegisterPhoneModel;

public class RegisterPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterPhoneController controller;
    private RegisterPhoneModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private final String TAG = "RegisterPhoneActivity";

    private EditText phone;
    private EditText code;
    private NoPhoneUser noPhoneUser;

    private String phoneNumber;

    private TextView invPhone;
    private TextView invCode;

    private Button sendCode;
    private Button verify;
    private boolean test = false;

    private ProgressBar loadingBar;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new RegisterPhoneController(this,auth);
        model = new RegisterPhoneModel(db, auth);

        phone = findViewById(R.id.phone_register);
        code = findViewById(R.id.code_verify);

        invPhone = findViewById(R.id.textIncorrectPhone);
        invCode = findViewById(R.id.incorrect_code);

        sendCode = findViewById(R.id.btn_send_cord);
        verify = findViewById(R.id.btn_verify);
        sendCode.setOnClickListener(this);
        verify.setOnClickListener(this);

        loadingBar = findViewById(R.id.loading_phone);

        invPhone.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);
        invCode.setVisibility(View.INVISIBLE);


        noPhoneUser = getIntent().getParcelableExtra("NoPhoneUser");



        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //TODO check if phone is valid
                verify.setEnabled(
                        validateUSPhoneNumber()
                );
            }
        });



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            //Only sometimes happens
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                invPhone.setVisibility(View.INVISIBLE);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                //TODO
                //[END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    invPhone.setVisibility(View.VISIBLE);
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //[END_EXCLUDE]
            }


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                invPhone.setVisibility(View.INVISIBLE);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                //TODO
                //[END_EXCLUDE]
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_cord:
                //if (!validateUSPhoneNumber()) {
                //    return;
                //}
                phoneNumber = phone.getText().toString();
                startPhoneNumberVerification(phoneNumber);

                break;
            case R.id.btn_verify:
                String codeVal = code.getText().toString();

                if (TextUtils.isEmpty(codeVal)) {
                    invPhone.setVisibility(View.VISIBLE);
                    return;
                }
                System.out.println("VerificationID");
                System.out.println(mVerificationId);
                System.out.println(code);
                verifyPhoneNumberWithCode(mVerificationId, codeVal);

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


    private void addUser(Users user) {
        model.publishUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "An error occurred: " + message, Toast.LENGTH_LONG).show();
                }
            }
        });
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
                            FirebaseUser user = task.getResult().getUser();
                            String uid = user.getUid();
                            Users uUser = new Users(noPhoneUser,phoneNumber,uid);
                //TODO just controller.clickVerify(uUser);
                            //TODO
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                invCode.setVisibility(View.VISIBLE);
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //TODO
                            //[END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]


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