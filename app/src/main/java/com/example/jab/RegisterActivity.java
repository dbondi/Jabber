package com.example.jab;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import custom_class.NoPhoneUser;
import models.RegisterModel;
import controllers.RegisterController;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterController controller;
    private RegisterModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private final String TAG = "RegisterActivity";

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    private Button registerBtn;

    private TextView passMatch;
    private TextView invPass;
    private TextView invEmail;

    private ProgressBar loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        model = new RegisterModel(db, auth);
        controller = new RegisterController(this,auth);

        firstName = findViewById(R.id.first_name_register);
        lastName = findViewById(R.id.last_name_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_password_register);
        registerBtn = findViewById(R.id.btn_register);

        passMatch = findViewById(R.id.invalidPasswordMatch);
        invEmail = findViewById(R.id.textIncorrectEmail);
        invPass = findViewById(R.id.password_invalid);

        loadingBar = findViewById(R.id.loading_register);

        passMatch.setVisibility(View.INVISIBLE);
        invEmail.setVisibility(View.INVISIBLE);
        invPass.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);

        registerBtn.setOnClickListener(this);
        registerBtn.setEnabled(false);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    //TODO check if email is valid
                    String emailString = email.getText().toString();
                    if (emailString.length() < 8) {
                        invEmail.setVisibility(View.VISIBLE);
                    } else {
                        invEmail.setVisibility(View.INVISIBLE);
                        registerBtn.setEnabled(
                                verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                        emailString, password.getText().toString(), confirmPassword.getText().toString())
                        );
                    }

                    //TODO for all
                }
        }});



        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String pass = password.getText().toString();

                    if (pass.length() < 8) {
                        invPass.setVisibility(View.VISIBLE);
                    } else {
                        invPass.setVisibility(View.INVISIBLE);
                    }

                    registerBtn.setEnabled(
                            verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                    email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())
                    );


                    CharSequence cPassword = confirmPassword.getText();
                    // update if confirm password isnt the same and not null
                    if (!cPassword.toString().equals(pass)) {
                        if(!cPassword.toString().equals("")) {
                            passMatch.setVisibility(View.VISIBLE);
                        }
                    } else {
                        passMatch.setVisibility(View.INVISIBLE);
                        registerBtn.setEnabled(
                                verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                        email.getText().toString(), password.getText().toString(), pass)
                        );
                    }

                }
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 8) {

                } else {
                    invPass.setVisibility(View.INVISIBLE);
                    registerBtn.setEnabled(
                            verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                    email.getText().toString(), s.toString(), confirmPassword.getText().toString())
                    );
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Confirm password
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String confirmPass = confirmPassword.getText().toString();
                    if(!confirmPass.equals("")) {
                        if (!confirmPass.equals(password.getText().toString())) {
                            passMatch.setVisibility(View.VISIBLE);
                        } else {
                            passMatch.setVisibility(View.INVISIBLE);
                        }

                        registerBtn.setEnabled(
                                verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                        email.getText().toString(), password.getText().toString(), confirmPass)
                        );
                    }
                }
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(password.getText().toString())) {

                } else {
                    passMatch.setVisibility(View.INVISIBLE);
                    registerBtn.setEnabled(
                            verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                    email.getText().toString(), password.getText().toString(), s.toString())
                    );
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            loadingBar.setVisibility(View.VISIBLE);
            final String uFirst = firstName.getText().toString();
            final String uLast = lastName.getText().toString();
            final String uEmail = email.getText().toString();
            final String uPass = password.getText().toString();

            auth.setLanguageCode("it");

            NoPhoneUser noPhoneUser = new NoPhoneUser(uFirst,uLast,uEmail,uPass);
            controller.askUserForPhone(noPhoneUser);
            loadingBar.setVisibility(View.INVISIBLE);
        }

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
                            // [START_EXCLUDE]

                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //TODO
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                        }
                    }
                });
    }
    // [END sign_in_with_phone]
    public boolean verifyInput(String firstName, String lastName, String email, String password, String confirmPass) {
        boolean everythingFilled = (firstName.length() > 0 && lastName.length() > 0 &&
                email.length() > 0 && password.length() > 0 && confirmPass.length() > 0);
        boolean passwordReqs = (password.length() >= 8 && password.equals(confirmPass));
        return everythingFilled && passwordReqs;
    }



}