package com.petscare.org.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R
import com.petscare.org.databinding.ActWelcomeBinding

class ActivityWelcome : AppCompatActivity() {

    private lateinit var binding : ActWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //Splashscreen config
        Thread.sleep(1500)
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)
        binding = ActWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventos()
    }

    private fun eventos(){
        binding.btnIngresar.setOnClickListener({mostrarActivity(Intent(this, ActivityAuthentication::class.java))})
    }

    private fun mostrarActivity(intent: Intent){
        startActivity(intent)
    }
}