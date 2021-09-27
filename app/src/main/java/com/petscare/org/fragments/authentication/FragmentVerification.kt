package com.petscare.org.fragments.authentication

import android.content.res.ColorStateList.*
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.petscare.org.R
import com.petscare.org.databinding.FragVerificationBinding
import java.util.concurrent.TimeUnit

class FragmentVerification : Fragment() {

    private var _binding: FragVerificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth
    private lateinit var mcallback : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var telefono : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telefono = arguments?.getString("telefono")!!
        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        contadorTiempo()
        //listeners()
        //enviarCodigo()
        eventosUI()

    }

    private fun listeners() {
        mcallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(activity,"Verificacion Exitosa", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity,"Verificacion Fallida", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun mostrarNumero() {
        binding.txtNumero.text = "Ingresa el código enviado al +52 $telefono"
    }

    private fun enviarCodigo() {
        val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+52$telefono")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mcallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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

    private fun eventosUI() {

    }

    private fun habilitarBtnReenviar() {

        binding.btnReenviar.isEnabled = true
        binding.btnReenviar.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.fondo_btn_google_fb)
        binding.btnReenviar.setTextColor(ContextCompat.getColor(requireContext(), R.color.blanco))
        binding.btnReenviar.compoundDrawableTintList = valueOf(ContextCompat.getColor(requireContext(), R.color.blanco))


    }
}