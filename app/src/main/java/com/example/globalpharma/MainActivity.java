package com.example.globalpharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override public void run() {
                Intent i = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(i);
                finish();
            }
        }, 5*1000);

    }
}
