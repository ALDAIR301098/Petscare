package com.petscare.org.vista.fragments.auth

import android.content.Context
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
import androidx.fragment.app.activityViewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.petscare.org.R
import com.petscare.org.databinding.FragmentVerificationBinding
import com.petscare.org.viewmodel.ViewModelAuth
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import com.petscare.org.vista.activitys.ActivityMenu
import com.petscare.org.vista.activitys.ActivityRegistro
import java.lang.Exception
import java.util.concurrent.TimeUnit

class FragmentVerification : Fragment(){

    private val vmAuth: ViewModelAuth by activityViewModels()
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    //Objetos para el uso de Firebase Phone Auth
    private lateinit var auth: FirebaseAuth
    private lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var id_verificacion_guardado: String
    private lateinit var token_reenvio: PhoneAuthProvider.ForceResendingToken

    //Contador de tiempo
    private lateinit var contador: CountDownTimer
    private var tiempo_contador: Long = 15000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        verificarCodigoEnviado()
        iniciarContadorTiempo()
        eventosUI()
    }

    fun verificarCodigoEnviado() {
        if (vmAuth.isCodigoEnviado() == false){
            getCodeListener()
            enviarCodigo()
        } else{
            id_verificacion_guardado = vmAuth.getCodigoVerificacionGuardado()!!
        }
    }

    private fun getCodeListener() {
        mcallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                binding.ctxCodigo.error = null
                binding.ctxCodigo.editText?.setText(credential.smsCode)
                Toast.makeText(requireContext(), "Verificacion exitosa", Toast.LENGTH_SHORT).show()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(firebaseException: FirebaseException) {
                verificarErrores(firebaseException)
            }

            override fun onCodeSent(verification_id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verification_id, token)
                Toast.makeText(requireContext(), "Se envió el código de verificación", Toast.LENGTH_SHORT).show()
                id_verificacion_guardado = verification_id
                vmAuth.setIdVerificacionGuardado(id_verificacion_guardado)
                token_reenvio = token
                vmAuth.setIsCodigoEnviado(true)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val db = Firebase.firestore
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val info = db.collection("Usuarios").document(task.result.user!!.uid)
                    info.get().addOnCompleteListener(requireActivity()) { task_data ->
                        if (task_data.isSuccessful){
                            val document_info = task_data.result
                            if (document_info.exists()){
                                cambiarActivity(0,null)
                            } else{
                                cambiarActivity(1, task.result.user!!.uid)
                            }
                        } else{
                            Toast.makeText(requireContext(),"Hubo un error",Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    verificarErrores(task.exception)
                }
            }
    }

    private fun verificarErrores(exception: Exception?) {
        when (exception) {
            is FirebaseAuthUserCollisionException -> Toast.makeText(requireContext(), "El número de telefono ingresado ya esta asociado a otra cuenta de Petscare", Toast.LENGTH_SHORT).show()
            is FirebaseNetworkException -> Toast.makeText(requireContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show()
            is FirebaseAuthInvalidCredentialsException -> binding.ctxCodigo.error = "Código incorrecto"
            else -> Toast.makeText(requireContext(), "Hubo un error: ${exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cambiarActivity(opc : Int, UID: String?) {
        val intent = Intent()
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (opc == 0){
            intent.setClass(requireContext(),ActivityMenu::class.java)
        } else {
            val bundle = Bundle()
            bundle.putString("lada", vmAuth.getLada())
            bundle.putString("telefono", vmAuth.getTelefono())
            bundle.putString("UID", UID)
            intent.putExtras(bundle)
            intent.setClass(requireContext(),ActivityRegistro::class.java)
        }
        startActivity(intent)
    }

    private fun mostrarNumero() {
        binding.txtNumero.setText("Ingresa el código enviado al ".plus(vmAuth.getLada()).plus(" ${vmAuth.getTelefono()}"))
    }

    private fun enviarCodigo() {
        auth.useAppLanguage()
        val options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("${vmAuth.getLada()}".plus(vmAuth.getTelefono()))
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mcallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun iniciarContadorTiempo() {
        tiempo_contador = vmAuth.getTiempoContador()
        contador = object : CountDownTimer(tiempo_contador, 1000) {
            override fun onTick(p0: Long) {
                tiempo_contador = p0
                binding.btnReenviar.text = "Reenviar ${p0 / 1000}s"
            }

            override fun onFinish() {
                binding.btnReenviar.text = "Reenviar"
                habilitarBtnReenviar()
                contador.cancel()
            }
        }.start()
    }

    private fun eventosUI() {
        binding.btnVerificar.setOnClickListener { verificarCampoCodigo() }
    }

    private fun verificarCampoCodigo() {
        binding.ctxCodigo.error = null
        if (binding.ctxCodigo.editText?.text.toString().isNotEmpty()) {
            if (binding.ctxCodigo.editText?.text.toString().length == 6) {
                val codigo = binding.ctxCodigo.editText?.text.toString()
                val credencial = PhoneAuthProvider.getCredential(id_verificacion_guardado, codigo)
                signInWithPhoneAuthCredential(credencial)
            } else {
                binding.ctxCodigo.error = "El código debe tener 6 digitos"
            }
        } else {
            binding.ctxCodigo.error = "Ingresa el código"
        }
    }

    private fun habilitarBtnReenviar() {
        binding.btnReenviar.isEnabled = true
        binding.btnReenviar.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.cafe)
        binding.btnReenviar.setTextColor(ContextCompat.getColor(requireContext(), R.color.blanco))
        binding.btnReenviar.compoundDrawableTintList = valueOf(ContextCompat.getColor(requireContext(), R.color.blanco))
    }

    override fun onPause() {
        super.onPause()
        vmAuth.setTiempoContador(tiempo_contador)
    }

    override fun onDestroy() {
        super.onDestroy()
        contador.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}