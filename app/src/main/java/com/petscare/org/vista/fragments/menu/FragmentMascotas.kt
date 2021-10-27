package com.petscare.org.vista.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.content.Intent
import com.petscare.org.vista.activitys.ActivityAgregarMascota
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petscare.org.R
import com.petscare.org.databinding.FragmentMascotasBinding
import com.petscare.org.viewmodel.ViewModelMascota
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorMascotas

class FragmentMascotas : Fragment() {

    private val vmMascotas: ViewModelMascota by activityViewModels()
    private var _binding: FragmentMascotasBinding? = null
    private val binding get() = _binding!!
    private lateinit var adaptador_mascotas : AdaptadorMascotas
    private var edad: Int= 0; //Edad

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMascotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mostrarRecycler()
        eventosUI()

        vmMascotas.ldata_edad.observe(viewLifecycleOwner){ edad ->
            this.edad = edad
        }
    }

    private fun mostrarRecycler() {
        val view = layoutInflater.inflate(R.layout.dialogo_foto,requireActivity().findViewById(R.id.contenedor_dialogo_fm),false)
        adaptador_mascotas = AdaptadorMascotas(requireContext(),view)
        binding.recyclerMascotas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMascotas.adapter = adaptador_mascotas
        observar_ldata()

        binding.recyclerMascotas.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)binding.fabAgregar.shrink()
                else binding.fabAgregar.extend()
            }
        })
    }

    fun observar_ldata(){
        vmMascotas.getListaMascotas().observe(viewLifecycleOwner){
            adaptador_mascotas.setListaMascotas(it)
            adaptador_mascotas.notifyDataSetChanged()
        }
    }

    private fun eventosUI() {
        binding.fabAgregar.setOnClickListener { view: View? ->
        edad++
        Toast.makeText(requireContext(),"$edad",Toast.LENGTH_SHORT).show()
        //val intent = Intent(requireContext(), ActivityAgregarMascota::class.java)
            //result_agregar_mascota.launch(intent)
        }
    }

    var result_agregar_mascota = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onPause() {
        super.onPause()
        vmMascotas.ldata_edad.value = edad
    }
}