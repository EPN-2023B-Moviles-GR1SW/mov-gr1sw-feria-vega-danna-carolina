package com.example.b20223_gr1sw_dcfv

class SistemaOperativo(
    var idSistemaOperativo : Int,
    var nombreSistemaOperativo: String?,
    var aplicaciones: ArrayList<Aplicacion> = arrayListOf()
){
    override fun toString(): String {
        return "ID Sistema Operativo: ${idSistemaOperativo} \n" +
                "${nombreSistemaOperativo}"
    }
}
