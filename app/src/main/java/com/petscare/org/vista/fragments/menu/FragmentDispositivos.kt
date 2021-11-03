package com.petscare.org.vista.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.petscare.org.R
import com.petscare.org.databinding.FragmentDispositivosBinding
import com.petscare.org.domain.providers.TipoDispositivo
import com.petscare.org.modelo.objetos.Dispositivo
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorDispositivos

class FragmentDispositivos : Fragment() {

    private var _binding: FragmentDispositivosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adaptador_dispositivos: AdaptadorDispositivos

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDispositivosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarRecycler()
    }

    private fun mostrarRecycler() {

        val lista_dispositivos = listOf(
            Dispositivo("Dispensador de Agua 1",TipoDispositivo.DISPENSADOR_AGUA.name,true,87f,false),
            Dispositivo("Dispensador de Alimento 2",TipoDispositivo.DISPENSADOR_ALIMENTO.name,false,null),
            Dispositivo("Puerta principal",TipoDispositivo.PUERTA.name,true,null,true),
            Dispositivo("Maquina propina",TipoDispositivo.MAQUINA_PROPINAS.name,true,127.50f,true),
            Dispositivo("Aire acondicionado",TipoDispositivo.AIRE_ACONDICIONADO.name,true,22.5f,true),
            Dispositivo("Secadora", TipoDispositivo.SECADORA.name,false,null),
            Dispositivo("Foco 1", TipoDispositivo.FOCO.name,true,null,false),
            Dispositivo("Maquina propina",TipoDispositivo.MAQUINA_PROPINAS.name,true,127.50f,true),
            Dispositivo("Aire acondicionado",TipoDispositivo.AIRE_ACONDICIONADO.name,true,22.5f,true),
            Dispositivo("Secadora", TipoDispositivo.SECADORA.name,false,null),
            Dispositivo("Foco 1", TipoDispositivo.FOCO.name,true,null,false)
        )

        binding.recyclerDispositivos.layoutManager = GridLayoutManager(requireContext(),2)
        adaptador_dispositivos = AdaptadorDispositivos(requireContext(),lista_dispositivos)
        binding.recyclerDispositivos.adapter = adaptador_dispositivos
        adaptador_dispositivos.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}