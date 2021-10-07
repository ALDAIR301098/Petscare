package com.petscare.org.modelo

data class ModeloDatosRegistro(
    var frag_index: Int = 0,
    var nombre: String = "",
    var apellidos: String = "",
    var fecha_nacimiento: String = "",
    var genero: String = "",
    var pais: String = "",
    var lada: String = "",
    var telefono: String = "",
    var correo: String = "",
    var contrasena: String = ""
)
