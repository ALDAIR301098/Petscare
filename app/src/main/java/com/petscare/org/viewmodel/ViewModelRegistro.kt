package com.petscare.org.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petscare.org.modelo.ModeloDatosRegistro

class ViewModelRegistro : ViewModel() {
    val ldata_registro = MutableLiveData<ModeloDatosRegistro>()

    init {
        ldata_registro.value = ModeloDatosRegistro()
    }

    fun setIndex(index: Int) {
        ldata_registro.value?.frag_index = index
    }

    fun setNombre(nombre: String) {
        ldata_registro.value?.nombre = nombre
    }

    fun setApellidos(apellidos: String) {
        ldata_registro.value?.apellidos = apellidos
    }

    fun setFechaNacimiento(fecha_nacimiento: String) {
        ldata_registro.value?.fecha_nacimiento = fecha_nacimiento
    }

    fun setGenero(genero: String) {
        ldata_registro.value?.genero = genero
    }

    fun setPais(pais : String){
        ldata_registro.value?.pais = pais
    }

    fun setLada(lada : String){
        ldata_registro.value?.lada = lada
    }

    fun setTelefono(telefono : String){
        ldata_registro.value?.telefono = telefono
    }

    fun setCorreo(correo: String) {
        ldata_registro.value?.correo = correo
    }

    fun setContrasena(contrasena: String) {
        ldata_registro.value?.contrasena = contrasena
    }

    fun getIndex(): Int?{
        return ldata_registro.value?.frag_index
    }

    fun getNombre(): String? {
        return ldata_registro.value?.nombre
    }

    fun getApellidos(): String? {
        return ldata_registro.value?.apellidos
    }

    fun getFechaNacimiento(): String? {
        return ldata_registro.value?.fecha_nacimiento
    }

    fun getGenero(): String? {
        return ldata_registro.value?.genero
    }

    fun getPais(): String? {
        return ldata_registro.value?.pais
    }

    fun getLada(): String? {
        return ldata_registro.value?.lada
    }

    fun getTelefono(): String? {
        return ldata_registro.value?.telefono
    }

    fun getCorreo(): String? {
        return ldata_registro.value?.correo
    }

    fun getContrasena(): String? {
        return ldata_registro.value?.contrasena
    }

}