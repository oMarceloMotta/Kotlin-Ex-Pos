package com.marcelomotta.task

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        supportActionBar?.setTitle(R.string.title_actionbar_registro)
        auth = Firebase.auth

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        val btnRegistrar = supportFragmentManager.findFragmentById(R.id.registrar_btn) as ButtonFragment
        btnRegistrar.setOnConfirmClickListener(object : ButtonFragment.OnConfirmClickListener {
            override fun onConfirmClicked(activityType: String) {
                onClickRegisterUser()
            }
        })

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun onClickRegisterUser(): View.OnClickListener {
        return View.OnClickListener {
            val etSenha = findViewById<EditText>(R.id.et_senha_registro)
            val etEmail = findViewById<EditText>(R.id.et_email_registro)
            val senhaText = etSenha.text.toString()
            val emailTxt = etEmail.text.toString()
            if(senhaText.isNullOrBlank() || emailTxt.isNullOrBlank()){
                Toast.makeText(this, "Preencha todos os campos!" , Toast.LENGTH_LONG).show()
            }else if(senhaText.toString().split(' ').size < 1){
                Toast.makeText(this, "Preencha seu nome completo!" , Toast.LENGTH_LONG).show()
            }else if (!isValidEmail(emailTxt.toString())){
                Toast.makeText(this, "E-mail não é valido" , Toast.LENGTH_LONG).show()
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Carregando...")
                val dialog = builder.create()
                dialog.show()
                auth.createUserWithEmailAndPassword(emailTxt, senhaText)
                    .addOnCompleteListener(this) { task ->
                        dialog.dismiss()
                        if (task.isSuccessful) {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            etSenha.setText("")
                            etEmail.setText("")
                        } else {
                            Toast.makeText(
                                baseContext, "Erro ao cadastrar.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro ao cadastrar ${it.localizedMessage}", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }

            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return pattern.matches(email)
    }

}