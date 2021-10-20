package com.petscare.org.vista.activitys

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R
import com.petscare.org.databinding.ActivityAgregarMascotaBinding

class ActivityAgregarMascota : AppCompatActivity() {

    private lateinit var binding : ActivityAgregarMascotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventosUI()
    }

    private fun eventosUI() {
        binding.btnGuardar.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}