package com.petscare.org.vista.fragments.registro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.petscare.org.databinding.FragmentVerificarBinding
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.AdminDataFragments
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import java.lang.Exception
import java.util.concurrent.TimeUnit

class FragmentVerificar : Fragment(), AdminDataFragments {

    private val vmRegistro: ViewModelRegistro by activityViewModels()
    private lateinit var change_fragment_listener: OnFragmentNavigationListener
    private var _binding: FragmentVerificarBinding? = null
    private val binding get() = _binding!!

    //Objetos para el uso de Firebase Phone Auth
    private lateinit var auth: FirebaseAuth
    private lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var id_verificacion_guardado: String
    private lateinit var token_reenvio: PhoneAuthProvider.ForceResendingToken

    override fun onAttach(context: Context) {
        super.onAttach(context)
        change_fragment_listener = context as OnFragmentNavigationListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVerificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        mostrarNumero()
        getCodeListener()
        enviarCodigo()
    }

    private fun mostrarNumero() {
        binding.txtNumero.text = vmRegistro.getLada().plus(" ").plus(vmRegistro.getTelefono())
    }

    private fun enviarCodigo() {
        Toast.makeText(requireContext(),"Solicitando código de verificación",Toast.LENGTH_SHORT).show()
        auth.useAppLanguage()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(vmRegistro.getLada().plus(vmRegistro.getTelefono()))
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mcallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
                token_reenvio = token
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

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    registrarDatosUsuario(task.result.user!!.uid)
                    cambiarFragment()
                } else {
                    verificarErrores(task.exception)
                }
            }
    }

    private fun registrarDatosUsuario(UID : String) {
        val db = Firebase.firestore
        val user = hashMapOf(
            "Nombre" to vmRegistro.getNombre(),
            "Apellidos" to vmRegistro.getApellidos(),
            "Fecha de nacimiento" to vmRegistro.getFechaNacimiento(),
            "Genero" to vmRegistro.getGenero(),
            "Correo" to vmRegistro.getCorreo(),
            "Contraseña" to vmRegistro.getContrasena(),
            "Lada" to vmRegistro.getLada(),
            "Telefono" to vmRegistro.getTelefono()
        )

        db.collection("Usuarios").document(UID).set(user).addOnSuccessListener { listener ->
            cambiarFragment()
        }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Registro de datos fallido",Toast.LENGTH_SHORT).show()
            }
    }

    private fun cambiarFragment() {
        change_fragment_listener.mostrarFragment(5)
    }

    override fun salvarDatos() {

    }

    override fun verificarCampos() {
        if (binding.ctxCodigo.editText?.text.toString().isNotEmpty()) {
            if (binding.ctxCodigo.editText?.text.toString().length == 6) {
                val codigo = binding.ctxCodigo.editText?.text.toString()
                val credencial = PhoneAuthProvider.getCredential(id_verificacion_guardado, codigo)
                signInWithPhoneAuthCredential(credencial)
            } else {
                binding.ctxCodigo.error = "Mínimo 6 digitos"
            }
        } else {
            binding.ctxCodigo.error = "Ingrese el código"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}