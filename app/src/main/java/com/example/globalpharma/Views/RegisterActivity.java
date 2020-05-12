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

import com.example.globalpharma.Model.Provider;
import com.example.globalpharma.Model.User;
import com.example.globalpharma.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseFirestore mDatabase;
    private Provider mProvider;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initActivity();

        passToLoginInterface();

        registerAndStartApplication();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    //Initializing graphics elements
    private void initActivity() {
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

        mProvider = new Provider();

        mDatabase = FirebaseFirestore.getInstance();

        //Database initialization
        database = FirebaseDatabase.getInstance();

        mRandom = new Random();

        //initialisation de l'authentificateur
        mAuth = FirebaseAuth.getInstance();
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
                verifyFieldsState();

                setAuthWithEmail();

                emptyInputEditText();

                //setAuthWithPhoneNumber(mTxtPhone.getText().toString());
                //addUser(mTxtName.getText().toString(), mTxtPhone.getText().toString(), "Male", null, mTxtPassword.getText().toString() );
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
    private void emptyInputEditText() {
        mTxtPhone.setText(null);
        mTxtConfirm.setText(null);
        mTxtPassword.setText(null);
        mTxtName.setText(null);
    }

    //Add user to firebase by signing in
    private void setAuthWithEmail(){
        mAuth.createUserWithEmailAndPassword(mTxtPhone.getText().toString(), mTxtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()){
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                addUserToDatabase(new User(mTxtPhone.getText().toString(), mTxtPassword.getText().toString()));
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

    //Register with phone number
    private void setAuthWithPhoneNumber(PhoneAuthCredential authWithPhoneNumber){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegisterActivity.this, "Validé", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this, "Ndem", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(RegisterActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                mAuth.signInWithCredential(authWithPhoneNumber)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                            }
                        });
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mTxtPhone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private void sendCodeSms(String mPhoneNumber, int code){
        code = mRandom.nextInt((999999-100000)+1)+1000000;
        //PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.get
    }

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

    //Unsuscribe the application
    private void deleteAccount(FirebaseAuth mAuth, FirebaseUser user){
        user = mAuth.getCurrentUser();

    }

    private void addUserToDatabase(User userAdded){
        HashMap<String, Object> user = new HashMap<>();
        user.put("user_id", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        user.put("name", userAdded.getName());
        user.put("email", userAdded.getPhone());
        user.put("password", userAdded.getPassword());
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


