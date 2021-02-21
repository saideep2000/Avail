package com.example.avail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Afterlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        setContentView(R.layout.activity_afterlogin);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlis);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlis = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.recordvoice:
                    selectedFragment = new recordvoice();
                    break;
                case R.id.textvoice:
                    selectedFragment = new textvoice();
                    break;
                case R.id.camera:
                    selectedFragment = new camera();
                    break;
//                case R.id.fav:
//                    selectedFragment = new fav();
//                    break;
                case R.id.account:
                    selectedFragment = new account();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };
}
