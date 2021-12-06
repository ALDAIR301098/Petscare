package com.petscare.org.vista.adaptadores.recyclers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorIOT(private val context: Context) : RecyclerView.Adapter<AdaptadorIOT.HolderIOT>() {

    companion object{
        private val VIEW_TYPE_DISPENSADOR: Int = 1
        private val VIEW_TYPE_FOCO_INTELIGENTE: Int = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderIOT {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HolderIOT, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class HolderIOT(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}