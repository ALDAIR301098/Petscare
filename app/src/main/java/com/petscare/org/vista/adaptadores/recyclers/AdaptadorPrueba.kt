package com.petscare.org.vista.adaptadores.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.petscare.org.R
import com.petscare.org.modelo.objetos.Mascota

class AdaptadorPrueba(
    private val context: Context, query: Query
): FirestoreAdapter<AdaptadorPrueba.MascotasHolder>(query) {

     inner class MascotasHolder(
         itemView: View
     ): RecyclerView.ViewHolder(itemView) {

        private val img_foto: ImageView = itemView.findViewById(R.id.img_foto_mascota)
        private val txt_nombre: TextView = itemView.findViewById(R.id.txt_nombre)
        private val txt_tipo_mascota: TextView = itemView.findViewById(R.id.txt_tipo)
        private val txt_raza: TextView = itemView.findViewById(R.id.txt_raza)

        fun mostrarDatos(mascota: Mascota){
            Glide.with(context).load(mascota.foto).circleCrop().into(img_foto)
            txt_nombre.text = mascota.nombre
            txt_tipo_mascota.text = "Mascota: ".plus(mascota.tipo)
            txt_raza.text = "Raza: ".plus(mascota.raza)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasHolder {
        return MascotasHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_mascota,parent,false))
    }

    override fun onBindViewHolder(holder: MascotasHolder, position: Int) {
        val document = getItem(position)
            val foto = document?.getString("Foto")
            val nombre = document?.getString("Nombre")
            val tipo = document?.getString("Tipo")
            val raza = document?.getString("Raza")
        val mascota = Mascota(null, nombre,tipo,raza,null,null,foto)
        holder.mostrarDatos(mascota)
        }
    }
