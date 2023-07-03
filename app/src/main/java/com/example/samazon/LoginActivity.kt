package com.example.samazon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        registerListeners()

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }


    private fun init() {

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonRegister)
        forgotButton = findViewById(R.id.buttonForgot)


    }
    private fun registerListeners() {

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotButton.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        loginButton.setOnClickListener {

            val email = editEmail.text.toString()
            val password = editPassword.text.toString()


            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "შეავსეთ ცარიელი ველი!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "E-mail ან პაროლი არასწორია!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}
