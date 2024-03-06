package com.example.politodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class Settings : AppCompatActivity() {
    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_nav_search -> {

                    true
                }

                R.id.menu_nav_home-> {
                    irActividad(MainActivity::class.java)
                    true
                }

                R.id.menu_nav_settings-> {
                    //Ya estas aqui
                    true
                }
                else -> false
            }
        }



        btnLogout = findViewById(R.id.btn_logout)
        btnLogout
            .setOnClickListener {

                println("Usuario antes del deslogueo: ${auth.currentUser}")
                // Cerrar sesión del usuario actual
                FirebaseAuth.getInstance().signOut()
                println("se deslogueo de la cuenta ")
                println("Usuario DESPUES del deslogueo: ${auth.currentUser}")

                irActividad(Login::class.java)

            }

    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}