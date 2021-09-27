package com.petscare.org.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.petscare.org.Interfaces.onNextFragmentListener
import com.petscare.org.R
import com.petscare.org.databinding.ActAuthBinding
import com.petscare.org.fragments.authentication.FragmentAuthentication
import com.petscare.org.fragments.authentication.FragmentVerification

class ActivityAuth : AppCompatActivity(), onNextFragmentListener{

    private lateinit var binding : ActAuthBinding
    private lateinit var frag_authentication : Fragment
    private lateinit var frag_verification : Fragment
    private var frag_index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
        crearFragments()
        mostrarFragments()
        eventosUI()
    }

    private fun crearFragments() {
        frag_authentication = FragmentAuthentication()
        frag_verification = FragmentVerification()
    }

    private fun mostrarFragments() {
        supportFragmentManager.beginTransaction().add(R.id.contenedor_frags_auth,frag_authentication).commit()
    }

    private fun eventosUI() {
        binding.topAppBar.setNavigationOnClickListener {finish()}
    }

    override fun cambiarFragment(data: Bundle) {
        frag_verification.arguments = data
        supportFragmentManager.beginTransaction().replace(R.id.contenedor_frags_auth,frag_verification).commit()
        frag_index++
    }

    override fun onBackPressed() {
        if (frag_index == 0){
            finish()
        }
    }
}
