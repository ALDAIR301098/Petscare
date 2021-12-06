package com.petscare.org.vista.fragments.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.petscare.org.databinding.FragmentDispositivosBinding
import com.petscare.org.domain.providers.TipoDispositivo
import com.petscare.org.modelo.objetos.Dispositivo
import com.petscare.org.vista.Interfaces.card_draggable.MyItemTouchHelperCallback
import com.petscare.org.vista.Interfaces.card_draggable.OnStartDragListener
import com.petscare.org.vista.activitys.ActivityAgregarDispositivoBT
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorDispositivos
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorIOT

class FragmentDispositivos : Fragment() {

    private var _binding: FragmentDispositivosBinding? = null
    private val binding get() = _binding!!
    private var itemTouchHelper:ItemTouchHelper? = null

    private lateinit var adaptador_dispositivos: AdaptadorIOT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDispositivosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarRecycler()
        eventosUI()

    }

    private fun eventosUI() {
        binding.fabAgregarDispositivo.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityAgregarDispositivoBT::class.java))
        }
    }


    private fun mostrarRecycler() {

        binding.recyclerDispositivos.layoutManager = GridLayoutManager(requireContext(),2)
        adaptador_dispositivos = AdaptadorIOT(requireContext())
        binding.recyclerDispositivos.adapter = adaptador_dispositivos
        adaptador_dispositivos.notifyDataSetChanged()

        /*val lista_dispositivos = mutableListOf(
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

        binding.recyclerDispositivos.setHasFixedSize(true)
        binding.recyclerDispositivos.layoutManager = GridLayoutManager(requireContext(),2)
        adaptador_dispositivos = AdaptadorDispositivos(requireContext(),lista_dispositivos,object : OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
                itemTouchHelper!!.startDrag(viewHolder!!)
            }

        })
        binding.recyclerDispositivos.adapter = adaptador_dispositivos
        val callback = MyItemTouchHelperCallback(adaptador_dispositivos)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(binding.recyclerDispositivos)
        */

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}