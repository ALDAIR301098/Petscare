package com.petscare.org.vista.adaptadores.recyclers

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.petscare.org.R
import com.petscare.org.domain.providers.TipoDispositivo
import com.petscare.org.modelo.objetos.Dispositivo

class AdaptadorDispositivos(private val context: Context, private val lista_dispositivos: List<Dispositivo>)
    : RecyclerView.Adapter<AdaptadorDispositivos.HolderDispositivos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDispositivos {
        return HolderDispositivos(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dispositivo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderDispositivos, position: Int) {
        holder.mostrarDatos(lista_dispositivos.get(position))
    }

    override fun getItemCount(): Int {
        return lista_dispositivos.size
    }

    inner class HolderDispositivos(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txt_nombre = itemView.findViewById<TextView>(R.id.txt_nombre_dispositivo)
        private val icon_dispositivo = itemView.findViewById<ImageView>(R.id.icon_dispositivo)
        private val txt_valor = itemView.findViewById<TextView>(R.id.txt_valor_dispositivo)

        fun mostrarDatos(dispositivo: Dispositivo) {

            txt_nombre.text = dispositivo.nombre

            var drawable: Drawable? = null

            when(dispositivo.tipo){
                TipoDispositivo.DISPENSADOR_AGUA.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_dispensador_agua)
                TipoDispositivo.DISPENSADOR_ALIMENTO.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_dispensador_alimento)
                TipoDispositivo.PUERTA.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_puerta_cerrada)
                TipoDispositivo.MAQUINA_PROPINAS.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_maquina_propinas)
                TipoDispositivo.SECADORA.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_secadora)
                TipoDispositivo.AIRE_ACONDICIONADO.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_aire_acondicionado)
                TipoDispositivo.FOCO.name -> drawable = ContextCompat.getDrawable(context,R.drawable.ic_foco)
            }


            if (dispositivo.conectado == true){
                if (dispositivo.valor_digital != null){ // Digital
                    if (dispositivo.valor_digital == true){
                        DrawableCompat.setTint(drawable!!, getColor(context,R.color.verde))
                        when(dispositivo.tipo){
                            TipoDispositivo.FOCO.name -> txt_valor.text = "Encendido"
                            TipoDispositivo.PUERTA.name -> txt_valor.text = "Abierta"
                            TipoDispositivo.MAQUINA_PROPINAS.name -> txt_valor.text = "$${dispositivo.valor_analogico} mxn"
                            TipoDispositivo.AIRE_ACONDICIONADO.name ->txt_valor.text = "${dispositivo.valor_analogico}° Centigrados"
                        }
                    } else{
                        DrawableCompat.setTint(drawable!!, getColor(context,R.color.rojo_pastel))
                        when(dispositivo.tipo){
                            TipoDispositivo.FOCO.name -> txt_valor.text = "Apagado"
                            TipoDispositivo.PUERTA.name -> txt_valor.text = "Cerrada"
                            TipoDispositivo.DISPENSADOR_AGUA.name -> txt_valor.text = "Apagado"
                        }
                    }
                } else{ // Analogico
                    when(dispositivo.valor_analogico!!.toInt()){
                        in 0..29 -> {
                            DrawableCompat.setTint(drawable!!,getColor(context,R.color.rojo_pastel))
                        }
                        in 30..69 -> {
                            DrawableCompat.setTint(drawable!!,getColor(context,R.color.naranja))
                        }
                        in 70..100 -> {
                            DrawableCompat.setTint(drawable!!,getColor(context,R.color.verde))
                        }
                    }
                }

            } else{
                DrawableCompat.setTint(drawable!!, getColor(context,R.color.gris))
                txt_valor.text = "Desconectado"
            }
            icon_dispositivo.background = drawable

        }
    }
}