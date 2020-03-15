package com.example.globalpharma.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.globalpharma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnRegisterActivity;
    private Button btnLogIn;
    private EditText password_editText;
    private EditText phone_editText;
    private FirebaseAuth mLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        iniElements();

        mLog = FirebaseAuth.getInstance();

        final String identifier = phone_editText.getText().toString();
        final String password = password_editText.getText().toString();

        btnRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
               startActivity(i);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInWithEmail();
            }
        });

    }

    private void iniElements(){
        password_editText = findViewById(R.id.txtPasswordLogin);
        phone_editText = findViewById(R.id.txtNameLogin);
        btnRegisterActivity =(Button) findViewById(R.id.btnRegistration);
        btnLogIn =(Button) findViewById(R.id.btnSubmit2);
    }

    private void logInWithEmail(){
        mLog.signInWithEmailAndPassword(phone_editText.getText().toString(), password_editText.getText().toString()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}

