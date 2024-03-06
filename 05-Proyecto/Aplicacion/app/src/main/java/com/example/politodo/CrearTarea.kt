package com.example.politodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CrearTarea : AppCompatActivity() {
    private lateinit var btnCreateTask: ImageView

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tarea)
        //Referencias
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        btnCreateTask = findViewById(R.id.btn_crear_tarea2)



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
        .setOnClickListener{
            irActividad(FReciclerAllTask::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}