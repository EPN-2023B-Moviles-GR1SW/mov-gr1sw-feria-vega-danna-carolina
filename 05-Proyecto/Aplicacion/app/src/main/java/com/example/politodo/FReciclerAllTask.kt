package com.example.politodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FReciclerAllTask : AppCompatActivity() {
    private lateinit var btnAddTask: ImageView

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecicler_all_task)
        //referencia
        btnAddTask = findViewById(R.id.btn_crear_tarea)


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
        btnAddTask
            .setOnClickListener{
                irActividad(CrearTarea::class.java)
            }
    }


    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }



}