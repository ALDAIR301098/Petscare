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
import com.petscare.org.vista.adaptadores.recyclers.AdaptadorFeed;

public class FragmentFeed extends Fragment {

    String[] Noticias = {"Tamaulipas", "Queretaro", "Yucatan", "Guanajuato", "chihuahua"};

    RecyclerView recyclerView;
    AdaptadorFeed adaptadorFeed;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView_feed);
        adaptadorFeed = new AdaptadorFeed();
        //ACTIVIDADES REQUIRE CONTEXT, PORQUE MUCHAS ACTIVIDADES PIDEN EL CONTEXTO ( NO TODOS, SOLO ALGUNOS EN FRAGMENTS )
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adaptadorFeed);



    }
}