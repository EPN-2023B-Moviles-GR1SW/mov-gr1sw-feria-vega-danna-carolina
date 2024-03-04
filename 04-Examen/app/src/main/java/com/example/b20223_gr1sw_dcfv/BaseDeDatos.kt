package com.example.b20223_gr1sw_dcfv

import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BaseDeDatos{
    companion object {
        val arregloAplicaciones = arrayListOf<Aplicacion>()
        val arregloSistemasOperativos = arrayListOf<SistemaOperativo>()
        private val db = FirebaseFirestore.getInstance() // instancia de firebase firestorage

        init {
            //aplicaciones
            arregloAplicaciones.add(
                Aplicacion(
                    1, "Word", 1
                )
            )
            arregloAplicaciones.add(
                Aplicacion(
                    2, "Excel", 2
                )
            )
            arregloAplicaciones.add(
                Aplicacion(
                    3, "Notas", 3
                )
            )

            //SistemasOperativos
            arregloSistemasOperativos.add(
                SistemaOperativo(1, "Windows 10")
            )
            arregloSistemasOperativos.add(
                SistemaOperativo(2, "Windows 11")
            )
            arregloSistemasOperativos.add(
                SistemaOperativo(3, "Linux")
            )

        }

        //Funciones
        fun crearNuevaAplicacion(id: Int, nombre: String, idSistemaOperativo: Int) {
            val nuevaAplicacion = Aplicacion(id, nombre, idSistemaOperativo)
            arregloAplicaciones.add(nuevaAplicacion)

            // COLECCION DE *APLICACIONES* EN FIRESTORE
            val aplicacionesRef = db.collection("aplicaciones")

            // Agregar un nuevo documento con el ID proporcionado y los campos de la aplicacion
            aplicacionesRef.document(id.toString())
                .set(nuevaAplicacion)
                .addOnSuccessListener {
                    // Éxito al agregar la aplicacion
                    println("Aplicacion agregada correctamente a Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al agregar la aplicacion
                    println("Error al agregar la aplicacion a Firestore: $e")
                }

        }

        fun editarAplicacionPorId(
            idAplicacion: Int,
            nuevoNombre: String,
            nuevoIdSO: Int
        ) {
            // Referencia al documento de la aplicacion en Firestore
            val aplicacionRef = db.collection("aplicaciones").document(idAplicacion.toString())

            // Actualizar los campos de la aplicacion en Firestore
            aplicacionRef.update(
                mapOf(
                    "nombre" to nuevoNombre,
                    "idSistemaOperativo" to nuevoIdSO
                )
            )
                .addOnSuccessListener {
                    // Éxito al editar la aplicacion
                    println("Aplicacion con ID $idAplicacion editado correctamente en Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al editar la aplicacion
                    println("Error al editar la aplicacion con ID $idAplicacion en Firestore: $e")
                }
            val aplicacionAEditar = arregloAplicaciones.find { it.idAplicacion == idAplicacion }

            if (aplicacionAEditar != null) {
                aplicacionAEditar.nombreAplicacion = nuevoNombre
                aplicacionAEditar.idSistemaOperativo = nuevoIdSO
            }
        }

        fun eliminarAplicacionPorId(adaptador: ArrayAdapter<Aplicacion>, idAplicacion: Int) {
            // Referencia al documento de la aplicacion en Firestore
            val aplicacionRef = db.collection("aplicaciones").document(idAplicacion.toString())

            // Eliminar el documento
            aplicacionRef.delete()
                .addOnSuccessListener {
                    // Éxito al eliminar la aplicacion
                    println("Aplicacion eliminada correctamente de Firestore.")

                }
                .addOnFailureListener { e ->
                    // Error al eliminar la aplicacion
                    println("Error al eliminar la aplicacion de Firestore: $e")
                }
            val aplicacionAEliminar = arregloAplicaciones.find { it.idAplicacion == idAplicacion }

            if (aplicacionAEliminar != null) {
                arregloAplicaciones.remove(aplicacionAEliminar)
                actualizarIdAplicacionesDespuesDeEliminar()
                adaptador.notifyDataSetChanged()
            }
        }

        fun obtenerAplicacionPorId(idAplicacion: Int): Aplicacion? {
            return arregloAplicaciones.find { it.idAplicacion == idAplicacion }
        }

        fun eliminarAplicacionPorIdSO(idSistemaOperativo: Int) {
            // Consultar aplicaciones que pertenecen al Sistema Operativo con el ID proporcionado
            val aplicacionesRef = db.collection("aplicaciones")
                .whereEqualTo("isSistemaOperativo", idSistemaOperativo)

            // Ejecutar la consulta
            aplicacionesRef.get()
                .addOnSuccessListener { querySnapshot ->
                    // Por cada aplicacion encontrada, eliminar su documento
                    querySnapshot.documents.forEach { document ->
                        document.reference.delete()
                            .addOnSuccessListener {
                                // Éxito al eliminar la aplicacion
                                println("Aplicacion con ID ${document.id} eliminado correctamente de Firestore.")
                            }
                            .addOnFailureListener { e ->
                                // Error al eliminar la aplicacion
                                println("Error al eliminar la aplicacion con ID ${document.id} de Firestore: $e")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Error al ejecutar la consulta
                    println("Error al consultar aplicaciones en Firestore: $e")
                }
            // Filtrar la lista de aplicaciones para mantener solo aquellos que no pertenecen al SO con el ID proporcionado
            val aplicacionesFiltradas = arregloAplicaciones.filter { it.idSistemaOperativo != idSistemaOperativo }

            // Actualizar la lista de aplicaciones
            arregloAplicaciones.clear()
            arregloAplicaciones.addAll(aplicacionesFiltradas)
        }

        fun actualizarIdAplicacionesDespuesDeEliminar() {
            // Recorre el arreglo y asigna nuevos IDs en orden
            for (indice in arregloAplicaciones.indices) {
                arregloAplicaciones[indice].idAplicacion = indice + 1
            }
        }
        fun actualizarIdsSOAplicaciones(idSOEliminado: Int) {
            // Filtra los aplicaciones con idSistemaOperativo mayor al eliminado
            val aplicacionesAActualizar = arregloAplicaciones.filter { it.idSistemaOperativo > idSOEliminado }

            // Actualiza los idSistemaOperativo restando 1
            aplicacionesAActualizar.forEach { aplicacion ->
                aplicacion.idSistemaOperativo -= 1
            }
        }
        fun obtenerUltimoIdAplicacion(): Int {
            return if (arregloAplicaciones.isEmpty()) {
                // Si el arreglo de aplicaciones está vacío, devuelve 1 como el primer ID posible
                1
            } else {
                // Obtiene la ultima aplicacion en el arreglo y devuelve su ID + 1
                val ultimaAplicacion = arregloAplicaciones.last()
                ultimaAplicacion.idAplicacion
            }
        }


        //FUNCIONES - SISTEMA OPERATIVO
        fun crearNuevoSistemaOperativo(
            idSistemaOperativo: Int,
            nombre: String,
        ) {
            // Verificar que el ID de clase sea único
            db.collection("sistemasOperativos").document(idSistemaOperativo.toString()).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Si el documento con el ID del sistema operativo ya existe, imprimir un mensaje de error
                        println("Error: El ID del sistema operativo $idSistemaOperativo ya existe en la base de datos.")
                    } else {
                        // Si el ID del sistema operativo es único, crear el nuevo sistema operativo
                        val datosSistemaOperativo = hashMapOf(
                            "idSistemaOperativo" to idSistemaOperativo,
                            "nombre" to nombre
                        )

                        // Agregar la clase a la colección "clases" en Firestore
                        db.collection("sistemasOperativos").document(idSistemaOperativo.toString())
                            .set(datosSistemaOperativo)
                            .addOnSuccessListener {
                                // Éxito al crear el sistema operativo
                                println("Sistema Operativo con ID $idSistemaOperativo creado correctamente en Firestore.")
                            }
                            .addOnFailureListener { e ->
                                // Error al crear el sistema operativo
                                println("Error al crear el sistema operativo con ID $idSistemaOperativo en Firestore: $e")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Error al verificar la existencia del ID del sistema operativo
                    println("Error al verificar la existencia del ID del sistema operativo en Firestore: $e")
                }
            // Verificar que el ID sea único
            val idExistente = arregloSistemasOperativos.any { it.idSistemaOperativo == idSistemaOperativo }

            if (!idExistente) {
                // Crear nuevo Sistema Operativo
                val nuevoSO = SistemaOperativo(idSistemaOperativo, nombre)
                arregloSistemasOperativos.add(nuevoSO)

            } else {
                // Manejar la situación donde el ID no es único (puedes lanzar un error o tomar otra acción)
                // Por ahora, simplemente imprimir un mensaje
                println("Error: El ID generado ya existe en la base de datos.")
            }
        }

        fun eliminarSistemaOperativoPorId(adaptador: ArrayAdapter<SistemaOperativo>, idSistemaOperativo: Int) {
            val sistemaAEliminar = arregloSistemasOperativos.find { it.idSistemaOperativo == idSistemaOperativo }

            // Eliminar el sistema operativo de Firestore
            db.collection("sistemasOperativos").document(idSistemaOperativo.toString())
                .delete()
                .addOnSuccessListener {
                    // Éxito al eliminar el sistema operativo de Firestore
                    println("Sistema operativo con ID $idSistemaOperativo eliminado correctamente de Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al eliminar el sistema operativo de Firestore
                    println("Error al eliminar el sistema operativo con ID $idSistemaOperativo de Firestore: $e")
                }

            if (sistemaAEliminar != null) {
                arregloSistemasOperativos.remove(sistemaAEliminar)
                actualizarIdsDespuesDeEliminar()
                adaptador.notifyDataSetChanged()
                eliminarAplicacionPorIdSO(idSistemaOperativo)
                actualizarIdsSOAplicaciones(idSistemaOperativo)
                actualizarIdAplicacionesDespuesDeEliminar()

            }
        }

        fun actualizarIdsDespuesDeEliminar() {
            // Recorre el arreglo y asigna nuevos IDs en orden
            for (indice in arregloSistemasOperativos.indices) {
                arregloSistemasOperativos[indice].idSistemaOperativo = indice + 1
            }
        }

        fun obtenerUltimoIdSistemaOperativo(): Int {
            // Si el arreglo está vacío, devuelve null indicando que no hay sistemas operativos
            // Devuelve el último ID del arreglo
            return arregloSistemasOperativos.last().idSistemaOperativo
        }

        fun actualizarSistemaOperativoPorId(
            idSistemaOperativo: Int,
            nuevoNombre: String,
        ) {
            val sistemaAActualizar = arregloSistemasOperativos.find { it.idSistemaOperativo == idSistemaOperativo }

            // Referencia al documento del sistema operativo en Firestore
            val sistemaOperativoRef = db.collection("sistemasOperativos").document(idSistemaOperativo.toString())

            // Actualizar los datos del sistema operativo en Firestore
            sistemaOperativoRef.update(
                mapOf(
                    "nombre" to nuevoNombre,
                )
            )
                .addOnSuccessListener {
                    // Éxito al actualizar el sistema operativo
                    println("Sistema operativo con ID $idSistemaOperativo actualizado correctamente en Firestore.")
                }
                .addOnFailureListener { e ->
                    // Error al actualizar el sistema operativo
                    println("Error al actualizar el sistema operativo con ID $idSistemaOperativo en Firestore: $e")
                }

            if (sistemaAActualizar != null) {
                // Actualiza los atributos del sistema encontrado
                sistemaAActualizar.nombreSistemaOperativo = nuevoNombre
            }
        }

        fun obtenerSistemaOperativoPorId(idSistemaOperativo: Int): SistemaOperativo? {
            return arregloSistemasOperativos.find { it.idSistemaOperativo == idSistemaOperativo }
        }

    }
}