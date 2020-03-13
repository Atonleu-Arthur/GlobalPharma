package com.example.globalpharma.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.globalpharma.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Accueil extends  AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ClipData.Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        getSupportActionBar().hide();


        defaultFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:

                fragment = new HomeFragment();

                break;

            case R.id.pills:
                fragment = new PillsFragment();

                break;

            case R.id.gps:
                fragment = new GpsFragment();

                break;

            case R.id.user:
                fragment = new ProfilFragment();
                break;

            case R.id.Tchat:
                fragment = new ProfilFragment();
                break;
        }

        return defaultFragment (fragment);
    }

    private boolean defaultFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
