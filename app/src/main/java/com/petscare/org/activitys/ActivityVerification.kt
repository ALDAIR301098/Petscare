package com.petscare.org.activitys

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import com.petscare.org.R
import com.petscare.org.databinding.ActVerificationBinding

class ActivityVerification : AppCompatActivity() {

    private lateinit var binding: ActVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)
        binding = ActVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contadorTiempo()
        eventos()
    }

    private fun eventos() {
        binding.btnVerificar.setOnClickListener { verificarCodigo(binding.ctxCodigo.editText?.text.toString()) }
        binding.topAppBar.setNavigationOnClickListener { finish() }
    }

    private fun verificarCodigo(codigo: String) {
        if (codigo.isNotEmpty()) {
            if (codigo.length == 6) {
                mostrarActivityInicio()
            } else {
                binding.ctxCodigo.error = "El código debe tener 6 dígitos"
            }
        } else {
            binding.ctxCodigo.error = "Ingrese el código"
        }
    }

    private fun contadorTiempo() {
        object : CountDownTimer(15000, 1000) {
            override fun onTick(p0: Long) {
                binding.txtTiempo.text = "Solicitar nuevo mensaje en ${p0 / 1000} segundos"
            }

            override fun onFinish() {
                habilitarBtnReenviar()
            }
        }.start()
    }

    private fun habilitarBtnReenviar() {
        binding.btnReenviar.isEnabled = true
        binding.btnReenviar.backgroundTintList = ContextCompat.getColorStateList(this, R.color.cafe)
        binding.btnReenviar.setTextColor(ContextCompat.getColor(this, R.color.blanco))
        binding.btnReenviar.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blanco))
    }

    private fun mostrarActivityInicio() {
        startActivity(Intent(this, ActivityInicio::class.java))
        finish()
    }

    override fun onBackPressed() {}
}
