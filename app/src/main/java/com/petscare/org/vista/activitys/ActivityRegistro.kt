package com.petscare.org.vista.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.petscare.org.R
import com.petscare.org.databinding.ActivityRegistroBinding
import com.petscare.org.utilidades.KeyboardUtils
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import com.petscare.org.vista.fragments.registro.*

class ActivityRegistro : AppCompatActivity(), OnFragmentNavigationListener {

    private lateinit var binding: ActivityRegistroBinding
    private val vmRegistro: ViewModelRegistro by viewModels()

    private var index: Int = 0
    private lateinit var frag_nombre: FragmentNombre
    private lateinit var frag_edad_genero: FragmenteEdadGenero
    private lateinit var frag_pais_telefono: FragmentPaisTelefono
    private lateinit var frag_verificacion: FragmentVerificar
    private lateinit var frag_correo_Correo_contrasena: FragmentCorreoContrasena
    private lateinit var frag_terminar: FragmentTerminar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearFragments()
        mostrarFragment(index)
        observarTeclado()
    }

    private fun observarTeclado() {
        KeyboardUtils.addKeyboardToggleListener(this,
            object : KeyboardUtils.SoftKeyboardToggleListener {
                override fun onToggleSoftKeyboard(isVisible: Boolean) {
                    if (isVisible) {
                        binding.appBar.setExpanded(false, true)
                    } else {
                        binding.appBar.setExpanded(true, true)
                    }
                }
            })
    }

    private fun crearFragments() {
        this.index = vmRegistro.getIndex()!!
        frag_nombre = FragmentNombre()
        frag_edad_genero = FragmenteEdadGenero()
        frag_pais_telefono = FragmentPaisTelefono()
        frag_verificacion = FragmentVerificar()
        frag_correo_Correo_contrasena = FragmentCorreoContrasena()
        frag_terminar = FragmentTerminar()
    }

    override fun mostrarFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        this.index = index
        when (this.index) {
            0 -> transaction.replace(R.id.contenedor_frags_registro, frag_nombre).commit()
            1 -> transaction.replace(R.id.contenedor_frags_registro, frag_edad_genero).commit()
            2 -> transaction.replace(R.id.contenedor_frags_registro, frag_correo_Correo_contrasena)
                .commit()
            3 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_pais_telefono).commit()
                binding.txtInfo.text = "Información de la cuenta"
            }
            4 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_verificacion).commit()
                binding.txtInfo.text = "Verificación de la cuenta"
            }
            5 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_terminar).commit()
                binding.txtInfo.visibility = View.GONE
            }
            6 -> {
                startActivity(Intent(this, ActivityMenu::class.java))
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        vmRegistro.setIndex(this.index)
    }

    override fun onBackPressed() {
        this.index--
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out, R.anim.slide_in, R.anim.fade_out)
        when (this.index) {
            -1 -> finish()
            0 -> transaction.replace(R.id.contenedor_frags_registro, frag_nombre).commit()
            1 -> transaction.replace(R.id.contenedor_frags_registro, frag_edad_genero).commit()
            2 -> transaction.replace(R.id.contenedor_frags_registro, frag_correo_Correo_contrasena).commit()
            else -> index++
        }
    }
}