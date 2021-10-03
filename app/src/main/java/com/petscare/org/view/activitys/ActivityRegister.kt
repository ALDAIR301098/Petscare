package com.petscare.org.view.activitys


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.petscare.org.R
import com.petscare.org.databinding.ActRegisterBinding
import com.petscare.org.view.fragments.register.FragmentContrasena
import com.petscare.org.view.fragments.register.FragmentTerminar
import com.petscare.org.view.fragments.register.FragmentNombre
import com.petscare.org.view.fragments.register.FragmenteEdadGenero
import com.petscare.org.viewmodel.ViewModelRegistro

class ActivityRegister : AppCompatActivity() {

    private lateinit var binding: ActRegisterBinding
    private val vmRegistro : ViewModelRegistro by viewModels()

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
        mostrarFragment()
        eventosUI()
    }

    private fun crearFragments() {
        frag_index = vmRegistro.ldata_registro.value!!.frag_index
        frag_nombre = FragmentNombre()
        frag_edad_genero = FragmenteEdadGenero()
        frag_contrasena = FragmentContrasena()
        frag_terminar = FragmentTerminar()
    }

    private fun eventosUI() {
        binding.fabSiguiente.setOnClickListener {
            frag_index++
            mostrarFragment() }
    }

    private fun mostrarFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        Toast.makeText(this,"index: $frag_index",Toast.LENGTH_SHORT).show()
        when(frag_index){
            0 -> transaction.replace(R.id.contenedor_frags_registro,frag_nombre).commit()
            1 -> transaction.replace(R.id.contenedor_frags_registro,frag_edad_genero).commit()
            2 -> transaction.replace(R.id.contenedor_frags_registro,frag_contrasena).commit()
            3 -> transaction.replace(R.id.contenedor_frags_registro,frag_terminar).commit()
            4 -> {
                startActivity(Intent(this,ActivityMenu::class.java))
                finish()
            }
        }
    }

    override fun onPause() {
        vmRegistro.guardarDataRegistro(frag_index)
        super.onPause()
    }

    override fun onBackPressed() {
        frag_index--
        if (frag_index<0){
            Toast.makeText(this,"No puedes cancelar el registro de datos",Toast.LENGTH_SHORT).show()
        } else{
            mostrarFragment()
        }
    }
}