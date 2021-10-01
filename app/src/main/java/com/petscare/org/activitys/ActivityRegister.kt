package com.petscare.org.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.petscare.org.R
import com.petscare.org.databinding.ActRegisterBinding
import com.petscare.org.fragments.register.FragmentContrasena
import com.petscare.org.fragments.register.FragmentTerminar
import com.petscare.org.fragments.register.FragmentNombre
import com.petscare.org.fragments.register.FragmenteEdadGenero

class ActivityRegister : AppCompatActivity() {

    private lateinit var binding: ActRegisterBinding

    private lateinit var frag_nombre : FragmentNombre
    private lateinit var frag_edad_genero : FragmenteEdadGenero
    private lateinit var frag_contrasena : FragmentContrasena
    private lateinit var frag_terminar : FragmentTerminar
    private var frag_index : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)

        binding = ActRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearFragments()
        mostrarFragmentInicial()
        eventosUI()
    }

    private fun mostrarFragmentInicial() {
        supportFragmentManager.beginTransaction().add(R.id.contenedor_frags_registro,frag_nombre).commit()
    }

    private fun crearFragments() {
        frag_nombre = FragmentNombre()
        frag_edad_genero = FragmenteEdadGenero()
        frag_contrasena = FragmentContrasena()
        frag_terminar = FragmentTerminar()
    }

    private fun eventosUI() {
        binding.fabSiguiente.setOnClickListener { cambiarFragment() }
    }

    private fun cambiarFragment() {
        when(frag_index){
            0 -> administrarFragments(frag_nombre,frag_edad_genero)
            1 -> administrarFragments(frag_edad_genero,frag_contrasena)
            2 -> administrarFragments(frag_contrasena,frag_terminar)
            3 -> {
                startActivity(Intent(this,ActivityMenu::class.java))
                finish()
            }
        }
        frag_index++
    }

    private fun administrarFragments(frag_actual : Fragment, frag_siguiente : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (frag_siguiente.isAdded){
            transaction.hide(frag_actual)
                .show(frag_siguiente)
        } else{
            transaction.hide(frag_actual)
                .add(R.id.contenedor_frags_registro,frag_siguiente)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        when(frag_index){
            3 -> administrarFragments(frag_terminar,frag_contrasena)
            2 -> administrarFragments(frag_contrasena,frag_edad_genero)
            1 -> administrarFragments(frag_edad_genero,frag_nombre)
            0 -> Toast.makeText(this, "No puedes cancelar el proceso de registro",Toast.LENGTH_SHORT).show()
        }
        frag_index--
    }
}