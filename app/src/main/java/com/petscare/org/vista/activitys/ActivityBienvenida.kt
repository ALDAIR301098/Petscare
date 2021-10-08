package com.petscare.org.vista.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.petscare.org.R
import com.petscare.org.databinding.ActivityWelcomeBinding
import com.petscare.org.modelo.ModeloProveedorAuth
import com.petscare.org.vista.adaptadores.dialogos.AdaptadorListaOpciones
import com.petscare.org.vista.adaptadores.dialogos.AdaptadorSelectorPaises
import java.util.ArrayList

class ActivityBienvenida : AppCompatActivity(){

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retardarSplashScreen()
        eventosUI()
    }

    private fun retardarSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Thread.sleep(1500)
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            }
        )
    }

    private fun eventosUI(){
        binding.btnIngresar.setOnClickListener {
            val intent = Intent(this,ActivityAuthentication::class.java)
            intent.putExtra("auth_mode","login")
            startActivity(intent)
        }
        binding.btnRegistrar.setOnClickListener {
            mostrarSelectorProovedorAutenticacion()
        }
        binding.imgLogo.setOnClickListener {
            startActivity(Intent(this,ActivityMenu::class.java))
            finish()
        }
        binding.imgPajaro.setOnClickListener {
            startActivity(Intent(this,ActivityRegistro::class.java))
        }
    }

    private fun mostrarSelectorProovedorAutenticacion() {
        val items = ArrayList<ModeloProveedorAuth>()
        items.add(ModeloProveedorAuth("Telefono", R.drawable.ic_phone))
        items.add(ModeloProveedorAuth("Google",R.drawable.ic_google))
        items.add(ModeloProveedorAuth("Facebook",R.drawable.ic_facebook))

        MaterialAlertDialogBuilder(this)
            .setTitle("Registrarme mediante")
            .setAdapter(AdaptadorListaOpciones.getAdaptador(this,items)) { dialogInterface, index ->
                when(index){
                    0 -> {
                        startActivity(Intent(this,ActivityRegistro::class.java))
                    }
                }
            }
            .show()
    }
}