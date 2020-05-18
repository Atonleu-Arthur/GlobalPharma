package com.example.globalpharma.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.globalpharma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnRegisterActivity;
    private Button btnLoginActivity;
    private Button btnLogIn;
    private EditText password_editText;
    private EditText phone_editText;
    private FirebaseAuth mLog;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private int result = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        iniElements();

        passToRegisterActivity();

        logAndStartApplication();


    }

    private void iniElements(){
        password_editText = findViewById(R.id.txtPasswordLogin);
        phone_editText = findViewById(R.id.txtNameLogin);
        btnRegisterActivity =(Button) findViewById(R.id.btnRegistration);
        btnLogIn =(Button) findViewById(R.id.btnSubmit2);
        btnLoginActivity = findViewById(R.id.btnConnect);
       /* mLog = FirebaseAuth.getInstance();*/

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_login, new android.app.Fragment())
                .commit()
        ;

       /* btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_register, new Fragment())
                .commit()
                ;
            }
        });*/
    }

    private void passToRegisterActivity() {
        btnRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean logInWithEmail(){
        mLog.signInWithEmailAndPassword(phone_editText.getText().toString(), password_editText.getText().toString()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            result = 0;
                        }
                    }
                }
        );

        if(result == 0) return false;
        else return true;
    }

    private void logAndStartApplication() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean p = logInWithEmail();
                if(p == true){
                    Intent intent = new Intent(LoginActivity.this, Accueil.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}