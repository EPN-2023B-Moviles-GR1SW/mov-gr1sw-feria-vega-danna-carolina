import java.util.*

data class SistemaOperativo(
    val nombre: String,
    val version: Int,
    val fechaLanzamiento: Date,
    val es64Bits: Boolean,
    val tamaño: Float,
    var aplicaciones: MutableList<Aplicacion> = mutableListOf()
)