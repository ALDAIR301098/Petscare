package com.petscare.org.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.petscare.org.R;

public class ActivityMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.THEME_GLOBAL_APP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu);
    }
}