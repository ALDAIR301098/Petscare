package com.petscare.org.vista.fragments.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.petscare.org.databinding.FragmentPerfilBinding;
import com.petscare.org.utilidades.InternetUtil;
import com.petscare.org.vista.activitys.ActivityInicio;

public class FragmentPerfil extends Fragment {

    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventosUI();
    }

    private void eventosUI() {
        binding.btnCerrarSesion.setOnClickListener(view -> {
            if (InternetUtil.Companion.verificarConexionInternet(requireContext())){
                cerrarSesion();
            } else {
                Toast.makeText(requireContext(),"Comprueba tu conexíon a internet",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cerrarSesion() {
        FirebaseAuth firebaseAuth;
        FirebaseAuth.AuthStateListener auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(getContext(), ActivityInicio.class));
                    requireActivity().finish();
                }
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(auth_listener);
        firebaseAuth.signOut();
    }
}