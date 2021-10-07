package com.petscare.org.vista.fragments.registro

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.petscare.org.databinding.FragmentEdadGeneroBinding
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.AdminDataFragments
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FragmenteEdadGenero : Fragment(), AdminDataFragments {

    private val vmRegistro: ViewModelRegistro by activityViewModels()
    private lateinit var change_fragment_listener: OnFragmentNavigationListener
    private var _binding: FragmentEdadGeneroBinding? = null
    private val binding get() = _binding!!

    private val formatear_fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEdadGeneroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventosUI()
        observar_ldata()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        change_fragment_listener = context as OnFragmentNavigationListener
    }

    private fun observar_ldata() {
        vmRegistro.ldata_registro.observe(viewLifecycleOwner, { ldata_registro ->
                binding.ctxFechaNacimiento.editText?.setText(ldata_registro.fecha_nacimiento)
                binding.ctxGenero.editText?.setText(ldata_registro.genero)
            })
    }

    private fun eventosUI() {

        //Llenar el espinner genero con sus valores
        val lista_genero = listOf("Masculino", "Femenino")
        val adapter_genero = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, lista_genero)
        binding.spinnerGenero.setAdapter(adapter_genero)

        binding.ctxFechaNacimiento.setStartIconOnClickListener { mostrarSelectorFecha() }
        //Formatear la fecha en tiempo real
        binding.ctxFechaNacimiento.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(texto: CharSequence?, start: Int, before: Int, count: Int) {
                if (texto?.length == 2 && start == 1) {
                    binding.ctxFechaNacimiento.editText?.text?.append("/")
                } else if (texto?.length == 5 && start == 4) {
                    binding.ctxFechaNacimiento.editText?.text?.append("/")
                }
            }
        })
        binding.btnSiguiente.setOnClickListener { verificarCampos() }
    }

    private fun mostrarSelectorFecha() {
        val constraintsBuilder = CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds())
        val calendario = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de nacimiento")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
        calendario.show(requireActivity().supportFragmentManager, "Calendario")
        calendario.addOnPositiveButtonClickListener {
            binding.ctxFechaNacimiento.editText?.setText(formatear_fecha.format(calendario.selection))
        }
    }

    private fun validarFecha(fecha: String): Boolean {
        try {
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            formatoFecha.isLenient = false
            formatoFecha.parse(fecha)
        } catch (ex: ParseException) {
            return false
        }
        return true
    }

    override fun salvarDatos() {
        vmRegistro.setFechaNacimiento(binding.ctxFechaNacimiento.editText?.text.toString())
        vmRegistro.setGenero(binding.ctxGenero.editText?.text.toString())
    }

    override fun verificarCampos() {
        val bool_fecha: Boolean
        val bool_genero: Boolean

        if (binding.ctxFechaNacimiento.editText?.text.toString().isNotEmpty()) {
            binding.ctxFechaNacimiento.error = null
            if (validarFecha(binding.ctxFechaNacimiento.editText?.text.toString())) {
                bool_fecha = true
                binding.ctxFechaNacimiento.error = null
            } else {
                bool_fecha = false
                binding.ctxFechaNacimiento.error = "Fecha no válida"
            }
        } else {
            bool_fecha = false
            binding.ctxFechaNacimiento.error = "Ingrese su fecha de nacimiento"
        }

        if (binding.ctxGenero.editText?.text.toString().isNotEmpty()) {
            bool_genero = true
            binding.ctxGenero.error = null
        } else {
            bool_genero = false
            binding.ctxGenero.error = "Seleccione su genero"
        }

        return if (bool_fecha && bool_genero) {
            salvarDatos()
            change_fragment_listener.mostrarFragment(2)
        } else {
            salvarDatos()
        }
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