package com.petscare.org.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petscare.org.modelo.ModeloRegistro

class ViewModelRegistro: ViewModel() {
    var ldata_registro = MutableLiveData<ModeloRegistro>()

    init {
        ldata_registro.value = ModeloRegistro()
    }

    fun establecerIndex(index:Int){
        ldata_registro.value?.frag_index = index
    }

    fun establecerNombre(nombre : String, apellidos : String){
        ldata_registro.value?.nombre = nombre
        ldata_registro.value?.apellidos = apellidos
    }

    fun establecerInfoBasica(fecha_cumpleanos : String, correo : String, genero : String){
        ldata_registro.value?.fecha_nacimiento = fecha_cumpleanos
        ldata_registro.value?.correo = correo
        ldata_registro.value?.genero = genero
    }

    fun establecerTelefono(telefono:String){
        ldata_registro.value?.telefono = telefono
    }

    fun establecerContrasena(contrasena : String, r_contrasena : String){
        ldata_registro.value?.contrasena = contrasena
        ldata_registro.value?.r_contrasena = r_contrasena
    }
}