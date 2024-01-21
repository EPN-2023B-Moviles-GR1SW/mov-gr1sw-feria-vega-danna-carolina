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

class ListaAplicacion : AppCompatActivity() {

    val arregloAplicacion = BaseDeDatos.arregloAplicaciones
    val arregloSistemasOperativos = BaseDeDatos.arregloSistemasOperativos
    var posicionItemSeleccionado =-1
    var aplicacionSeleccionada = -1

    private lateinit var adaptador : ArrayAdapter<Aplicacion>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_aplicacion)

        val btn_anadir_aplicacion = findViewById<Button>(R.id.btn_anadir_aplicacion)
        val posicionItem: Int = intent.getIntExtra("posicionItem",-1)
        mostrarSnackbar("posicion item: " +posicionItem)
        this.posicionItemSeleccionado = posicionItem

        val listView = findViewById<ListView>(R.id.lv_lista_aplicaciones)

        adaptador = ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1, //como se va a ver XML
            if (posicionItem != -1) {
                // Filtrar aplicaciones por idSistemaOperativo si posicionItem no es -1
                val aplicacionSeleccionada = arregloAplicacion[posicionItem]
                val idSistemaOperativo = aplicacionSeleccionada.idSistemaOperativo
                btn_anadir_aplicacion.isEnabled = true
                btn_anadir_aplicacion.visibility =
                    if (btn_anadir_aplicacion.isEnabled) View.VISIBLE else View.GONE
                BaseDeDatos.arregloAplicaciones.filter { it.idSistemaOperativo == idSistemaOperativo }
            } else {
                // Mostrar todos las aplicaciones si posicionItem es -1
                btn_anadir_aplicacion.isEnabled = false
                btn_anadir_aplicacion.visibility =
                    if (btn_anadir_aplicacion.isEnabled) View.VISIBLE else View.GONE
                arregloAplicacion
            }
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        //buttons action
        btn_anadir_aplicacion
            .setOnClickListener {
                mostrarSnackbar("Dirigiendo al formulario de aplicaciones")
                val claseSeleccionada = arregloSistemasOperativos[this.posicionItemSeleccionado]
                val idClase = claseSeleccionada.idSistemaOperativo
                irActividadConParametroIdSO(FormularioAplicacion::class.java, idClase)
            }
        val btn_home_lista_aplicaciones = findViewById<Button>(R.id.btn_home_list_aplicaciones)
        btn_home_lista_aplicaciones
            .setOnClickListener {
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
        inflater.inflate(R.menu.menu_aplicacion, menu)

        // Obtiene información sobre el elemento de lista que se ha mantenido presionado para mostrar el menú
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        // Guarda la posición del elemento de lista seleccionado para su uso posterior
        posicionItemSeleccionado = posicion
    }

    //Acciones al seleccional un item del menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                val aplicacionSeleccionada = adaptador.getItem(posicionItemSeleccionado)

                //ahora podemos acceder a los datos de ese estudiante
                val idAplicacion = aplicacionSeleccionada?.idAplicacion
                val nombreAplicacion = aplicacionSeleccionada?.nombreAplicacion
                val idSistemaOperativo = aplicacionSeleccionada?.idSistemaOperativo
                if (idAplicacion != null) {
                    this.aplicacionSeleccionada = idAplicacion
                    // Puedes imprimir o usar estos datos según tus necesidades
                    println("ID de la aplicacion seleccionada: $idAplicacion")
                    println("Nombre de la aplicacion seleccionada: $nombreAplicacion")
                    println("ID del Sistema Operativo que pertenece: $idSistemaOperativo")

                    if (idSistemaOperativo != null) {
                        irActividadConParametroIdAplicacion(
                            FormularioAplicacion::class.java,
                            this.aplicacionSeleccionada,
                            idSistemaOperativo
                        )
                    }
                    mostrarSnackbar("${this.aplicacionSeleccionada}")
                    return true // Indica que se ha manejado la acción correctamente
                } else {
                    // Manejar el caso en el que la aplicacion seleccionado sea nulo
                    return false
                }
            }

            R.id.mi_eliminar -> {
                val aplicacionSeleccionada = arregloAplicacion[posicionItemSeleccionado]
                val idAplicacion = aplicacionSeleccionada.idAplicacion
                this.aplicacionSeleccionada = idAplicacion
                mostrarSnackbar("${posicionItemSeleccionado}")
                // Abre un diálogo (supuestamente) para confirmar la eliminación del elemento
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
            BaseDeDatos.eliminarAplicacionPorId(adaptador, aplicacionSeleccionada)
        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            this.aplicacionSeleccionada = -1
        }

        val dialogo = builder.create()
        dialogo.show()
    }

    //ir actividad
    fun irActividad(
        sistemaOperativo: Class<*>

    ) {
        val intent = Intent(this, sistemaOperativo)
        startActivity(intent)
    }

    //Ir actividad con parametros
    fun irActividadConParametroIdAplicacion(aplicacion: Class<*>, idAplicacion: Int, idSistemaOperativo: Int) {
        val intentExplicito = Intent(this, aplicacion)
        intentExplicito.putExtra("idAplicacion", idAplicacion)
        intentExplicito.putExtra("idSistemaOperativo", idSistemaOperativo)
        startActivity(intentExplicito)
    }

    fun irActividadConParametroIdSO(sistemaOperativo: Class<*>, idSistemaOperativo: Int) {
        val intentExplicito = Intent(this, sistemaOperativo)
        intentExplicito.putExtra("idSistemaOperativo", idSistemaOperativo)
        startActivity(intentExplicito)
    }

    //Mensajes en snackbar
    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.activity_view_lista_aplicaciones), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show() //muestra el snackbar
    }
}