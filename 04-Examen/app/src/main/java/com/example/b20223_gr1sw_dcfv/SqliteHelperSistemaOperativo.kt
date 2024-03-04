<<<<<<< HEAD
package com.example.b20223_gr1sw_dcfv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperSistemaOperativo (contexto: Context?):
    SQLiteOpenHelper(
        contexto,
        "Sistema",
        null,
        1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaSistemaOperativo =
            """
                CREATE TABLE SistemaOperativo(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreSO TEXT
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaSistemaOperativo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearNuevoSistemaOperativo(nombreSO: String, descripcionSO: String): Long {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombreSO)
        }
        val resultadoGuardar = basedatosEscritura.insert("Sistema Operativo", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar
    }

    fun eliminarSistemaOperativo(idSistemaOperativo: Int): Int {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idSistemaOperativo.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("Sistema Operativo", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion
    }

    fun obtenerSistemaOperativoPorId(idSistemaOperativo: Int): SistemaOperativo? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM SistemaOperativo WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idSistemaOperativo.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var sistemaOperativo: SistemaOperativo? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            sistemaOperativo = SistemaOperativo(idSistemaOperativo, nombre)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sistemaOperativo
    }


    fun actualizarSistemaOperativoPorId(idSistemaOperativo: Int, nuevoNombre: String): Int {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nuevoNombre)
        }
        val parametrosConsultaActualizar = arrayOf(idSistemaOperativo.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "Sistema Operativo",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion
    }

    fun obtenerUltimoIdSistemaOperativo(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM SistemaOperativo ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        if (resultadoConsultaLectura.moveToFirst()) {
            ultimoId = resultadoConsultaLectura.getInt(0)
        } else {
            ultimoId = 0
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

class SqliteHelperSistemaOperativo (contexto: Context?):
    SQLiteOpenHelper(
        contexto,
        "Sistema",
        null,
        1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaSistemaOperativo =
            """
                CREATE TABLE SistemaOperativo(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreSO TEXT
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaSistemaOperativo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearNuevoSistemaOperativo(nombreSO: String, descripcionSO: String): Long {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues().apply {
            put("nombre", nombreSO)
        }
        val resultadoGuardar = basedatosEscritura.insert("Sistema Operativo", null, valoresAGuardar)
        basedatosEscritura.close()
        return resultadoGuardar
    }

    fun eliminarSistemaOperativo(idSistemaOperativo: Int): Int {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idSistemaOperativo.toString())
        val resultadoEliminacion =
            conexionEscritura.delete("Sistema Operativo", "id=?", parametrosConsultaDelete)
        conexionEscritura.close()
        return resultadoEliminacion
    }

    fun obtenerSistemaOperativoPorId(idSistemaOperativo: Int): SistemaOperativo? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM SistemaOperativo WHERE id =?"
        val parametrosConsultaLectura = arrayOf(idSistemaOperativo.toString())
        val resultadoConsultaLectura =
            baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        var sistemaOperativo: SistemaOperativo? = null
        if (resultadoConsultaLectura.moveToFirst()) {
            val nombre = resultadoConsultaLectura.getString(1)
            sistemaOperativo = SistemaOperativo(idSistemaOperativo, nombre)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sistemaOperativo
    }


    fun actualizarSistemaOperativoPorId(idSistemaOperativo: Int, nuevoNombre: String): Int {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nuevoNombre)
        }
        val parametrosConsultaActualizar = arrayOf(idSistemaOperativo.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "Sistema Operativo",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion
    }

    fun obtenerUltimoIdSistemaOperativo(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT id FROM SistemaOperativo ORDER BY id DESC LIMIT 1"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val ultimoId: Int
        if (resultadoConsultaLectura.moveToFirst()) {
            ultimoId = resultadoConsultaLectura.getInt(0)
        } else {
            ultimoId = 0
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ultimoId
    }

>>>>>>> 2eabfca93f0312060ab546d040023652f6fde9cd
}