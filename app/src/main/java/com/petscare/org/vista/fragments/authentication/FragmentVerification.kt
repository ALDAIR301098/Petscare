package com.petscare.org.vista.fragments.authentication

import android.content.Context
import android.content.res.ColorStateList.*
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.petscare.org.vista.Interfaces.onNextFragmentListener
import com.petscare.org.R
import com.petscare.org.databinding.FragmentVerificationBinding
import java.lang.Exception
import java.util.concurrent.TimeUnit

class FragmentVerification : Fragment() {

    //Objetos para la vinculacióin de vistas (View Binding)
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    //Objetos para el uso de Firebase Phone Auth
    private lateinit var auth: FirebaseAuth
    private lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var id_verificacion_guardado: String
    private lateinit var token_reenvio: PhoneAuthProvider.ForceResendingToken

    //Propiedades y objetos necesarios para el funcionameinto del fragment
    private lateinit var telefono: String
    private lateinit var contador: CountDownTimer
    private lateinit var change_frag_listener : onNextFragmentListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telefono = arguments?.getString("telefono")!!
        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        getCodeListener()
        enviarCodigo()
        iniciarContadorTiempo()
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
                verificarErrores(firebaseException)
            }

            override fun onCodeSent(verification_id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verification_id, token)

                Toast.makeText(activity, "Se envió el código de verificación", Toast.LENGTH_SHORT).show()
                id_verificacion_guardado = verification_id
                token_reenvio = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    cambiarActivity()
                } else {
                    verificarErrores(task.exception)
                }
            }
    }

    private fun verificarErrores(exception: Exception?) {
        when(exception){
            is FirebaseAuthUserCollisionException -> Toast.makeText(requireContext(),"El número de telefono ingresado ya esta asociado a otra cuenta de Petscare",Toast.LENGTH_SHORT).show()
            is FirebaseNetworkException -> Toast.makeText(requireContext(),"No hay conexión a internet", Toast.LENGTH_SHORT).show()
            is FirebaseAuthInvalidCredentialsException -> binding.ctxCodigo.error = "Código incorrecto"
            else -> Toast.makeText(requireContext(),"Hubo un error: ${exception?.message}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun cambiarActivity() {
        contador.cancel()
        val data = bundleOf("index" to 1)
        change_frag_listener.cambiarFragment(data)
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

    private fun iniciarContadorTiempo() {
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
        binding.btnVerificar.setOnClickListener { verificarCampoCodigo() }
    }

    private fun verificarCampoCodigo() {
        binding.ctxCodigo.error = null
        if (binding.ctxCodigo.editText?.text.toString().isNotEmpty()){
            if (binding.ctxCodigo.editText?.text.toString().length == 6){
                val codigo = binding.ctxCodigo.editText?.text.toString()
                val credencial = PhoneAuthProvider.getCredential(id_verificacion_guardado,codigo)
                signInWithPhoneAuthCredential(credencial)
            } else{
                binding.ctxCodigo.error = "El código debe tener 6 digitos"
            }
        } else{
            binding.ctxCodigo.error = "Ingresa el código"
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        change_frag_listener = context as onNextFragmentListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}