import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.FileReader
import java.io.BufferedReader
import java.text.SimpleDateFormat
import java.util.*


class CRUDManager(
    private val sistemaOperativoFilePath: String = "sistemas_operativos.txt",
    private val aplicacionFilePath: String = "aplicaciones.txt"
) {

    fun crearSistemaOperativo(sistema: SistemaOperativo) {
        // Guardar el sistema operativo en el archivo
        val file = File(sistemaOperativoFilePath)

        // Crear un nuevo archivo si no existe
        if (!file.exists()) {
            file.createNewFile()
        }

        // Escribir el nuevo sistema operativo al final del archivo
        BufferedWriter(FileWriter(file, true)).use { writer ->
            writer.appendln("${sistema.nombre},${sistema.version},${sistema.fechaLanzamiento}," +
                    "${sistema.es64Bits},${sistema.tamaño}")
        }
    }

    fun leerSistemaOperativo(nombre: String): SistemaOperativo? {
        val file = File(sistemaOperativoFilePath)

        if (!file.exists()) {
            println("El archivo de sistemas operativos no existe.")
            return null
        }

        BufferedReader(FileReader(file)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val sistema = line?.let { parsearSistemaOperativoDesdeLinea(it) }
                if (sistema?.nombre == nombre) {
                    return sistema
                }
            }
        }

        println("Sistema Operativo no encontrado.")
        return null
    }

    fun actualizarSistemaOperativo(sistemaActualizado: SistemaOperativo) {
        val file = File(sistemaOperativoFilePath)

        if (!file.exists()) {
            println("El archivo de sistemas operativos no existe.")
            return
        }

        val sistemasTemporales = mutableListOf<String>()

        file.forEachLine { line ->
            val sistema = parsearSistemaOperativoDesdeLinea(line)
            if (sistema?.nombre == sistemaActualizado.nombre) {
                // Actualizar la lista de aplicaciones en el sistema operativo
                sistema.aplicaciones = sistemaActualizado.aplicaciones
                // Reemplazar la línea con la información actualizada
                val lineaActualizada = "${sistemaActualizado.nombre}," +
                        "${sistemaActualizado.version}," +
                        "${SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).format(sistemaActualizado.fechaLanzamiento)}," +
                        "${sistemaActualizado.es64Bits}," +
                        "${sistemaActualizado.tamaño}"
                sistemasTemporales.add(lineaActualizada)
            } else {
                sistemasTemporales.add(line)
            }
        }

        BufferedWriter(FileWriter(file)).use { writer ->
            for (sistemaLinea in sistemasTemporales) {
                writer.write(sistemaLinea)
                writer.newLine()
            }
        }
    }

    fun eliminarSistemaOperativo(nombre: String) {
        val file = File(sistemaOperativoFilePath)

        if (!file.exists()) {
            println("El archivo de sistemas operativos no existe.")
            return
        }

        val sistemasTemporales = mutableListOf<String>()

        BufferedReader(FileReader(file)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val sistema = parsearSistemaOperativoDesdeLinea(line!!)
                if (sistema?.nombre != nombre) {
                    sistemasTemporales.add(line!!)
                }
            }
        }

        BufferedWriter(FileWriter(file)).use { writer ->
            for (sistemaLinea in sistemasTemporales) {
                writer.write(sistemaLinea)
                writer.newLine()
            }
        }

        println("Sistema Operativo eliminado exitosamente.")
    }

    fun crearAplicacion(nombreSistemaOperativo: String, aplicacion: Aplicacion) {
        // Verificar si el sistema operativo existe antes de agregar la aplicación
        val sistemaOperativoExistente = leerSistemaOperativo(nombreSistemaOperativo)
        if (sistemaOperativoExistente == null) {
            println("El sistema operativo \"$nombreSistemaOperativo\" no existe.")
            return
        }

        // Agregar la aplicación al archivo de aplicaciones
        BufferedWriter(FileWriter(aplicacionFilePath, true)).use { writer ->
            writer.appendln("${nombreSistemaOperativo},${aplicacion.nombre}," +
                    "${aplicacion.version},${aplicacion.fechaInstalacion}," +
                    "${aplicacion.requiereInternet},${aplicacion.tamaño}")
        }

        // Actualizar la lista de aplicaciones en el sistema operativo
        sistemaOperativoExistente.aplicaciones.add(aplicacion)

        // Actualizar el archivo de sistemas operativos con la nueva información
        actualizarSistemaOperativo(sistemaOperativoExistente)

        println("Aplicación agregada al Sistema Operativo exitosamente.")
    }


    fun leerAplicacion(nombreSistemaOperativo: String, nombreAplicacion: String): Aplicacion? {
        val file = File(aplicacionFilePath)

        if (!file.exists()) {
            println("El archivo de aplicaciones no existe.")
            return null
        }

        val aplicacionEncontrada: Aplicacion? = null

        for (line in file.readLines()) {
            val datos = line.split(",")
            if (datos.size == 6 && datos[0] == nombreSistemaOperativo && datos[1] == nombreAplicacion) {
                val version = datos[2]?.toIntOrNull() ?: 0
                val fechaInstalacion = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(datos[3])
                val requiereInternet = datos[4].toBoolean()
                val tamaño = datos[5].toFloatOrNull()

                if (fechaInstalacion != null && tamaño != null) {
                    return Aplicacion(nombreAplicacion, version, fechaInstalacion, requiereInternet, tamaño)
                }
            }
        }

        println("No se encontró la aplicación \"$nombreAplicacion\" en el sistema operativo \"$nombreSistemaOperativo\".")
        return aplicacionEncontrada
    }

    fun actualizarAplicacion(nombreSistemaOperativo: String, nombreAplicacion: String, nuevaAplicacion: Aplicacion) {
        val sistemaOperativoFile = File(sistemaOperativoFilePath)
        val aplicacionFile = File(aplicacionFilePath)

        if (!sistemaOperativoFile.exists()) {
            println("El archivo de sistemas operativos no existe.")
            return
        }

        if (!aplicacionFile.exists()) {
            println("El archivo de aplicaciones no existe.")
            return
        }

        // Actualizar la aplicación en el archivo de aplicaciones
        val aplicacionesTemporales = mutableListOf<String>()
        aplicacionFile.forEachLine { line ->
            val datos = line.split(",")
            if (datos.size == 6 && datos[0] == nombreSistemaOperativo && datos[1] == nombreAplicacion) {
                // Reemplazar la línea con la nueva información de la aplicación
                val lineaActualizada = "${nombreSistemaOperativo},${nuevaAplicacion.nombre}," +
                        "${nuevaAplicacion.version}," +
                        "${SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).format(nuevaAplicacion.fechaInstalacion)}," +
                        "${nuevaAplicacion.requiereInternet},${nuevaAplicacion.tamaño}"
                aplicacionesTemporales.add(lineaActualizada)
            } else {
                aplicacionesTemporales.add(line)
            }
        }

        BufferedWriter(FileWriter(aplicacionFile)).use { writer ->
            for (aplicacionLinea in aplicacionesTemporales) {
                writer.write(aplicacionLinea)
                writer.newLine()
            }
        }

        // Actualizar la lista de aplicaciones en el sistema operativo
        val sistemaOperativoExistente = leerSistemaOperativo(nombreSistemaOperativo)
        val aplicacionExistente = sistemaOperativoExistente?.aplicaciones?.find { it.nombre == nombreAplicacion }
        aplicacionExistente?.let {
            it.version = nuevaAplicacion.version
            it.fechaInstalacion = nuevaAplicacion.fechaInstalacion
            it.requiereInternet = nuevaAplicacion.requiereInternet
            it.tamaño = nuevaAplicacion.tamaño
        }

        // Actualizar el archivo de sistemas operativos con la nueva información
        if (sistemaOperativoExistente != null) {
            actualizarSistemaOperativo(sistemaOperativoExistente)
        }

        println("Aplicación \"$nombreAplicacion\" actualizada exitosamente en el Sistema Operativo \"$nombreSistemaOperativo\".")
    }

    fun eliminarAplicacion(nombreSistemaOperativo: String, nombreAplicacion: String) {
        val sistemaOperativoFile = File(sistemaOperativoFilePath)
        val aplicacionFile = File(aplicacionFilePath)

        if (!sistemaOperativoFile.exists()) {
            println("El archivo de sistemas operativos no existe.")
            return
        }

        if (!aplicacionFile.exists()) {
            println("El archivo de aplicaciones no existe.")
            return
        }

        // Eliminar la aplicación del archivo de aplicaciones
        val aplicacionesTemporales = mutableListOf<String>()
        aplicacionFile.forEachLine { line ->
            val datos = line.split(",")
            if (datos.size == 6 && datos[0] == nombreSistemaOperativo && datos[1] == nombreAplicacion) {
                // Omitir la línea que corresponde a la aplicación a eliminar
            } else {
                aplicacionesTemporales.add(line)
            }
        }

        BufferedWriter(FileWriter(aplicacionFile)).use { writer ->
            for (aplicacionLinea in aplicacionesTemporales) {
                writer.write(aplicacionLinea)
                writer.newLine()
            }
        }

        // Actualizar la lista de aplicaciones en el sistema operativo
        val sistemaOperativoExistente = leerSistemaOperativo(nombreSistemaOperativo)
        sistemaOperativoExistente?.aplicaciones?.removeIf { it.nombre == nombreAplicacion }

        // Actualizar el archivo de sistemas operativos con la nueva información
        if (sistemaOperativoExistente != null) {
            actualizarSistemaOperativo(sistemaOperativoExistente)
        }

        println("Aplicación \"$nombreAplicacion\" eliminada exitosamente del Sistema Operativo \"$nombreSistemaOperativo\".")
    }

    private fun leerAplicacionesPorSistema(nombreSistema: String): MutableList<Aplicacion> {
        val file = File(aplicacionFilePath)
        val aplicaciones = mutableListOf<Aplicacion>()

        if (!file.exists()) {
            println("El archivo de aplicaciones no existe.")
            return aplicaciones
        }

        file.forEachLine { line ->
            val datos = line.split(",")
            if (datos.size == 6 && datos[0] == nombreSistema) {
                val nombre = datos[1]
                val version = datos[2]?.toIntOrNull() ?: 0
                val fechaInstalacion = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(datos[3])
                val requiereInternet = datos[4].toBoolean()
                val tamaño = datos[5].toFloatOrNull()

                if (fechaInstalacion != null && tamaño != null) {
                    aplicaciones.add(Aplicacion(nombre, version, fechaInstalacion, requiereInternet, tamaño))
                }
            }
        }

        return aplicaciones
    }

    private fun parsearSistemaOperativoDesdeLinea(linea: String): SistemaOperativo? {
        val datos = linea.split(",")
        if (datos.size == 5) {
            val nombre = datos[0]
            val version = datos[1].toIntOrNull()
            val fechaLanzamiento = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(datos[2])
            val es64Bits = datos[3].toBoolean()
            val tamaño = datos[4].toFloatOrNull()

            if (version != null && fechaLanzamiento != null && tamaño != null) {
                val sistema = SistemaOperativo(nombre, version, fechaLanzamiento, es64Bits, tamaño)
                // Se asume que las aplicaciones se encuentran en el mismo archivo
                sistema.aplicaciones = leerAplicacionesPorSistema(nombre)
                return sistema
            }
        }
        return null
    }
}