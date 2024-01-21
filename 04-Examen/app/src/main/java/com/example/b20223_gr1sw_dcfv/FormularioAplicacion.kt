package com.example.b20223_gr1sw_dcfv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class FormularioAplicacion : AppCompatActivity() {

    private var idAplicacion: Int = -1
    private var idSistemaOperativoActual: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aplicacion)
        var estudianteId = BaseDeDatos.obtenerUltimoIdAplicacion()

        //recuperar SO
        val idSistemaOperativo: Int = intent.getIntExtra("idSistemaOperativo", -1)
        mostrarSnackbar("idSistemaOperativo : " + idSistemaOperativo)
        println("Id del SO que se paso por parametro: ${idSistemaOperativo}")
        this.idSistemaOperativoActual = idSistemaOperativo

        //recuperar Aplicacion
        val idAplicacionRecuperada: Int = intent.getIntExtra("idAplicacion", -1)
        mostrarSnackbar("idAplicacion: $idAplicacionRecuperada")
        this.idAplicacion = idAplicacionRecuperada

        //obtenemos el valor de los componetes visuales
        val id = findViewById<EditText>(R.id.txt_id_aplicacion)
        val nombreAplicacion = findViewById<EditText>(R.id.txt_nombre_aplicacion)

        if (this.idAplicacion == -1) {
            println("No se le paso ningun id vamos a crear nueva aplicacion")
            println("Valor si es que NO se paso parametro de idAplicacion: ${this.idAplicacion}")
            id.setText("${estudianteId + 1}")
        } else {
            val aplicacionAEditar = BaseDeDatos.obtenerAplicacionPorId(this.idAplicacion)
            println("Si se le paso id vamos a editar la aplicacion")
            println("Valor si es que SI se paso parametro de idAplicacion: ${this.idAplicacion}")
//            id.setText("${this.idClase}")
            if (aplicacionAEditar != null) {
                // Puedes ajustar esto según la estructura de tu formulario
                id.setText(aplicacionAEditar.idAplicacion.toString())
                nombreAplicacion.setText(aplicacionAEditar.nombreAplicacion)
            } else {
                // Manejar el caso en el que no se encuentre la clase con el ID proporcionado
                println("No se encontró el SO con el ID proporcionado")
            }

        }
        val btn_guardar_aplicacion = findViewById<Button>(R.id.btn_guardar_aplicacion)
        btn_guardar_aplicacion
            .setOnClickListener {

                if (this.idAplicacion == -1) {
                    println("No se le paso ningun id vamos a crear nueva aplicacion")
                    var idAplicacion = id.text.toString().toInt()
                    var nombreAplicacion = nombreAplicacion.text.toString()

                    BaseDeDatos.crearNuevaAplicacion(
                        idAplicacion,
                        nombreAplicacion,
                        this.idSistemaOperativoActual
                    )
                    id.setText("")
                    irActividad(ListaSistemaOperativo::class.java)
                } else {
                    println("Valor si esq se paso parametro de idAplicacion: ${this.idAplicacion}")
                    println("Aqui logica si esq se paso el id de la aplicacion")
                    var idAplicacion = id.text.toString().toInt()
                    var nuevoNombreAplicacion = nombreAplicacion.text.toString()
                    BaseDeDatos.editarAplicacionPorId(
                        idAplicacion,
                        nuevoNombreAplicacion,
                        this.idSistemaOperativoActual
                    )
                    irActividad(ListaSistemaOperativo::class.java)
                }

            }
    }

    fun irActividadConParametroIdSistemaOperativo(sistemaOperativo: Class<*>, idSistemaOperativo: Int) {
        val intentExplicito = Intent(this, sistemaOperativo)
        intentExplicito.putExtra("idSistemaOperativo", idSistemaOperativo)
        startActivity(intentExplicito)
    }

    fun irActividad(
        sistemaOperativo: Class<*>

    ) {
        val intent = Intent(this, sistemaOperativo)
        startActivity(intent)
    }

    //Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_Form_Aplicacion), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}