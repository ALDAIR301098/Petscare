package com.petscare.org.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petscare.org.domain.data.FirestoreData
import com.petscare.org.modelo.dataui.DataUIRMascota
import com.petscare.org.modelo.objetos.Mascota

class ViewModelMascota: ViewModel() {

    private val firestore_data = FirestoreData()

    private val ldata_mascotas = MutableLiveData<DataUIRMascota>()

    val ldata_edad = MutableLiveData<Int>()

    init {
        ldata_mascotas.value = DataUIRMascota()
        ldata_edad.value = 0
    }

    fun data(): DataUIRMascota {
        return ldata_mascotas.value!!
    }

    fun liveData(): LiveData<DataUIRMascota>{
        return ldata_mascotas
    }

    fun getListaMascotas(): LiveData<MutableList<Mascota>>{
        val mutable_ldata = MutableLiveData<MutableList<Mascota>>()
        firestore_data.getDataMascotas().observeForever{ lista_mascotas ->
            mutable_ldata.value = lista_mascotas
        }
        return mutable_ldata
    }
}