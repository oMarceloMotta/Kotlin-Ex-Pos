package com.marcelomotta.task

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class SenhaFragment : Fragment() {
    private lateinit var textViewDifficultyValue: TextView
    lateinit var etPassword: EditText
    lateinit var text: Editable;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_senha, container, false)

        textViewDifficultyValue = view.findViewById<TextView>(R.id.statusPassword)
        etPassword = view.findViewById<EditText>(R.id.etPassword)

        etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    text = s
                }

                val password = text.toString()
                val difficulty = getPasswordDifficulty(password)
                textViewDifficultyValue.text = difficulty
            }
        })

        return view
    }

    fun getPasswordDifficulty(password: String): String {
        return when {
            password.isEmpty() -> resources.getString(R.string.sem_senha)
            password.length < 6 -> resources.getString(R.string.senha_muito_fraca)
            password.length < 8 -> resources.getString(R.string.senha_fraca)
            password.length < 10 -> resources.getString(R.string.senha_media)
            password.length < 12 -> resources.getString(R.string.senha_forte)
            else -> resources.getString(R.string.senha_muito_forte)
        }
    }

}