package com.petscare.org.vista.fragments.authentication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.petscare.org.databinding.FragmentAuthenticationBinding
import com.petscare.org.vista.Interfaces.onNextFragmentListener

class FragmentAuthentication : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!
    private lateinit var telefono: String
    private lateinit var change_frag_listener : onNextFragmentListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventosUI()
    }

    private fun eventosUI(){
        binding.btnContinuar.setOnClickListener {verificarNumero()}
    }

    private fun verificarNumero() {
    binding.ctxTelefono.error = null
        telefono = binding.ctxTelefono.editText?.text.toString()
        if (telefono.isNotEmpty()){
            if (telefono.length==10){
                cambiarFragment()
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

    private fun cambiarFragment() {
        val data = bundleOf("telefono" to telefono, "index" to 0)
        change_frag_listener.cambiarFragment(data)
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