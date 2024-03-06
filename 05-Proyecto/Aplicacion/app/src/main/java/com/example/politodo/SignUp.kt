package com.example.politodo

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var lastname: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var passw: EditText
    private lateinit var btnUserExist: Button
    private lateinit var btnRegisterUser: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Referenciar los EditText y el Button desde el layout
        name = findViewById(R.id.txtNameSignup)
        lastname = findViewById(R.id.txtLastnameSignup)
        username = findViewById(R.id.txtUsernameSignup)
        email = findViewById(R.id.txtEmailSignup)
        passw = findViewById(R.id.txtPasswSignup)
        btnUserExist = findViewById(R.id.btn_user_exist)
        btnRegisterUser = findViewById(R.id.btn_register)


        // Agregar un OnClickListener al botón para registrar un nuevo usuario
        btnRegisterUser
            .setOnClickListener {
                //valores del usuaario
                val userEmail = email.text.toString()
                val userPassw = passw.text.toString()
                val userName = name.text.toString()
                val userLastname = lastname.text.toString()
                val userUsername = username.text.toString()

                // Crear un nuevo usuario con Firebase Authentication
                auth.createUserWithEmailAndPassword(userEmail, userPassw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso, obtén el UID del usuario registrado
                            val user = auth.currentUser
                            val userId = user?.uid

                            // Guardar los datos del usuario en Firestore
                            userId?.let {
                                val userRef =
                                    FirebaseFirestore
                                        .getInstance()
                                        .collection("users")
                                        .document(it)

                                // Crear un mapa con los datos del usuario
                                val userData = hashMapOf(
                                    "name" to userName,
                                    "lastname" to userLastname,
                                    "username" to userUsername,
                                    "email" to userEmail
                                )

                                // Guardar los datos del usuario en Firestore
                                userRef.set(userData)
                                    .addOnSuccessListener {
                                        Log.d(
                                            TAG,
                                            "Datos del usuario guardados correctamente en Firestore"
                                        )
                                        // Aquí puedes llamar a una función para manejar el éxito del registro
                                        // updateUI(user)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(
                                            TAG,
                                            "Error al guardar datos del usuario en Firestore",
                                            e
                                        )
                                        // Aquí puedes llamar a una función para manejar el fallo del registro
                                        // updateUI(null)
                                    }
                            }
                        } else {
                            // Si falla el registro, muestra un mensaje de error al usuario
                            Toast.makeText(
                                baseContext,
                                "Fallo en la creacion de cuenta failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Aquí puedes llamar a una función para manejar el fallo del registro
                            // updateUI(null)
                        }
                    }
            }

        // Agregar un OnClickListener al botón "btnUserExist" si es necesario
        btnUserExist.setOnClickListener {
            // Aquí puedes implementar la lógica para verificar si el usuario ya existe
        }









        btnUserExist
            .setOnClickListener {
                irActividad(Login::class.java)

            }

    }



    fun abrirLogin() {
        irActividad(Login::class.java)
    }

    fun irActividad(
        clase: Class<*>

    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}