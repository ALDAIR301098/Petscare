package com.petscare.org.domain.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.petscare.org.modelo.objetos.Mascota

class FirestoreData {
    fun getDataMascotas(): LiveData<MutableList<Mascota>>{
        val mutable_ldata = MutableLiveData<MutableList<Mascota>>()
        val firebase_user = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance().collection("Usuarios").document(firebase_user!!)
            .collection("Mascotas").get().addOnSuccessListener { result ->
                val list_data = mutableListOf<Mascota>()
                for (document in result){
                    val id = firebase_user
                    val nombre = document.getString("Nombre")
                    val tipo = document.getString("Tipo")
                    val raza = document.getString("Raza")
                    val edad = document.getString("Edad")
                    val color = document.getString("Color")
                    val foto = document.getString("Foto")
                    val mascota = Mascota(id,nombre,tipo,raza,edad,color,foto)
                    list_data.add(mascota)
                }
                mutable_ldata.value = list_data
            }
        return mutable_ldata
    }
}