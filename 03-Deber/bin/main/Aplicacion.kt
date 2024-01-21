import java.util.*

data class Aplicacion(
    val nombre: String,
    var version: Int,
    var fechaInstalacion: Date,
    var requiereInternet: Boolean,
    var tamaño: Float
)