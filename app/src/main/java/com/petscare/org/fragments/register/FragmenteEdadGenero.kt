package com.petscare.org.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.petscare.org.R
import com.petscare.org.databinding.FragmentEdadGeneroBinding

class FragmenteEdadGenero : Fragment() {

    private var _binding : FragmentEdadGeneroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEdadGeneroBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventosUI()
    }

    private fun eventosUI() {
        binding.ctxFechaNacimiento.setStartIconOnClickListener { mostrarSelectorFecha() }
    }

    private fun mostrarSelectorFecha() {
        val calendario = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de nacimiento")
            .build()

        calendario.show(requireActivity().supportFragmentManager,"Calendario")
        calendario.addOnPositiveButtonClickListener { establecerFecha() }
    }

    private fun establecerFecha() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}