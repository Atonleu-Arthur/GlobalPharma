package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.globalpharma.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override public void run() {
                Intent i = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(i);
                finish();
            }
        }, 5*1000);

    }
}
