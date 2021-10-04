package com.petscare.org.vista.fragments.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.petscare.org.databinding.FragmentNombreBinding
import com.petscare.org.vista.Interfaces.IFragmentData
import com.petscare.org.viewmodel.ViewModelRegistro


class FragmentNombre : Fragment(), IFragmentData{

    private var _binding : FragmentNombreBinding? = null
    private val binding get() = _binding!!
    private val vmRegistro : ViewModelRegistro by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNombreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obserbarLiveData()
    }

    private fun obserbarLiveData() {
        vmRegistro.ldata_registro.observe(viewLifecycleOwner, Observer { ldata_registro ->
            binding.ctxNombre.editText?.setText(ldata_registro.nombre)
            binding.ctxApellidos.editText?.setText(ldata_registro.apellidos)
        })
    }

    override fun verificarCampos(): Boolean {
        val bool_nombre: Boolean ; val bool_apellidos: Boolean
        if (binding.ctxNombre.editText?.text.toString().isNotEmpty()) {
            bool_nombre = true
            binding.ctxNombre.error = null
        } else{
            bool_nombre = false
            binding.ctxNombre.error = "Ingrese el nombre"
        }

        if (binding.ctxApellidos.editText?.text.toString().isNotEmpty()) {
            bool_apellidos = true
            binding.ctxApellidos.error = null
        } else{
            bool_apellidos = false
            binding.ctxApellidos.error = "Ingrese los apellidos"
        }

        return bool_nombre && bool_apellidos
    }

    override fun salvarDatos() {
        vmRegistro.ldata_registro.value?.nombre = binding.ctxNombre.editText?.text.toString()
        vmRegistro.ldata_registro.value?.apellidos = binding.ctxApellidos.editText?.text.toString()
    }

    override fun onPause() {
        salvarDatos()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}