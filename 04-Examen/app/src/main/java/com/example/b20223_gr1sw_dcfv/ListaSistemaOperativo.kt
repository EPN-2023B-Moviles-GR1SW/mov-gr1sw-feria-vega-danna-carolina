package com.example.b20223_gr1sw_dcfv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListaSistemaOperativo : AppCompatActivity() {

    val arreglo = BaseDeDatos.arregloSistemasOperativos
    var posicionItemSeleccionado = -1
    var sistemaOperativoSeleccionado = -1

    private lateinit var adaptador: ArrayAdapter<SistemaOperativo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_sistema_operativo)
        val listView = findViewById<ListView>(R.id.lv_lista_SO)
        this.adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        //buttons
        val btn_anadir_SO = findViewById<Button>(R.id.btn_anadir_SO)
        btn_anadir_SO
            .setOnClickListener {
                mostrarSnackbar("Dirigiendo al formulario Sistema Operativo")
                irActividad(FormularioSistemaOperativo::class.java)
            }

        val btn_home_lista_SO = findViewById<Button>(R.id.btn_home_list_SO)
        btn_home_lista_SO
            .setOnClickListener{
                irActividad(MainActivity::class.java)
            }
    }

    //mostrar menu emergente en un elemento de la lista
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // Llama al método onCreateContextMenu de la clase base (superclase)
        super.onCreateContextMenu(menu, v, menuInfo)

        // Infla (crea) el menú desde un archivo XML de recursos (R.menu.menu) y lo muestra en el contexto
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_so, menu)

        // Obtiene información sobre el elemento de lista que se ha mantenido presionado para mostrar el menú
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Guarda la posición del elemento de lista seleccionado para su uso posterior
        posicionItemSeleccionado = posicion
    }

    //Acciones al seleccional un item del menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_ver_aplicaciones -> {

                mostrarSnackbar("${posicionItemSeleccionado}")
                irActividadConParametrosPosicionItem(
                    ListaAplicacion::class.java,
                    posicionItemSeleccionado
                )
                this.posicionItemSeleccionado = -1
                println("pase de este punto ")
                return true // Indica que se ha manejado la acción correctamente
            }

            R.id.mi_editar -> {
                val sistemaOperativoSeleccionado = arreglo[posicionItemSeleccionado]

                // Ahora puedes acceder a los datos de la clase seleccionada
                val idSistemaOperativo = sistemaOperativoSeleccionado.idSistemaOperativo
                val nombreSistemaOperativo = sistemaOperativoSeleccionado.nombreSistemaOperativo
                this.sistemaOperativoSeleccionado = idSistemaOperativo

                // Puedes imprimir o usar estos datos según tus necesidades
                println("ID del Sistema Operativo seleccionado: $idSistemaOperativo")
                println("Nombre del Sistema Operativo seleccionado: $nombreSistemaOperativo")


                irActividadConParametros(FormularioSistemaOperativo::class.java, this.sistemaOperativoSeleccionado)
                mostrarSnackbar("${this.sistemaOperativoSeleccionado}")
                return true // Indica que se ha manejado la acción correctamente
            }

            R.id.mi_eliminar -> {
                val sistemaOperativoSeleccionado = arreglo[posicionItemSeleccionado]
                val idSistemaOperativoSeleccionado = sistemaOperativoSeleccionado.idSistemaOperativo
                this.sistemaOperativoSeleccionado = idSistemaOperativoSeleccionado
                mostrarSnackbar("${posicionItemSeleccionado}")
                abrirDialogo()
                return true // Indica que se ha manejado la acción correctamente
            }

            else -> super.onContextItemSelected(item)
            // Si no se selecciona ninguna opción conocida, delega al comportamiento predeterminado
        }
    }

    //abrir dialogo de confirmaicon
    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")

        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            mostrarSnackbar("Elemento eliminado")
            BaseDeDatos.eliminarSistemaOperativoPorId(adaptador, sistemaOperativoSeleccionado)

        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            this.sistemaOperativoSeleccionado = -1
        }

        val dialogo = builder.create()
        dialogo.show()
    }

    //Ir actividad
    fun irActividad(
        sistemaOperativo: Class<*>

    ) {
        val intent = Intent(this, sistemaOperativo)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametros(sistemaOperativo: Class<*>, idSistemaOperativo: Int) {
        val intentExplicito = Intent(this, sistemaOperativo)
        intentExplicito.putExtra("idSistemaOperativo", idSistemaOperativo)
        startActivity(intentExplicito)
    }

    fun irActividadConParametrosPosicionItem(sistemaOperativo: Class<*>, posicionItem: Int) {
        val intentExplicito = Intent(this, sistemaOperativo)
        intentExplicito.putExtra("posicionItem", posicionItem)
        startActivity(intentExplicito)
    }


    //    Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.activity_view_lista_SO), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }


}
