package com.example.b20223_gr1sw_dcfv

class Aplicacion(
    var idAplicacion: Int,
    var nombreAplicacion: String?,
    var idSistemaOperativo: Int // Añadido para representar la relación 1 a n con el Sistema Operativo

) {
    override fun toString(): String {
        return "Id: ${idAplicacion} | \t${nombreAplicacion}\t | Sistema Operativo: ${idSistemaOperativo}"
    }
}