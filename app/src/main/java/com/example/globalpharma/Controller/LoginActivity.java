package com.example.globalpharma.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.globalpharma.R;

public class LoginActivity extends AppCompatActivity {


    private Button mBtnRegistration;
    private Button mBtnRegister;
    private Button mBtnConnection;
    private Button mBtnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnRegistration = findViewById(R.id.btnRegistration);

        mBtnConnect = findViewById(R.id.btnSubmit);
        mBtnConnect.setEnabled(false);
        mBtnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
