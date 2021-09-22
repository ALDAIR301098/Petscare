package com.petscare.org.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R
import com.petscare.org.databinding.ActAuthBinding

class ActivityAuth : AppCompatActivity() {

    private lateinit var binding: ActAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventosUI()
    }

    private fun eventosUI() {
        binding.topAppBar.setNavigationOnClickListener {finish()}
    }
}