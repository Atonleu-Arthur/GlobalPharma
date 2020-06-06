package com.example.globalpharma.Model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.globalpharma.Views.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Authentication {
    public   String codeSent;
    private  FirebaseAuth auth = FirebaseAuth.getInstance();
    private  FirebaseUser currentUser = auth.getCurrentUser();

    @Nullable
    private String email;

    private String password;
    @Nullable
    private String phone;

    private Activity activity;

    public Authentication(String email, String password, Activity activity) {
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    public Authentication(){}

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };

    public void registerWithEmail(){
       /* auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(Authentication.class.getSimpleName(), "createUserWithEmail:success");
                        }
                        else {
                            Log.w(Authentication.class.getSimpleName(), "createUserWithEmail:failure", task.getException());
                        }
                    }
                });*/
    }

    public void registerWithPhoneNumber(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(activity.getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                            //verifyCodeSent("1234");
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                Toast.makeText(activity.getApplicationContext(), "Not done", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void sendOtpCode(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                activity,
                callbacks
        );
    }

    public void verifyCodeSent(String code){
        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(codeSent, code);
        sendOtpCode(phone);
    }

    public void logIn(){

    }

    public void logOut(){
        if (currentUser != null) {
            auth.signOut();
        }
    }




}
