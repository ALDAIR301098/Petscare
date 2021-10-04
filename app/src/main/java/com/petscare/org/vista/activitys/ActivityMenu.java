package com.petscare.org.vista.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.petscare.org.R;

public class ActivityMenu extends AppCompatActivity {
/*
    FragmentFeed firstFragment = new FragmentFeed();
    FragmentMascotas secondFragment = new FragmentMascotas();
    FragmentServicios thirdFragment = new FragmentServicios();
    FragmentPerfil fourFragment = new FragmentPerfil();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.THEME_GLOBAL_APP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

       /* BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); */
    }

   /* private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
    } */

}