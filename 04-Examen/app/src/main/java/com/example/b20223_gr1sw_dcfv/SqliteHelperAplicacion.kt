<<<<<<< HEAD
package com.example.b20223_gr1sw_dcfv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperAplicacion(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "Sistema",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaAplicacion =
            """
                CREATE TABLE APLICACION(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                idSistemaOperativo INTEGER,
                FOREIGN KEY (idSistemaOperativo) REFERENCES SistemaOperativo(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAplicacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearAplicacion(nombre: String, idSistemaOperativo: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("idSistemaOperativo", idSistemaOperativo)
        val resultadoGuardar = basedatosEscritura.insert("APLICACION", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun editarAplicacionPorId(
        idAplicacion: Int,
        nuevoNombre: String,
        nuevoIdSistemaOperativo: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nuevoNombre)
        valoresAActualizar.put("idSistemaOperativo", nuevoIdSistemaOperativo)
        val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "APLICACION",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun eliminarAplicacionPorId(idAplicacion: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idAplicacion.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("APLICACION", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun obtenerAplicacionPorId(idAplicacion: Int): Aplicacion? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idAplicacion.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var aplicacion: Aplicacion? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            val idSistemaOperativo = resultadoConsultaLectura.getInt(2)
            aplicacion = Aplicacion(idAplicacion, nombre, idSistemaOperativo)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return aplicacion
    }


    fun eliminarAplicacionPorIdSistemaOperativo(idSistemaOperativo: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idSistemaOperativo.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("APLICACION", "idClase=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarIdsAplicacionDespuesDeEliminar() {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        var nuevoId = 1
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                valoresAActualizar.put("id", nuevoId)
                val idAplicacion = resultadoConsultaLectura.getInt(0)
                val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
                baseDatosLectura.update(
                    "APLICACION",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
                nuevoId++
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun actualizarIdsSistemaOperativoAplicacion(idSistemaOperativoEliminado: Int) {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION WHERE idSistemaOperativo > $idSistemaOperativoEliminado"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val idAplicacion = resultadoConsultaLectura.getInt(0)
                val nuevoIdSistemaOperativo = resultadoConsultaLectura.getInt(2) - 1
                valoresAActualizar.put("idSistemaOperativo", nuevoIdSistemaOperativo)
                val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
                baseDatosLectura.update(
                    "APLICACION",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun obtenerUltimoIdAplicacion(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM APLICACION ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        ultimoId = if (resultadoConsultaLectura.moveToFirst()) {
            resultadoConsultaLectura.getInt(0)
        } else {
            0
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ultimoId
    }

=======
package com.example.b20223_gr1sw_dcfv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperAplicacion(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "Sistema",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaAplicacion =
            """
                CREATE TABLE APLICACION(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                idSistemaOperativo INTEGER,
                FOREIGN KEY (idSistemaOperativo) REFERENCES SistemaOperativo(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaAplicacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearAplicacion(nombre: String, idSistemaOperativo: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("idSistemaOperativo", idSistemaOperativo)
        val resultadoGuardar = basedatosEscritura.insert("APLICACION", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun editarAplicacionPorId(
        idAplicacion: Int,
        nuevoNombre: String,
        nuevoIdSistemaOperativo: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nuevoNombre)
        valoresAActualizar.put("idSistemaOperativo", nuevoIdSistemaOperativo)
        val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "APLICACION",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun eliminarAplicacionPorId(idAplicacion: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idAplicacion.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("APLICACION", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun obtenerAplicacionPorId(idAplicacion: Int): Aplicacion? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idAplicacion.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var aplicacion: Aplicacion? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            val idSistemaOperativo = resultadoConsultaLectura.getInt(2)
            aplicacion = Aplicacion(idAplicacion, nombre, idSistemaOperativo)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return aplicacion
    }


    fun eliminarAplicacionPorIdSistemaOperativo(idSistemaOperativo: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idSistemaOperativo.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("APLICACION", "idClase=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarIdsAplicacionDespuesDeEliminar() {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        var nuevoId = 1
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                valoresAActualizar.put("id", nuevoId)
                val idAplicacion = resultadoConsultaLectura.getInt(0)
                val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
                baseDatosLectura.update(
                    "APLICACION",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
                nuevoId++
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun actualizarIdsSistemaOperativoAplicacion(idSistemaOperativoEliminado: Int) {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM APLICACION WHERE idSistemaOperativo > $idSistemaOperativoEliminado"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val valoresAActualizar = ContentValues()
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val idAplicacion = resultadoConsultaLectura.getInt(0)
                val nuevoIdSistemaOperativo = resultadoConsultaLectura.getInt(2) - 1
                valoresAActualizar.put("idSistemaOperativo", nuevoIdSistemaOperativo)
                val parametrosConsultaActualizar = arrayOf(idAplicacion.toString())
                baseDatosLectura.update(
                    "APLICACION",
                    valoresAActualizar,
                    "id=?",
                    parametrosConsultaActualizar
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
    }


    fun obtenerUltimoIdAplicacion(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM APLICACION ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        ultimoId = if (resultadoConsultaLectura.moveToFirst()) {
            resultadoConsultaLectura.getInt(0)
        } else {
            0
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ultimoId
    }

>>>>>>> 2eabfca93f0312060ab546d040023652f6fde9cd
}