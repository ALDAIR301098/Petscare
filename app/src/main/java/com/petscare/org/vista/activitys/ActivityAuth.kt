package com.petscare.org.vista.activitys

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.petscare.org.R
import com.petscare.org.databinding.ActivityAuthBinding
import com.petscare.org.utilidades.KeyboardUtil
import com.petscare.org.viewmodel.ViewModelAuth
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import com.petscare.org.vista.fragments.auth.FragmentTelefono
import com.petscare.org.vista.fragments.auth.FragmentVerification

class ActivityAuth : AppCompatActivity(), OnFragmentNavigationListener{

    private val vmAuth : ViewModelAuth by viewModels()
    private lateinit var binding : ActivityAuthBinding

    private lateinit var network_callback: ConnectivityManager.NetworkCallback

    private var index : Int = 0
    private lateinit var transaction: FragmentTransaction
    private lateinit var frag_telefono : Fragment
    private lateinit var frag_verification : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_TOOLBAR_ACTIVITY)
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearFragments()
        mostrarFragment(index)
        observarConexionInternet()

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun crearFragments() {
        this.index = vmAuth.getIndex()!!
        frag_telefono = FragmentTelefono()
        frag_verification = FragmentVerification()
    }

    override fun mostrarFragment(index: Int) {
        transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
        this.index = index
        when(this.index){
            0 -> transaction.replace(R.id.contenedor_frags_auth,frag_telefono).commit()
            1 -> transaction.replace(R.id.contenedor_frags_auth,frag_verification).commit()
        }
    }

    private fun observarConexionInternet() {
        network_callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                runOnUiThread(Runnable{
                    binding.layoutNoConection.visibility = View.GONE
                    binding.layoutNormal.visibility = View.VISIBLE
                })
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                runOnUiThread(Runnable{
                    KeyboardUtil.cerrarTeclado(binding.root)
                    binding.layoutNoConection.visibility = View.VISIBLE
                    binding.layoutNormal.visibility = View.GONE
                    binding.animNoInternet.playAnimation()
                })
            }
        }

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(network_callback)
        } else {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            cm.registerNetworkCallback(networkRequest, network_callback)
        }
    }

    override fun onBackPressed() {
        if (index == 0){
            finish()
        } else if (index == 1){
            Toast.makeText(this, "No puedes cancelar el proceso de verificación de la cuenta.",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        vmAuth.setIndex(this.index)
    }

    override fun onDestroy() {
        super.onDestroy()
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).unregisterNetworkCallback(network_callback)
    }
}
