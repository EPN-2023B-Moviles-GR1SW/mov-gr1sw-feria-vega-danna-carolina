package com.example.politodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CrearCategoria : AppCompatActivity() {
    private lateinit var btnCreateCategory: ImageView

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var tituloCategoria: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_categoria)

        //Referencia
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        btnCreateCategory = findViewById(R.id.btn_crear_categoria)
        tituloCategoria = findViewById(R.id.titulo_categoria)

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

        btnCreateCategory
            .setOnClickListener {
                val db = FirebaseFirestore.getInstance()
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                val tituloCategoria = findViewById<EditText>(R.id.titulo_categoria).text.toString()

                // Verificar que el campo del título de la categoría no esté vacío
                if (tituloCategoria.isNotEmpty()) {
                    // Verificar si el usuario está autenticado
                    if (userId != null) {
                        // Crear una nueva categoría en la colección de categorías del usuario
                        val userCategoriesCollection =
                            db.collection("users").document(userId).collection("categories")

                        //id del documento es el nombre que el usuario puso
                        val newCategoryDocument = userCategoriesCollection.document(tituloCategoria)

                        // Crear el documento de la categoría con el nombre y la subcolección de tareas
                        val categoriaData = hashMapOf(
                            "nombre" to tituloCategoria

                            // Agrega más campos si es necesario
                        )

                        newCategoryDocument.set(categoriaData)
                            .addOnSuccessListener {
                                // El documento de la categoría se creó exitosamente
                                // Ahora puedes mostrar un mensaje de éxito o realizar otras acciones
                                Toast.makeText(
                                    applicationContext,
                                    "Categoría creada exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //ya que salio bien redirigimos a donde se deberia ver las categorias
                                irActividad(MainActivity::class.java)

                            }
                            .addOnFailureListener { exception ->
                                // Ocurrió un error al crear el documento de la categoría
                                Toast.makeText(
                                    applicationContext,
                                    "Error al crear la categoría: $exception",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        // El usuario no está autenticado, muestra un mensaje de error
                        Toast.makeText(
                            applicationContext,
                            "Usuario no autenticado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // El campo del título de la categoría está vacío, muestra un mensaje de error
                    Toast.makeText(
                        applicationContext,
                        "El título de la categoría no puede estar vacío",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//                irActividad(MainActivity::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}