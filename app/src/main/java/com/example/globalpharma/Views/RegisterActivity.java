package com.example.globalpharma.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globalpharma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public class RegisterActivity extends AppCompatActivity {
    private Button btnconnect;
    private Button mBtnSubmit;
    private TextView mTxtPhone;
    private TextView mTxtPassword;
    private TextView mTxtConfirm;
    private TextView mTxtName;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initElements();

        //initialisation de l'authentificateur
        mAuth = FirebaseAuth.getInstance();

        //isLoggedIn();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFieldsState();
                setAuthWithEmail();
            }
        });


        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }

    private void initElements() {
        btnconnect = (Button) findViewById(R.id.btnConnect);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mTxtPhone = findViewById(R.id.txtEmail);
        mTxtPassword = findViewById(R.id.txtPassword);
        mTxtConfirm = findViewById(R.id.txtConfirmPassword);
        mTxtName = findViewById(R.id.txtName);
    }

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

    private void getAllFieldsText(){
        String name = mTxtName.getText().toString();
        String email = mTxtPhone.getText().toString();
        String password = mTxtPassword.getText().toString();
    }


    private void emptyInputEditText() {
        mTxtPhone.setText(null);
        mTxtConfirm.setText(null);
        mTxtPassword.setText(null);
        mTxtName.setText(null);
    }

    private void updateUI(FirebaseUser user) {
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn == true) {
            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
        }
    }

    //Add user to firebase
    private void setAuthWithEmail(){
        getAllFieldsText();
        mAuth.createUserWithEmailAndPassword(mTxtPhone.getText().toString(), mTxtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Log.d("Registration", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Verify is user is already logged in
    public void isLoggedIn(){
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already connected", Toast.LENGTH_SHORT).show();
            //return true;
        }
        else{
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            //return false;
        }
    }
}
