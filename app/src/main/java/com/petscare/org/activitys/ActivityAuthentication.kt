package com.petscare.org.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.petscare.org.Interfaces.onNextFragmentListener
import com.petscare.org.R
import com.petscare.org.databinding.ActAuthenticationBinding
import com.petscare.org.fragments.authentication.FragmentAuthentication
import com.petscare.org.fragments.authentication.FragmentVerification

class ActivityAuthentication : AppCompatActivity(), onNextFragmentListener{

    private lateinit var binding : ActAuthenticationBinding
    private lateinit var frag_authentication : Fragment
    private lateinit var frag_verification : Fragment
    private var frag_index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActAuthenticationBinding.inflate(layoutInflater)

        setContentView(binding.root)
        crearFragments()
        mostrarFragment()
        eventosUI()
    }

    private fun crearFragments() {
        frag_authentication = FragmentAuthentication()
        frag_verification = FragmentVerification()
    }

    private fun mostrarFragment() {
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
