package com.petscare.org.modelo

data class ModeloDatosAuth(
    var frag_index: Int = 0,
    var lada: String= "+52",
    var telefono: String? = null,
    var tiempo_contador: Long = 15000,
    var is_codigo_enviado : Boolean = false,
    var id_verificacion_guardado: String? = null
)
