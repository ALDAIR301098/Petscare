package com.petscare.org.vista.fragments.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petscare.org.R;
import com.petscare.org.modelo.objetos.Noticia;
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorFeed;

import java.util.ArrayList;

public class FragmentFeed extends Fragment {

    String[] Noticias = {"Tamaulipas", "Queretaro", "Yucatan", "Guanajuato", "chihuahua"};



    //CREACION DE LOS OBJETOS DE CLASE RECYCLERVIEW Y ADAPTADOR
    RecyclerView recyclerView;
    AdaptadorFeed adaptadorFeed;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);



    }
   //SE USA EL METODO ONVIEWCREATED, de manera que infla el recycler una vez el fragment este cargado
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //SE CREA UN ARRAYLIST DE NOTICIAS, PARA CREAR Y OBTENER MUCHAS PLANTILLAS DE LA CLASE
        ArrayList <Noticia> listadenoticias = new ArrayList<Noticia>();
        listadenoticias.add(new Noticia("Tamaulipas","La vanguardia"));
        listadenoticias.add(new Noticia("Chihuahua","Prensa digital"));
        listadenoticias.add(new Noticia("Durango","El Universal"));
        listadenoticias.add(new Noticia("Queretaro","La nacion"));


        //Se llama al objeto recyclerView y se le infla la vista del RecyclerView-feed
        recyclerView = view.findViewById(R.id.recyclerView_feed);
        // Se llama al objeto del adaptado y se le llama como objeto como tal
        adaptadorFeed = new AdaptadorFeed(listadenoticias);
        //ACTIVIDADES REQUIRE CONTEXT, PORQUE MUCHAS ACTIVIDADES PIDEN EL CONTEXTO ( NO TODOS, SOLO ALGUNOS EN FRAGMENTS )
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //Se le asigna el adaptador programado haciendo llamado al OBJETO de la clase adaptador
        recyclerView.setAdapter(adaptadorFeed);



    }
}