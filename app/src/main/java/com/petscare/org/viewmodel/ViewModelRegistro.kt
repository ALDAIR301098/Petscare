package com.petscare.org.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petscare.org.model.ModeloRegistro

class ViewModelRegistro: ViewModel() {
    var ldata_registro = MutableLiveData<ModeloRegistro>()

    init {
        ldata_registro.value = ModeloRegistro()
    }

    fun guardarDataRegistro(index:Int){
        ldata_registro.value?.frag_index = index
    }
}