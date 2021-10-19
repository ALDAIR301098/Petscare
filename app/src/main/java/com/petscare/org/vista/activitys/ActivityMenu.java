package com.petscare.org.vista.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.petscare.org.R;
import com.petscare.org.databinding.ActivityMenuBinding;
import com.petscare.org.vista.fragments.menu.FragmentFeed;
import com.petscare.org.vista.fragments.menu.FragmentMascotas;
import com.petscare.org.vista.fragments.menu.FragmentPerfil;
import com.petscare.org.vista.fragments.menu.FragmentServicios;

public class ActivityMenu extends AppCompatActivity {

    private ActivityMenuBinding binding;

    private FragmentTransaction transaction;
    private FragmentFeed frag_feed;
    private FragmentMascotas frag_mascotas;
    private FragmentServicios frag_servicios;
    private FragmentPerfil frag_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.THEME_GLOBAL_APP);
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        crearFragments();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_frags_menu,frag_feed).commit();
        eventosUI();

    }

    private void eventosUI() {
        binding.bottomNavigationMenu.setOnItemSelectedListener( item ->{
            transaction = getSupportFragmentManager().beginTransaction();
            if (item.getItemId() == R.id.frag_feed){
                transaction.replace(R.id.contenedor_frags_menu,frag_feed).commit();
                return true;
            } else if (item.getItemId() == R.id.frag_mascotas){
                transaction.replace(R.id.contenedor_frags_menu,frag_mascotas).commit();
                return true;
            } else if (item.getItemId() == R.id.frag_servicios){
                transaction.replace(R.id.contenedor_frags_menu,frag_servicios).commit();
                return true;
            } else  if (item.getItemId() == R.id.frag_perfil){
                transaction.replace(R.id.contenedor_frags_menu,frag_perfil).commit();
                return true;
            }
            return false;
        });
    }

    private void crearFragments() {
        transaction = getSupportFragmentManager().beginTransaction();
        frag_feed = new FragmentFeed();
        frag_mascotas = new FragmentMascotas();
        frag_servicios = new FragmentServicios();
        frag_perfil = new FragmentPerfil();
    }
}