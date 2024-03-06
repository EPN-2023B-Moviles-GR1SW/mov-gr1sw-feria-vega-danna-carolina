package com.example.politodo

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CrearTarea2 : AppCompatActivity() {
    private lateinit var btnCreateTask: ImageView
    private lateinit var txtTiutloTarea: EditText
    private lateinit var txtDescripcionTarea: EditText
    private lateinit var bottomNavigationView: BottomNavigationView
    val userID = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tarea2)


        //obtener nombre de la categoria que le pasamos en el intent
        val nombreCategoria = intent.getStringExtra("nombre_categoria")


        //Referecias
        btnCreateTask = findViewById(R.id.btn_crear_tarea2)

        txtTiutloTarea = findViewById(R.id.txt_titulo_tarea)
        txtDescripcionTarea = findViewById(R.id.txt_descripcion_tarea)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)





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
        btnCreateTask
            .setOnClickListener {

                // Obtener la referencia a la subcolección de categorías
                val categoriasRef = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userID!!)
                    .collection("categories")
                    .document(nombreCategoria!!) // Suponiendo que "nombreCategoria" ya tiene un valor no nulo

                // Obtener la referencia a la subcolección de tareas dentro de la categoría
                val tareasRef = categoriasRef.collection("tasks")

                // Crear un nuevo documento para la tarea
                val nuevaTarea = hashMapOf(
                    "titulo" to txtTiutloTarea.text.toString(),
                    "descripcion" to txtDescripcionTarea.text.toString()
                )

                // Añadir la nueva tarea al subcolección de tareas
                tareasRef.add(nuevaTarea)
                    .addOnSuccessListener { documentReference ->

                        Toast.makeText(
                            applicationContext,
                            "Tarea agregada con ID: ${documentReference.id}",
                            Toast.LENGTH_SHORT
                        ).show()

                        irActividadConParametroNombreCategoria(this,ListCategori::class.java,nombreCategoria)

                    // Tarea agregada exitosamente
                        // Puedes realizar cualquier acción adicional aquí si es necesario
                    }
                    .addOnFailureListener { e ->

                        Toast.makeText(
                            applicationContext,
                            "Error al agregar la tarea",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Ocurrió un error al agregar la tarea
                        // Puedes manejar el error de acuerdo a tus necesidades
                    }


                irActividad(FReciclerAllTask::class.java)//debe regresar a ala actividad anterior
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

