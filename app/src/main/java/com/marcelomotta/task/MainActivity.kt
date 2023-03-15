package com.marcelomotta.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = Firebase.auth

        //auth = FirebaseAuth.getInstance()
        val btn_login = findViewById(R.id.login_google) as Button
        val btn_register = findViewById<Button>(R.id.register_google)

        btn_login.setOnClickListener {
            login()
        }
        btn_register.setOnClickListener{
            val intent = Intent(this, RegistroActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun login() {
        val email = findViewById<EditText>(R.id.et_email_login)
        val password = findViewById<EditText>(R.id.et_senha_login)

        if (email.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Carregando...")
        val dialog = builder.create()
        dialog.show()
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                dialog.dismiss()
                if (task.isSuccessful) {
                    //val user = auth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Não foi possivel logar, tente novamente, se a conta existir.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(
                    baseContext, "Não foi possivel logar. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}


