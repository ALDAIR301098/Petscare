package com.petscare.org.vista.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.petscare.org.R
import com.petscare.org.databinding.ActivityRegistroBinding
import com.petscare.org.vista.fragments.registro.FragmentContrasena
import com.petscare.org.vista.fragments.registro.FragmentTerminar
import com.petscare.org.vista.fragments.registro.FragmentNombre
import com.petscare.org.vista.fragments.registro.FragmenteEdadGenero
import com.petscare.org.viewmodel.ViewModelRegistro

class ActivityRegistro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val vmRegistro : ViewModelRegistro by viewModels()

    private lateinit var frag_nombre : FragmentNombre
    private lateinit var frag_edad_genero : FragmenteEdadGenero
    private lateinit var frag_contrasena : FragmentContrasena
    private lateinit var frag_terminar : FragmentTerminar
    private var frag_index : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
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
                mostrarFragment()

        }
    }

    private fun mostrarFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        when(frag_index){
            -1 -> {
                Toast.makeText(this,"No puedes cancelar el registro de datos",Toast.LENGTH_SHORT).show()
                frag_index++
            }
            0 -> transaction.replace(R.id.contenedor_frags_registro,frag_nombre).commit()
            1 -> {
                if (vmRegistro.ldata_registro.value?.frag_nombre_verificado == true){
                    transaction.replace(R.id.contenedor_frags_registro,frag_edad_genero).commit()
                    binding.fabSiguiente.text = "Siguiente"
                } else{
                    if (frag_nombre.verificarCampos()){
                        transaction.replace(R.id.contenedor_frags_registro,frag_edad_genero).commit() //Aqui estoy y el frag verification = true
                        binding.fabSiguiente.text = "Siguiente"
                    } else{
                        frag_index--
                    }
                }
            }
            2 -> {
                if(vmRegistro.ldata_registro.value?.frag_edad_genero_verificado == true){
                    transaction.replace(R.id.contenedor_frags_registro,frag_contrasena).commit()
                    binding.fabSiguiente.text = "Terminar"
                } else{
                    if (frag_edad_genero.verificarCampos()){
                        transaction.replace(R.id.contenedor_frags_registro,frag_contrasena).commit()
                        binding.fabSiguiente.text = "Terminar"
                    } else{
                        frag_index--
                    }
                }
            }
            3 -> {
                if (vmRegistro.ldata_registro.value?.frag_contrasena_verificado == true){
                    transaction.replace(R.id.contenedor_frags_registro,frag_terminar).commit()
                    binding.fabSiguiente.text = "Ingresar"
                } else{
                    if (frag_contrasena.verificarCampos()){
                        transaction.replace(R.id.contenedor_frags_registro,frag_terminar).commit()
                        binding.fabSiguiente.text = "Ingresar"
                    } else{
                        frag_index--
                    }
                }
            }
            4 -> {
                startActivity(Intent(this,ActivityMenu::class.java))
                finish()
            }
        }
    }

    override fun onPause() {
        vmRegistro.establecerIndex(frag_index)
        super.onPause()
    }

    override fun onBackPressed() {
        frag_index--
        mostrarFragment()
    }
}