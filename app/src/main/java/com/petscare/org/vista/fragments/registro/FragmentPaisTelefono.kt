package com.petscare.org.vista.fragments.registro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.petscare.org.R
import com.petscare.org.databinding.FragmentPaisTelefonoBinding
import com.petscare.org.modelo.ModeloPais
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.AdminDataFragments
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import com.petscare.org.vista.adaptadores.dialogos.AdaptadorSelectorPaises

class FragmentPaisTelefono : Fragment(), AdminDataFragments {

    private val vm_registro : ViewModelRegistro by activityViewModels()
    private lateinit var change_fragment_listener: OnFragmentNavigationListener
    private var _binding: FragmentPaisTelefonoBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        change_fragment_listener = context as OnFragmentNavigationListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPaisTelefonoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observarLiveData()
        eventosUI()
    }

    private fun observarLiveData() {
        vm_registro.ldata_registro.observe(viewLifecycleOwner, { ldata_registro ->
            binding.ctxPais.editText?.setText(ldata_registro.pais)
            binding.ctxTelefono.editText?.setText(ldata_registro.telefono)
        })
    }

    private fun eventosUI() {
        binding.btnElegir.setOnClickListener { mostrarSelectorPais() }
    }

    private fun mostrarSelectorPais() {

        val lista_paises = ArrayList<ModeloPais>()
        lista_paises.add(ModeloPais("México","52",R.drawable.ic_mexico))
        lista_paises.add(ModeloPais("Estados Unidos","1",R.drawable.ic_estados_unidos))
        lista_paises.add(ModeloPais("Alemania","49",R.drawable.ic_alemania))
        lista_paises.add(ModeloPais("Argentina","54",R.drawable.ic_argentina))
        lista_paises.add(ModeloPais("Brasil","55",R.drawable.ic_brasil))
        lista_paises.add(ModeloPais("Canada","1",R.drawable.ic_canada))
        lista_paises.add(ModeloPais("Chile","56",R.drawable.ic_chile))
        lista_paises.add(ModeloPais("China","86",R.drawable.ic_china))
        lista_paises.add(ModeloPais("Colombia","57",R.drawable.ic_colombia))
        lista_paises.add(ModeloPais("Corea del sur","82",R.drawable.ic_corea_del_sur))
        lista_paises.add(ModeloPais("Costa Rica","506",R.drawable.ic_costa_rica))
        lista_paises.add(ModeloPais("Ecuador","593",R.drawable.ic_ecuador))
        lista_paises.add(ModeloPais("España","34",R.drawable.ic_espana))
        lista_paises.add(ModeloPais("Francia","33",R.drawable.ic_francia))
        lista_paises.add(ModeloPais("Inglaterra","44",R.drawable.ic_inglaterra))
        lista_paises.add(ModeloPais("Italia","39",R.drawable.ic_italia))
        lista_paises.add(ModeloPais("Japon","81",R.drawable.ic_japon))
        lista_paises.add(ModeloPais("Panama","507",R.drawable.ic_panama))
        lista_paises.add(ModeloPais("Paraguay","595",R.drawable.ic_paraguay))
        lista_paises.add(ModeloPais("Peru","51",R.drawable.ic_peru))
        lista_paises.add(ModeloPais("Portugal","351",R.drawable.ic_portugal))
        lista_paises.add(ModeloPais("Rusia","7",R.drawable.ic_rusia))
        lista_paises.add(ModeloPais("Uruguay","598",R.drawable.ic_uruguay))
        lista_paises.add(ModeloPais("Venezuela","58",R.drawable.ic_venezuela))

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Elige tu país")
            .setAdapter(AdaptadorSelectorPaises(requireActivity(),R.layout.items_selector_paises,lista_paises)) { dialogInterface, index ->
                binding.ctxPais.editText?.setText(lista_paises.get(index).nombre_pais)
                binding.ctxTelefono.prefixText = "+".plus(lista_paises.get(index).lada)
            }
            .show()
    }

    override fun salvarDatos() {
        vm_registro.setPais(binding.ctxPais.editText?.text.toString())
        vm_registro.setLada(binding.ctxTelefono.prefixText.toString())
        vm_registro.setTelefono(binding.ctxTelefono.editText?.text.toString())
    }

    override fun verificarCampos() {
        val bool_pais : Boolean
        val bool_telefono : Boolean

        if (binding.ctxPais.editText?.text.toString().isNotEmpty()){                                //Verifica que el campo de texto País no está vacio
            binding.ctxPais.error = null
            bool_pais = true
        } else{
            binding.ctxPais.error = "Seleccione el país"
            bool_pais = false
        }

        if (binding.ctxTelefono.editText?.text.toString().isNotEmpty()){                            //Verifica que el campo de texto teléfono no está vacio
            binding.ctxTelefono.error = null
            if (binding.ctxTelefono.editText?.text?.length == 10){                                  //Verifica si el campo de texto telefono tiene mínimo 10 dígitos
                binding.ctxTelefono.error = null
                bool_telefono = true
            } else{
                binding.ctxTelefono.error = "Mínimo 10 dígitos"
                bool_telefono = false
            }
        } else{
            binding.ctxTelefono.error = "Ingrese el número de teléfono"
            bool_telefono = false
        }

        if (bool_pais  && bool_telefono){
            salvarDatos()
            change_fragment_listener.mostrarFragment(4)
        } else{
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