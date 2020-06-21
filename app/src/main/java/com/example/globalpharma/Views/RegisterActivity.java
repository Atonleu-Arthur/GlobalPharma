package com.example.globalpharma.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globalpharma.Model.Authentication;
import com.example.globalpharma.Model.Profile;
import com.example.globalpharma.Model.Provider;
import com.example.globalpharma.Model.User;
import com.example.globalpharma.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKeyFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button btnconnect;
    private Button mBtnSubmit;
    private TextView mTxtPhone;
    private TextView mTxtPassword;
    private TextView mTxtConfirm;
    private TextView mTxtName;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthCredential credential;
    private FirebaseUser currentUser ;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseFirestore mDatabase;
    private FirebaseUser mUser;
    private Provider mProvider;
    private Random mRandom;
    private String mVerificationid;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initView();

        initActivity();

        passToLoginInterface();

        registerAndStartApplication();

    }


    private void initActivity() {

        mProvider = new Provider();

        mDatabase = FirebaseFirestore.getInstance();

        //Database initialization
        database = FirebaseDatabase.getInstance();

        mRandom = new Random();

        //initialisation de l'authentificateur
        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
    }

    //Initializing graphics elements
    private void initView(){
        btnconnect = (Button) findViewById(R.id.btnConnect);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mTxtPhone = findViewById(R.id.txtEmail);
        mTxtPassword = findViewById(R.id.txtPassword);
        mTxtConfirm = findViewById(R.id.txtConfirmPassword);
        mTxtName = findViewById(R.id.txtName);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_register, new Fragment())
                .commit()
        ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.



    }

    public void passToLoginInterface(){
        //Pass to connexion interface
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void registerAndStartApplication(){
        //Click on register button
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAuthWithEmail();
                setAuthWithPhoneNumber();
            }
        });
    }

    //Verifying if fields are completely filled
    public void verifyFieldsState() {
        if (TextUtils.isEmpty(mTxtPhone.getText().toString()))
            mTxtPhone.setError(getString(R.string.error_txt));
        if(TextUtils.isEmpty(mTxtName.getText().toString()))
            mTxtName.setError(getString(R.string.error_txt));
        if(TextUtils.isEmpty(mTxtPassword.getText().toString()))
            mTxtPassword.setError(getString(R.string.error_txt));
        else if(mTxtPassword.getText().toString().length() < 8)
            mTxtPassword.setError(getString(R.string.error_password));
        if(TextUtils.isEmpty(mTxtConfirm.getText().toString()))
            mTxtConfirm.setError(getString(R.string.error_txt));
        else if (!mTxtPassword.getText().toString().equals(mTxtConfirm.getText().toString()))
            mTxtConfirm.setError(getString(R.string.error_txt));
    }

    //Clear all the fields
    private void clearView() {
        mTxtPhone.setText(null);
        mTxtConfirm.setText(null);
        mTxtPassword.setText(null);
        mTxtName.setText(null);
    }

    //Sign up user by email
    private void setAuthWithEmail(){
        mAuth.createUserWithEmailAndPassword(mTxtPhone.getText().toString(), mTxtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(user.isEmailVerified()){
                               //Profile profile = new Profile(mTxtPhone.getText().toString(), mTxtPassword.getText().toString());
                                Toast.makeText(RegisterActivity.this, "Ok", Toast.LENGTH_LONG).show();
                                /*if(verifyEmailAddress() == true){
                                    profile.saveUserToFirestore(profile);
                                    addUserToDatabase(new User(mTxtPhone.getText().toString(), mTxtPassword.getText().toString()));
                                }*/
                                sendEmailLink();
                            }

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailLink(){
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.example.globalpharma",
                                true,
                                "12"    )
                        .build();

        mAuth.sendSignInLinkToEmail(mTxtPhone.getText().toString(), actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Sent email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    boolean result = false;
    private boolean verifyEmailAddress(){
        mUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Mail sent", Toast.LENGTH_SHORT).show();
                            result = true;
                        }
                        else
                            Toast.makeText(RegisterActivity.this, "Mail not sent", Toast.LENGTH_SHORT).show();
                    }
                });
        return result;
    }


    //          --- PHONE NUMBER ---

    //Register with phone number
    private void setAuthWithPhoneNumber(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                registrationWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("Error ", "Error: " + e.toString());
                Toast.makeText(RegisterActivity.this, "Ndem", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
                Log.d("Code ", "Code: " + s);
                mResendToken = forceResendingToken;
                mVerificationid = s;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mTxtPhone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private void registrationWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, Accueil.class));
                            // ...
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }
                    }
                });
    }

    private void sendCodeSms(String mPhoneNumber, int code){
    }

    //          --- MANAGE USERS ---

    //Verify is user is already logged in
    public boolean isLoggedIn(){
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already connected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //Log out the current account
    private void logOut(FirebaseAuth mAuth){
        if(mAuth.getCurrentUser() != null)
            mAuth.signOut();
    }

    private void addUserToDatabase(User userAdded){
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        mDatabase.collection("user")
                .add(user)
                .isSuccessful()
                ;
    }

    public void hashPasswordToMD5(String password){
        SecretKeyFactory secretKeyFactory;
        MessageDigest hash = null;
        byte[] byteChaine = password.getBytes(StandardCharsets.UTF_8);
        try {
            hash = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        hash.update(byteChaine);
    }
}


