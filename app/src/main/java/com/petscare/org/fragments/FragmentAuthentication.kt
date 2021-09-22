package com.petscare.org.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.petscare.org.R
import com.petscare.org.databinding.FrgAuthenticationBinding

class FragmentAuthentication : Fragment() {

    private var _binding: FrgAuthenticationBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FrgAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        eventosUI()
    }

    private fun eventosUI(){
        binding.btnContinuar.setOnClickListener {verificarNumero()}
    }

    private fun verificarNumero() {
        binding.ctxTelefono.error = null
        val numero_tel = binding.ctxTelefono.editText?.text.toString()
        if (numero_tel.isNotEmpty()){
            if (numero_tel.length==10){
                mostrarFragmentVerificacion()
            } else{
                mostrarError("Número de teléfono no válido")
            }
        } else{
            mostrarError("Ingrese su número de teléfono.")
        }
    }

    private fun mostrarError(text: String) {
        binding.ctxTelefono.error = text
    }

    private fun mostrarFragmentVerificacion() {
        navController.navigate(R.id.action_fragmentAuthentication_to_fragmentVerification)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}