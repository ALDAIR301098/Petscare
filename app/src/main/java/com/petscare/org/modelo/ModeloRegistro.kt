package com.petscare.org.modelo

data class ModeloRegistro(
    var frag_index: Int = 0,
    var frag_nombre_verificado : Boolean = false,
    var frag_edad_genero_verificado : Boolean = false,
    var frag_contrasena_verificado : Boolean = false,
    var nombre: String? = null,
    var apellidos: String? = null,
    var fecha_nacimiento : String? = null,
    var correo : String? = null,
    var genero : String? = null,
    var telefono : String? = null,
    var contrasena: String? = null,
    var r_contrasena : String? = null)
