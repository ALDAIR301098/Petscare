package com.petscare.org.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_register)
    }
}