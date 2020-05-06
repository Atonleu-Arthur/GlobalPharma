package com.example.globalpharma.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.globalpharma.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Button btnregist;
        Button btnconnection;
        btnconnection=(Button) findViewById(R.id.btnconnection);
        btnregist=(Button) findViewById(R.id.btnRegistration);
        btnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
      btnconnection.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(LoginActivity.this, Accueil.class);
        startActivity(i);
    }
});
    }
}

