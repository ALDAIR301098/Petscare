package com.petscare.org.vista.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.petscare.org.vista.Interfaces.onNextFragmentListener
import com.petscare.org.R
import com.petscare.org.databinding.ActivityAuthenticationBinding
import com.petscare.org.vista.fragments.authentication.FragmentAuthentication
import com.petscare.org.vista.fragments.authentication.FragmentVerification

class ActivityAuthentication : AppCompatActivity(), onNextFragmentListener{

    private lateinit var binding : ActivityAuthenticationBinding
    private lateinit var frag_authentication : Fragment
    private lateinit var frag_verification : Fragment
    private var frag_index : Int = 0
    private lateinit var auth_mode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        auth_mode = intent.extras?.getString("auth_mode").toString()

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
        frag_index = data.getInt("index")
        if (frag_index == 0){
            frag_verification.arguments = data
            supportFragmentManager.beginTransaction().replace(R.id.contenedor_frags_auth,frag_verification).commit()
            frag_index++
        } else if (frag_index == 1 && auth_mode == "login"){
            cambiarActivity(Intent(this,ActivityMenu::class.java))
        } else if(frag_index == 1 && auth_mode == "register"){
            cambiarActivity(Intent(this,ActivityRegistro::class.java))
        }
    }

    private fun cambiarActivity(intent : Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (frag_index == 0){
            finish()
        }
    }
}
