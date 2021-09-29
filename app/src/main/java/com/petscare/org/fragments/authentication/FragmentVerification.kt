package com.petscare.org.fragments.authentication

import android.content.Intent
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
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.petscare.org.R
import com.petscare.org.activitys.ActivityMenu
import com.petscare.org.databinding.FragVerificationBinding
import java.util.concurrent.TimeUnit

class FragmentVerification : Fragment() {

    private var _binding: FragVerificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var telefono: String
    private lateinit var contador: CountDownTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telefono = arguments?.getString("telefono")!!
        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        getCodeListener()
        enviarCodigo()
        contadorTiempo()
        eventosUI()

    }

    private fun getCodeListener() {
        mcallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                binding.ctxCodigo.error = null
                binding.ctxCodigo.editText?.setText(credential.smsCode)
                Toast.makeText(activity, "Verificacion exitosa", Toast.LENGTH_SHORT).show()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(firebaseException: FirebaseException) {
                if (firebaseException is FirebaseAuthInvalidCredentialsException) {
                    binding.ctxCodigo.error = "Código incorrecto"
                } else if (firebaseException is FirebaseNetworkException) {
                    Toast.makeText(activity, "No hay conexión a internet", Toast.LENGTH_SHORT)
                        .show()
                } else if (firebaseException is FirebaseAuthUserCollisionException) {
                    Toast.makeText(
                        activity,
                        "El número de teléfono ingresado, se encuentra asociado a otra cuenta ya existente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Toast.makeText(activity, "Se envió el código de verificación", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    cambiarActivity(Intent(requireActivity(), ActivityMenu::class.java))
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(activity,"Error de autenticación: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun cambiarActivity(intent : Intent) {
        startActivity(intent)
        activity?.finish()
    }

    private fun mostrarNumero() {
        binding.txtNumero.text = "Ingresa el código enviado al +52 $telefono"
    }

    private fun enviarCodigo() {
        auth.useAppLanguage()
        val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+52$telefono")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mcallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun contadorTiempo() {
            contador = object : CountDownTimer(15000, 1000) {
            override fun onTick(p0: Long) {
                binding.txtTiempo.text = "Solicitar nuevo mensaje en ${p0 / 1000} segundos"
            }
            override fun onFinish() {
                habilitarBtnReenviar()
            }
        }.start()
    }

    private fun eventosUI() {
        binding.btnVerificar.setOnClickListener {
            startActivity(Intent(requireActivity(),ActivityMenu::class.java))
            contador.cancel()
            activity?.finish()
        }
    }

    private fun habilitarBtnReenviar() {

        binding.btnReenviar.isEnabled = true
        binding.btnReenviar.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.boton_reenviar_corregir)
        binding.btnReenviar.setTextColor(ContextCompat.getColor(requireContext(), R.color.blanco))
        binding.btnReenviar.compoundDrawableTintList =
            valueOf(ContextCompat.getColor(requireContext(), R.color.blanco))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}