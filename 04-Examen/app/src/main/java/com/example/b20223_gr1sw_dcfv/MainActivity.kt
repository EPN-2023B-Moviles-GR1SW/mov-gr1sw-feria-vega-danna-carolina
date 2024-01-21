package com.example.b20223_gr1sw_dcfv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnListViewAplicaciones = findViewById<Button>(R.id.btn_lv_aplicaciones)
        btnListViewAplicaciones.setOnClickListener {
            // mostrarSnackbar("Redirigiendo a lista de Aplicaciones")
            irActividad(ListaAplicacion::class.java)
        }


        val btnListViewSistemasOperativos = findViewById<Button>(R.id.btn_lv_SO)
        btnListViewSistemasOperativos.setOnClickListener {
//            mostrarSnackbar("Redirigiendo a lista de Sistemas Operativos")
            irActividad(ListaSistemaOperativo::class.java)
        }
    }

    //mostrar mensajes
    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.activity_view_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
            .show()
    }

    //ir a la siguiente actividad
    fun irActividad(
        sistemaOperativo: Class<*>

    ) {
        val intent = Intent(this, sistemaOperativo)
        startActivity(intent)
    }
}