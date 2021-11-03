package com.petscare.org.modelo.objetos

data class Dispositivo(
    var nombre: String? = null,
    var tipo: String? = null,
    var conectado: Boolean? = false,
    var valor_analogico: Float? = null,
    var valor_digital: Boolean? = null
)
