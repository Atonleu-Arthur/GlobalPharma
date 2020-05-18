package com.example.globalpharma.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.globalpharma.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

       /* mAuth = FirebaseAuth.getInstance();*/

     /*   isLoggedIn();*/

        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(i);
                finish();
            }
        }, 3 * 1000);

    }

    /*public void isLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Already connected", Toast.LENGTH_SHORT).show();
            //return true;
        } else {
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            //return false;

        }
    }*/
}