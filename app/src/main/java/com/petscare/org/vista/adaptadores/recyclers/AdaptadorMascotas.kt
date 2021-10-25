package com.petscare.org.vista.adaptadores.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petscare.org.R
import com.petscare.org.modelo.objetos.Mascota

class AdaptadorMascotas(private val context: Context): RecyclerView.Adapter<AdaptadorMascotas.HolderMascotas>() {

    private var lista_mascotas = mutableListOf<Mascota>()

    fun setListaMascotas(lista_mascotas : MutableList<Mascota>){
        this.lista_mascotas = lista_mascotas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMascotas {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mascota,parent,false)
        return HolderMascotas(view)
    }

    override fun onBindViewHolder(holder: HolderMascotas, position: Int) {
        val mascota = lista_mascotas[position]
        holder.mostrarDatos(mascota)
    }

    override fun getItemCount(): Int {
        return lista_mascotas.size
    }

    inner class HolderMascotas(vista_item: View): RecyclerView.ViewHolder(vista_item) {

        private val txt_nombre = vista_item.findViewById<TextView>(R.id.txt_nombre)
        private val txt_tipo = vista_item.findViewById<TextView>(R.id.txt_tipo)
        private val txt_raza = vista_item.findViewById<TextView>(R.id.txt_raza)
        private val img_foto = vista_item.findViewById<ImageButton>(R.id.img_foto)

        fun mostrarDatos(mascota: Mascota?) {
            txt_nombre.text = mascota?.nombre
            txt_tipo.text = mascota?.tipo
            txt_raza.text = mascota?.raza
            img_foto.setImageResource(0)
            Glide.with(context).load(mascota?.foto).into(img_foto)
        }

    }
}