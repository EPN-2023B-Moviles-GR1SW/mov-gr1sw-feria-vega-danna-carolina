import java.text.SimpleDateFormat

fun main(args: Array<String>) {
    println("Hello World!")

val crudManager = CRUDManager()

var opcion: Int
    do {
        println("---- Menú ----")
        println("1. Crear Sistema Operativo")
        println("2. Leer Sistema Operativo")
        println("3. Actualizar Sistema Operativo")
        println("4. Eliminar Sistema Operativo")
        println("5. Crear Aplicacion")
        println("6. Leer Aplicacion")
        println("7. Actualizar Aplicacion")
        println("8. Eliminar Aplicacion")
        println("9. Salir")
        print("Seleccione una opción: ")

        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                val sistema = leerDatosSistemaOperativo()
                crudManager.crearSistemaOperativo(sistema)
                println("Sistema Operativo creado exitosamente.")
            }
            2 -> {
                print("Ingrese el nombre del Sistema Operativo: ")
                val nombre = readLine() ?: ""
                val sistemaLeido = crudManager.leerSistemaOperativo(nombre)
                println(sistemaLeido)
            }
            3 -> {
                val sistema = leerDatosSistemaOperativo()
                crudManager.actualizarSistemaOperativo(sistema)
                println("Sistema Operativo actualizado exitosamente.")
            }
            4 -> {
                print("Ingrese el nombre del Sistema Operativo: ")
                val nombre = readLine() ?: ""
                crudManager.eliminarSistemaOperativo(nombre)
                println("Sistema Operativo eliminado exitosamente.")
            }
            5 -> {
                print("Ingrese el nombre del Sistema Operativo al que pertenece la aplicación: ")
                val nombreSistema = readLine() ?: ""
                val aplicacion = leerDatosAplicacion()
                crudManager.crearAplicacion(nombreSistema, aplicacion)
                println("Aplicación creada exitosamente.")
            }
            6 -> {
                print("Ingrese el nombre del Sistema Operativo al que pertenece la aplicación: ")
                val nombreSistema = readLine() ?: ""
                print("Ingrese el nombre de la aplicación: ")
                val nombreAplicacion = readLine() ?: ""
                val aplicacionLeida = crudManager.leerAplicacion(nombreSistema, nombreAplicacion)
                println(aplicacionLeida)
            }
            7 -> {
                print("Ingrese el nombre del Sistema Operativo al que pertenece la aplicación: ")
                val nombreSistema = readLine() ?: ""
                print("Ingrese el nombre de la aplicación: ")
                val nombreAplicacion = readLine() ?: ""
                val aplicacion = leerDatosAplicacion()
                val aplicacionActualizada =crudManager.actualizarAplicacion(nombreSistema, nombreAplicacion, aplicacion)
                println(aplicacionActualizada)
            }
            8 -> {
                print("Ingrese el nombre del Sistema Operativo al que pertenece la aplicación: ")
                val nombreSistema = readLine() ?: ""
                print("Ingrese el nombre de la aplicación: ")
                val nombreAplicacion = readLine() ?: ""
                crudManager.eliminarAplicacion(nombreSistema, nombreAplicacion)
                println("Aplicación eliminada exitosamente.")
            }
            9 -> println("Saliendo del programa.")
            else -> println("Opción no válida. Intente de nuevo.")
        }

    } while (opcion != 9)
}

fun leerDatosSistemaOperativo(): SistemaOperativo {
    print("Nombre del Sistema Operativo: ")
    val nombre = readLine() ?: ""
    print("Versión del Sistema Operativo: ")
    val version = readLine()?.toIntOrNull() ?: 0
    print("Fecha de Lanzamiento del Sistema Operativo (Formato: dd/MM/yyyy): ")
    val fechaStr = readLine() ?: ""
    val fechaLanzamiento = SimpleDateFormat("dd/MM/yyyy").parse(fechaStr)
    print("¿Es 64 bits? (true/false): ")
    val es64Bits = readLine()?.toBoolean() ?: false
    print("Tamaño del Sistema Operativo: ")
    val tamaño = readLine()?.toFloatOrNull() ?: 0.0f

    return SistemaOperativo(nombre, version, fechaLanzamiento, es64Bits, tamaño)
}

fun leerDatosAplicacion(): Aplicacion {
    print("Nombre de la Aplicación: ")
    val nombre = readLine() ?: ""
    print("Versión de la Aplicación: ")
    val version = readLine()?.toIntOrNull() ?: 0
    print("Fecha de Instalación de la Aplicación (Formato: dd/MM/yyyy): ")
    val fechaInstalacionStr = readLine() ?: ""
    val fechaInstalacion = SimpleDateFormat("dd/MM/yyyy").parse(fechaInstalacionStr)
    print("¿Requiere Internet? (true/false): ")
    val requiereInternet = readLine()?.toBoolean() ?: false
    print("Tamaño de la Aplicación: ")
    val tamaño = readLine()?.toFloatOrNull() ?: 0.0f

    return Aplicacion(nombre, version, fechaInstalacion, requiereInternet, tamaño)
}