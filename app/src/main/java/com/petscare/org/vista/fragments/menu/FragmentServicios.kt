package com.petscare.org.vista.fragments.menu
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorPrueba
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.petscare.org.databinding.FragmentServiciosBinding

class FragmentServicios : Fragment() {
    private var binding: FragmentServiciosBinding? = null
    private lateinit var adaptador: AdaptadorPrueba
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentServiciosBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarRecycler()
    }

    private fun mostrarRecycler() {
        val id_usuario = FirebaseAuth.getInstance().currentUser!!.uid
        val query: Query = FirebaseFirestore.getInstance().collection("Usuarios")
            .document(id_usuario).collection("Mascotas")
        adaptador = AdaptadorPrueba(requireContext(), query)
        binding!!.recyclerPrueba.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerPrueba.adapter = adaptador

    }

    override fun onStart() {
        super.onStart()
        adaptador.startListener()
    }

    override fun onStop() {
        super.onStop()
        adaptador.stopListener()
    }
}