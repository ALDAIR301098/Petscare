package com.petscare.org.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petscare.org.R
import com.petscare.org.databinding.ActAuthenticationBinding

class ActivityAuthentication : AppCompatActivity() {

    private lateinit var binding: ActAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)
        binding = ActAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventos()
    }

    private fun eventos() {
        binding.topAppBar.setNavigationOnClickListener({cerrarActivity()})
        binding.btnGenerar.setOnClickListener({verificarNumero()})
    }

    private fun verificarNumero() {
        binding.ctxTelefono.error = null
        val numero_tel = binding.ctxTelefono.editText?.text.toString()
        if (numero_tel.isNotEmpty()){
            if (numero_tel.length==10){
                mostrarActivity(numero_tel)
            } else{
                mostrarError("Número de teléfono no válido")
            }
        } else{
            mostrarError("Ingrese su número de teléfono.")
        }
    }

    private fun mostrarError(text : String) {
        binding.ctxTelefono.error = text
    }

    private fun mostrarActivity(data : String) {
        val intent = Intent(this, ActivityVerification::class.java)
        intent.putExtra("telefono",data)
        startActivity(intent)
        finish()
    }

    private fun cerrarActivity(){
        finish()
    }
}