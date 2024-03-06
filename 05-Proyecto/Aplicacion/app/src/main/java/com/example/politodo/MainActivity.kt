package com.example.politodo


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var btnAddCategory: Button
    private lateinit var btnMyDay: Button
    private lateinit var btnAllTask: Button
    private lateinit var lblsaludo: TextView
    private lateinit var listaCategorias: ListView
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    var posicionItemSeleccionado = -1
    private lateinit var adaptador: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //referencia a los id's de los diferentes components

        btnAddCategory = findViewById(R.id.btn_add_category)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        lblsaludo = findViewById(R.id.lblBienvenida)
        btnMyDay = findViewById(R.id.btn_my_day)
        btnAllTask = findViewById(R.id.btn_all_task)
        listaCategorias = findViewById(R.id.lv_categories)

        //listener de bootnes
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_nav_search -> {
                    true
                }

                R.id.menu_nav_home -> {
                    Toast.makeText(
                        baseContext,
                        "Home",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                R.id.menu_nav_settings -> {
                    irActividad(Settings::class.java)
                    true
                }

                else -> false
            }
        }
        btnMyDay
            .setOnClickListener {
//                irActividad(MyDay::class.java)
            }
        btnAllTask
            .setOnClickListener {
                irActividad(FReciclerAllTask::class.java)
            }
        btnAddCategory
            .setOnClickListener {
                irActividad(CrearCategoria::class.java)
            }


        //manejo para el adaptador
        if (userId != null) {
            val userCategoriesCollection =
                db.collection("users").document(userId).collection("categories")

            userCategoriesCollection
                .get()
                .addOnSuccessListener { categories ->
                    val arreglo = ArrayList<String>()
                    for (category in categories) {
                        val categoryName = category.getString("nombre")
                        if (categoryName != null) {
                            arreglo.add(categoryName)
                        }
                    }

                    // Aquí tienes tu arreglo lleno con los nombres de las categorías
                    // Ahora, puedes establecer el adaptador para tu ListView
                    adaptador = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        arreglo
                    )
                    listaCategorias.adapter = adaptador
                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged()
                    registerForContextMenu(listaCategorias)
                }
                .addOnFailureListener { exception ->
                    // Manejar el error de obtener las categorías
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener las categorías: $exception",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            // El usuario no está autenticado, mostrar un mensaje de error
            Toast.makeText(applicationContext, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            //no hay usuario redirigiendo a vista login
            irActividad(Login::class.java)
        } else {
            //si hay usuario activo
            setBienvenida()
        }
    }

    fun setBienvenida() {
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
            val usersCollection = db.collection("users")
            usersCollection.document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Para cada documento en la colección, puedes acceder a sus campos
                        val name = document.getString("name")
                        lblsaludo.text = "Hola ${name}"
                    } else {
                        println("El documento no existe o está vacío")
                    }
                }
                .addOnFailureListener { exception ->
                    // Maneja cualquier error que ocurra durante la obtención de los datos
                    println("Error getting documents: $exception")
                }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llamamos a las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        //obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {

                Toast.makeText(
                    applicationContext,
                    "$posicionItemSeleccionado",
                    Toast.LENGTH_SHORT
                ).show()
                val categoryName = obtenerNombreCategoria(posicionItemSeleccionado)
                irActividadConParametroNombreCategoria(this, ListCategori::class.java,categoryName)

                return true
            }

            R.id.mi_eliminar -> {

                Toast.makeText(
                    applicationContext,
                    "$posicionItemSeleccionado",
                    Toast.LENGTH_SHORT
                ).show()

                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }

    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar categoria")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                Toast.makeText(
                    applicationContext,
                    "Eliminar aceptado",
                    Toast.LENGTH_SHORT
                ).show()

                val categoryName = obtenerNombreCategoria(posicionItemSeleccionado)
                if (categoryName != null) {
                    // Llama a la función para eliminar la categoría
                    eliminarCategoria(categoryName)
                }

            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    private fun obtenerNombreCategoria(posicion: Int): String? {
        val adapter = listaCategorias.adapter
        if (adapter is ArrayAdapter<*>) {
            return adapter.getItem(posicion) as String?
        }
        return null
    }

    // Función para eliminar la categoría con el nombre dado
    private fun eliminarCategoria(categoryName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val userCategoriesCollection =
                db.collection("users").document(userId).collection("categories")

            userCategoriesCollection.document(categoryName)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Categoría eliminada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    //notifica cambios al adaptador
                    adaptador.notifyDataSetChanged()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        "Error al eliminar la categoría: $exception",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(applicationContext, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
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
