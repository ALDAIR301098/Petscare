package com.petscare.org.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petscare.org.modelo.ModeloDatosAuth

class ViewModelAuth : ViewModel() {

    val ldata_auth = MutableLiveData<ModeloDatosAuth>()

    init {
        ldata_auth.value = ModeloDatosAuth()
    }

    fun setIndex(index: Int){
        ldata_auth.value?.frag_index = index
    }

    fun setLada(lada: String){
        ldata_auth.value?.lada = lada
    }

    fun setTelefono(telefono: String){
        ldata_auth.value?.telefono = telefono
    }

    fun setTiempoContador(tiempo: Long){
        ldata_auth.value?.tiempo_contador = tiempo
    }

    fun setIsCodigoEnviado(value: Boolean){
        ldata_auth.value?.is_codigo_enviado = value
    }

    fun setIdVerificacionGuardado(id:String?){
        ldata_auth.value?.id_verificacion_guardado = id
    }

    fun getIndex(): Int?{
        return ldata_auth.value?.frag_index
    }

    fun getLada(): String?{
        return ldata_auth.value?.lada
    }

    fun getTelefono(): String? {
        return ldata_auth.value?.telefono
    }

    fun getTiempoContador(): Long{
        return ldata_auth.value!!.tiempo_contador
    }

    fun isCodigoEnviado(): Boolean?{
        return ldata_auth.value?.is_codigo_enviado
    }

    fun getCodigoVerificacionGuardado(): String?{
        return ldata_auth.value?.id_verificacion_guardado
    }
}