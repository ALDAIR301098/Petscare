package com.petscare.org.vista.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R
import com.petscare.org.databinding.ActivityAgregarDispositivoBinding

class ActivityAgregarDispositivo : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarDispositivoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarDispositivoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){

    }
}