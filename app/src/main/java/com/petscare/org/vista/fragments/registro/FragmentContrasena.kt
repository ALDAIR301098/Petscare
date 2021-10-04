package com.petscare.org.vista.fragments.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.petscare.org.databinding.FragmentContrasenaBinding
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.IFragmentData

class FragmentContrasena : Fragment(), IFragmentData {

    //

    private val vmRegistro : ViewModelRegistro by activityViewModels()
    private var _binding : FragmentContrasenaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentContrasenaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observarLiveData()
    }

    private fun observarLiveData() {
        vmRegistro.ldata_registro.observe(viewLifecycleOwner, Observer { ldata_registro ->
            binding.ctxContrasena.editText?.setText(ldata_registro.contrasena)
            binding.ctxRContrasena.editText?.setText(ldata_registro.r_contrasena)
        })
    }

    override fun onPause() {
        salvarDatos()
        super.onPause()
    }

    override fun salvarDatos() {
        vmRegistro.ldata_registro.value?.contrasena = binding.ctxContrasena.editText?.text.toString()
        vmRegistro.ldata_registro.value?.r_contrasena = binding.ctxRContrasena.editText?.text.toString()
    }

    override fun verificarCampos(): Boolean {
        val bool_contrasena : Boolean; val bool_r_contrasena : Boolean

        if (binding.ctxContrasena.editText?.text.toString().isNotEmpty()){
            binding.ctxContrasena.error = null
            if (binding.ctxContrasena.editText?.text.toString().length >=8){
                bool_contrasena = true
                binding.ctxContrasena.error = null
            } else{
                bool_contrasena = false
                binding.ctxContrasena.error = "Mínimo 8 caracteres"
            }
        } else{
            bool_contrasena = false
            binding.ctxContrasena.error = "Ingrese una contraseña"
        }

        if (binding.ctxRContrasena.editText?.text.toString().isNotEmpty()){
            binding.ctxRContrasena.error = null
            if (binding.ctxRContrasena.editText?.text.toString().length >=8){
                bool_r_contrasena = true
                binding.ctxRContrasena.error = null
            } else{
                bool_r_contrasena = false
                binding.ctxRContrasena.error = "Mínimo 8 caracteres"
            }
        } else{
            bool_r_contrasena = false
            binding.ctxRContrasena.error = "Ingrese una contraseña"
        }

        if (bool_contrasena && bool_r_contrasena){
            if (binding.ctxContrasena.editText?.text.toString() != binding.ctxRContrasena.editText?.text.toString()){
                Toast.makeText(requireContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
                vmRegistro.ldata_registro.value?.frag_contrasena_verificado = false
                return false
            } else{
                vmRegistro.ldata_registro.value?.frag_contrasena_verificado = true
                return true
            }
        } else{
            return false
        }

    }
}