package com.petscare.org.vista.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import com.petscare.org.vista.activitys.ActivityAgregarMascota
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.ActivityResultCallback
import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.petscare.org.databinding.FragmentMascotasBinding

class FragmentMascotas : Fragment() {
    private var binding: FragmentMascotasBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMascotasBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventosUI()
    }

    private fun eventosUI() {
        binding!!.fabAgregar.setOnClickListener { view: View? ->
            val intent = Intent(requireContext(), ActivityAgregarMascota::class.java)
            result_agregar_mascota.launch(intent)
        }
    }

    var result_agregar_mascota = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(requireContext(),"EXITO",Toast.LENGTH_SHORT).show()
        }
    }
}