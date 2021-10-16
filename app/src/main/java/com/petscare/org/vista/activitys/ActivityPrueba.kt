package com.petscare.org.vista.activitys

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.petscare.org.R
import com.petscare.org.databinding.ActivityPruebaBinding

class ActivityPrueba : AppCompatActivity() {

    private lateinit var binding: ActivityPruebaBinding
    private lateinit var network_callback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observarConexionInternet()
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
                    binding.layoutNoConection.visibility = View.VISIBLE
                    binding.layoutNormal.visibility = View.GONE
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

    override fun onPause() {
        super.onPause()
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).unregisterNetworkCallback(network_callback)
    }
}
