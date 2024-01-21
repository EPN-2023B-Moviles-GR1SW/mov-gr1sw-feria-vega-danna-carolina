package com.example.b20223_gr1sw_dcfv

import android.widget.ArrayAdapter

class BaseDeDatos{
    companion object {
        val arregloAplicaciones = arrayListOf<Aplicacion>()
        val arregloSistemasOperativos = arrayListOf<SistemaOperativo>()

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
        }

        fun editarAplicacionPorId(
            idAplicacion: Int,
            nuevoNombre: String,
            nuevoIdSO: Int
        ) {
            val aplicacionAEditar = arregloAplicaciones.find { it.idAplicacion == idAplicacion }

            if (aplicacionAEditar != null) {
                aplicacionAEditar.nombreAplicacion = nuevoNombre
                aplicacionAEditar.idSistemaOperativo = nuevoIdSO
            }
        }

        fun eliminarAplicacionPorId(adaptador: ArrayAdapter<Aplicacion>, idAplicacion: Int) {
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