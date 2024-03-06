package com.example.politodo

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var txtUserLogin: EditText
    private lateinit var txtPasswordUser: EditText
    private lateinit var btnLogin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var btnNewUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Referenciar los EditText y el Button desde el layout
        txtUserLogin = findViewById(R.id.txtUserLogin)
        txtPasswordUser = findViewById(R.id.txtPasswordUser)
        btnLogin = findViewById(R.id.btnLogin)
        btnNewUser = findViewById(R.id.btn_new_user)
        btnNewUser
            .setOnClickListener {
                irActividad(SignUp::class.java)

            }

        //listener del boton
        btnLogin.setOnClickListener {
            val email = txtUserLogin.text.toString()
            val password = txtPasswordUser.text.toString()

            println("USer: ${email} \nand \nPassword: ${password}")

            if (email.isNotEmpty() && password.isNotEmpty()) {
                println(" Entro al IF : USer: ${email} \nand \nPassword: ${password}")
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Inicio de sesión exitoso, actualizar la interfaz de usuario con la información del usuario registrado
                            val user = auth.currentUser
                            print(user)
                            // Aquí puedes llamar a una función para actualizar la interfaz de usuario (UI) con la información del usuario
                            irActividad(MainActivity::class.java)
                        } else {
                            // Ocurrió un error durante el inicio de sesión, mostrar un mensaje de error al usuario
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Aquí puedes llamar a una función para actualizar la interfaz de usuario (UI) en caso de falla de inicio de sesión
                            println("no  se pudo loguear ")
                        }
                    }
            } else {
                // Mostrar un mensaje de error si el correo electrónico o la contraseña están vacíos
                Toast.makeText(
                    this, "Please enter email and password.",
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
}