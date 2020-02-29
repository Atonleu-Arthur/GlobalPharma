package com.example.globalpharma.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.globalpharma.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override public void run() {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
            }, 5*1000);
    }
}
