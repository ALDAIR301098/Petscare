package com.petscare.org.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.petscare.org.databinding.ActWelcomeBinding

class ActivityWelcome : AppCompatActivity() {

    private lateinit var binding : ActWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retardarSplashScreen()
        EventosUI()
    }

    private fun retardarSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            Thread.sleep(1500)
            true
        }
    }

    private fun EventosUI(){
        binding.btnIngresar.setOnClickListener {mostrarActivity(Intent(this, ActivityAuthentication::class.java))}
    }

    private fun mostrarActivity(intent: Intent){
        startActivity(intent)
    }
}