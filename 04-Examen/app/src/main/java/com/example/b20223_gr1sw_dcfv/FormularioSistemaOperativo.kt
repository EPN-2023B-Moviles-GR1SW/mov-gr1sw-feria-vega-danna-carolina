package com.example.b20223_gr1sw_dcfv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class FormularioSistemaOperativo : AppCompatActivity() {
    private var idSistemaOperativo: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_sistema_operativo)
        var idSO = BaseDeDatos.obtenerUltimoIdSistemaOperativo()

        val extras = intent.extras
        if (extras != null && extras.containsKey("idSistemaOperativo")) {
            this.idSistemaOperativo = extras.getInt("idSistemaOperativo")
        }

        //obtenemos el valor de los componentes visuales
        val id = findViewById<EditText>(R.id.txt_id_SO)
        val nombreSistemaOperativo = findViewById<EditText>(R.id.txt_nombre_SO)

        if (this.idSistemaOperativo == -1){
            println("No se le paso ningun id vamos a crer nuevo SO")
            println("Valor si es que NO se paso parametro de idSistemaOperativo: ${this.idSistemaOperativo}")
            id.setText("${idSO + 1}")
        }else{
            val sistemaOperativoAEditar = BaseDeDatos.obtenerSistemaOperativoPorId(this.idSistemaOperativo)
            println("Si se le paso id vamos a editar nuevo SO")
            println("Valor si es que SI se paso parametro de idSistemaOperativo: ${this.idSistemaOperativo}")
//            id.setText("${this.idClase}")
            if (sistemaOperativoAEditar != null) {
                // Puedes ajustar esto según la estructura de tu formulario
                id.setText(sistemaOperativoAEditar.idSistemaOperativo.toString())
                nombreSistemaOperativo.setText(sistemaOperativoAEditar.nombreSistemaOperativo)
            } else {
                // Manejar el caso en el que no se encuentre la clase con el ID proporcionado
                println("No se encontró la clase con el ID proporcionado")
            }

        }
        //boton
        val botonGuardarSO = findViewById<Button>(R.id.btn_guardar_form_SO)
        botonGuardarSO
            .setOnClickListener {

                if (this.idSistemaOperativo == -1){
                    println("No se le paso ningun id vamos a crer un nuevo sistema operativo")
                    var idSistemaOperativo = id.text.toString().toInt()
                    var nombreSistemaOperativo = nombreSistemaOperativo.text.toString()

                    BaseDeDatos.crearNuevoSistemaOperativo(idSistemaOperativo, nombreSistemaOperativo)
                    id.setText("")
                    irActividad(ListaSistemaOperativo::class.java)
                }else{
                    println("Valor si esq se paso parametro de idSistemaOperativo: ${this.idSistemaOperativo}")
                    println("Aqui logica si esq se paso el id del SO a editar ")
                    var idSistemaOperativo = id.text.toString().toInt()
                    var nuevoNombreSistemaOperativo = nombreSistemaOperativo.text.toString()
                    BaseDeDatos.actualizarSistemaOperativoPorId(idSistemaOperativo, nuevoNombreSistemaOperativo)
                    irActividad(ListaSistemaOperativo::class.java)
                }


            }
    }
    fun editarSistemaOperativo(){

    }

    //Ir actividad
    fun irActividad(
        sistemaOperativo: Class<*>

    ) {
        val intent = Intent(this, sistemaOperativo)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametros(sistemaOperativo: Class<*>, posicionItem: Int) {
        val intentExplicito = Intent(this, sistemaOperativo)
        intentExplicito.putExtra("posicionItem", posicionItem)
        startActivity(intentExplicito)
    }

    //    Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_Form_SO), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}