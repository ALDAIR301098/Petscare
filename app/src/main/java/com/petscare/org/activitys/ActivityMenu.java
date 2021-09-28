package com.petscare.org.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.petscare.org.R;

public class ActivityMenu extends AppCompatActivity {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    FourFragment fourFragment = new FourFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.THEME_GLOBAL_APP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.firstFragment:
                loadFragment(firstFragment);
                return true;

                case R.id.secondFragment:
                    loadFragment(secondFragment);
                    return true;

                case R.id.thirdFragment:
                    loadFragment(thirdFragment);
                    return true;

                case R.id.fourFragment:
                    loadFragment(fourFragment);
                    return true;


            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}