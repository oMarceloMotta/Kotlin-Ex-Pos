package com.marcelomotta.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAnalytics: FirebaseAnalytics;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = Firebase.auth
        FirebaseApp.initializeApp(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        //auth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this)

        val btn_login = findViewById(R.id.login_google) as Button
        val btn_register = findViewById<Button>(R.id.register_google)
        val btn_recover = findViewById<Button>(R.id.recuperar_senha)
        btn_login.setOnClickListener {
            login()
        }

        btn_recover.setOnClickListener{
            val intent = Intent(this, RecuperarSenhaActivity::class.java)
            this.startActivity(intent)
        }

        btn_register.setOnClickListener{
            val intent = Intent(this, RegistroActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun login() {
        val email = supportFragmentManager.findFragmentById(R.id.etEmail) as EmailInputFragment
        val password = supportFragmentManager.findFragmentById(R.id.etPassword) as SenhaFragment

        //email.text.toString().isEmpty() || password.text.toString().isEmpty()
        if (email.etEmail.text.toString().isEmpty()  || password.etPassword.text.toString().isEmpty()) {
            Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Carregando...")

        val dialog = builder.create()
        dialog.show()
        /*
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()
        */
        val inputEmail = email.etEmail.text.toString()
        val inputPassword = password.etPassword.text.toString()

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
                    baseContext, "Não foi possivel logar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}


