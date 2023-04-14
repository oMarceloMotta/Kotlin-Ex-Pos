package com.marcelomotta.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarSenhaActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        auth = Firebase.auth
        supportActionBar?.setTitle(R.string.recover_password)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        val btnRegistrar = supportFragmentManager.findFragmentById(R.id.recuperar_senha) as ButtonFragment
        btnRegistrar.setOnConfirmClickListener(object : ButtonFragment.OnConfirmClickListener {
            override fun onConfirmClicked(activityType: String) {
                resetPassword()
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun resetPassword(){
        val email = supportFragmentManager.findFragmentById(R.id.etEmail) as EmailInputFragment

        //email.text.toString().isEmpty()
        if (email.etEmail.text.toString().isEmpty()) {
            Toast.makeText(this, "Por favor preencha o campo e-mail para recuperar a senha", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Carregando...")
        val dialog = builder.create()
        dialog.show()
        auth.sendPasswordResetEmail(email.etEmail.text.toString())
            .addOnCompleteListener { task ->
                dialog.dismiss()
                if (task.isSuccessful) {
                    Toast.makeText(this, "O e-mail para recuperar a senha foi enviado", Toast.LENGTH_SHORT)
                        .show()
                }else{

                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}