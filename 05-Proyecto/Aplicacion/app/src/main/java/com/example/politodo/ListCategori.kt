package com.example.politodo

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ListCategori : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var lblCategoria: TextView
    private lateinit var listaTareasCategoria: ListView
    private lateinit var btnCrearTarea: ImageView
    private lateinit var adaptador: ArrayAdapter<String>


    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_categori)


        //Referecia
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        btnCrearTarea = findViewById(R.id.btn_crear_tarea5)
        lblCategoria = findViewById(R.id.lbl_titulo_categoria_tareas)
        listaTareasCategoria = findViewById(R.id.lv_tareas_categoria)
        btnCrearTarea = findViewById(R.id.btn_crear_tarea5)

        // Obtener el nombre de la categoría del intent
        val nombreCategoria = intent.getStringExtra("nombre_categoria")
        val listaNombresTareas: ArrayList<String> = ArrayList()

        // Establecer el texto del TextView con el nombre de la categoría
        lblCategoria.text = nombreCategoria


        //Listener
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_nav_search -> {

                    true
                }

                R.id.menu_nav_home -> {
                    irActividad(MainActivity::class.java)
                    true
                }

                R.id.menu_nav_settings -> {
                    irActividad(Settings::class.java)
                    true
                }

                else -> false
            }
        }
        btnCrearTarea
            .setOnClickListener {
                irActividadConParametroNombreCategoria(
                    this,
                    CrearTarea2::class.java,
                    nombreCategoria
                )
            }

        //manejo del adaptador del listview
        if (userId != null && nombreCategoria != null) {
            // Referencia a la subcolección de tareas en la categoría específica del usuario
            val tareasRef = db.collection("users").document("${userId}")
                .collection("categories").document("${nombreCategoria}")
                .collection("tasks")

            tareasRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // Obtener el nombre de cada tarea
                        val nombreTarea = document.getString("nombre")
                        // Agregar el nombre de la tarea a la lista
                        nombreTarea?.let { listaNombresTareas.add(it) }
                    }

                    // Una vez que hemos obtenido todos los nombres, actualizamos la ListView
                    adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        listaNombresTareas
                    )
                    listaTareasCategoria.adapter = adaptador
                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    // Manejar el error de obtener las tareas según tus necesidades
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener las tareas de la categoría",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }





    }


    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun irActividadConParametroNombreCategoria(
        contexto: Context,
        claseDestino: Class<*>,
        nombreCategoria: String?
    ) {
        val intent = Intent(contexto, claseDestino)
        intent.putExtra("nombre_categoria", nombreCategoria)
        contexto.startActivity(intent)
    }
}