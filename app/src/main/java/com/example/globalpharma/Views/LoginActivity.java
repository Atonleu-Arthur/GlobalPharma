package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.globalpharma.Model.DatabaseHelper;
import com.example.globalpharma.R;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Button btnRegisterActivity;
    private Button btnLogIn;
    private EditText password_editText;
    private EditText phone_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        iniElements();
        databaseHelper = new DatabaseHelper(this);
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
                if(!(RegisterActivity.databaseHelper.searchPassword(identifier) == null)){
                    Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void iniElements(){
        password_editText = findViewById(R.id.txtPasswordLogin);
        phone_editText = findViewById(R.id.txtNameLogin);
        btnRegisterActivity =(Button) findViewById(R.id.btnRegistration);
        btnLogIn =(Button) findViewById(R.id.btnSubmit2);
    }
}

