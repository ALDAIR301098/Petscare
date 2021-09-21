package com.petscare.org.activitys

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.petscare.org.R
import com.petscare.org.databinding.ActVerificationBinding
import java.util.concurrent.TimeUnit

class ActivityVerification : AppCompatActivity() {

    private lateinit var binding: ActVerificationBinding
    private lateinit var telefono : String
    private lateinit var auth : FirebaseAuth
    private lateinit var mcallback : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        contadorTiempo()
        eventos()
        enviarCodigo()
    }

    private fun enviarCodigo() {
        val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+52$telefono")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mcallback)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun mostrarNumero() {

        telefono = intent.extras?.getString("telefono").toString()
        binding.txtNumero.text = "Ingresa el código enviado al +52 $telefono"
    }

    private fun eventos() {
        binding.btnVerificar.setOnClickListener { verificarCodigo(binding.ctxCodigo.editText?.text.toString()) }
        binding.topAppBar.setNavigationOnClickListener { finish() }

        mcallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(applicationContext, "Verificacion Exitosa",Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext,"Verificacion Fallida",Toast.LENGTH_SHORT).show()
            }

        }
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
