package com.petscare.org.view.fragments.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.petscare.org.databinding.FragmentEdadGeneroBinding
import java.text.SimpleDateFormat
import java.util.*

class FragmenteEdadGenero : Fragment() {

    private var _binding : FragmentEdadGeneroBinding? = null
    private val binding get() = _binding!!

    private val formatear_fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEdadGeneroBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventosUI()
    }

    private fun eventosUI() {

        val lista_genero = listOf("Masculino","Femenino")
        val adapter_genero = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,lista_genero)
        binding.spinnerGenero.setAdapter(adapter_genero)

        binding.ctxFechaNacimiento.setStartIconOnClickListener { mostrarSelectorFecha() }
        binding.ctxFechaNacimiento.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(texto: CharSequence?, start: Int, before: Int, count: Int) {
                if (texto?.length == 2 && start == 1){
                    binding.ctxFechaNacimiento.editText?.text?.append("/")
                } else if(texto?.length == 5 && start == 4){
                    binding.ctxFechaNacimiento.editText?.text?.append("/")
                }
            }
        })
    }

    private fun mostrarSelectorFecha() {
        val constraintsBuilder = CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds())
        val calendario = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de nacimiento")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
        calendario.show(requireActivity().supportFragmentManager,"Calendario")
        calendario.addOnPositiveButtonClickListener { establecerFecha(calendario.selection)
        }
    }

    private fun establecerFecha(fecha: Long?) {
        binding.ctxFechaNacimiento.editText?.setText(formatear_fecha.format(fecha))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}