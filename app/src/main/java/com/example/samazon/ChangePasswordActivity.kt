package com.example.samazon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {


    private lateinit var newPassword: EditText
    private lateinit var newPassword1: EditText
    private lateinit var buttonNewPass: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)



        init()
        registerListeners()
    }

    private fun init() {
        newPassword = findViewById(R.id.newPassword)
        newPassword1 = findViewById(R.id.newPassword1)
        buttonNewPass = findViewById(R.id.change)
    }

    private fun registerListeners() {
        buttonNewPass.setOnClickListener {

            val newPassword = newPassword.text.toString()
            val newPassword1 = newPassword1.text.toString()

            if (newPassword.isEmpty() || newPassword1.isEmpty()) {
                Toast.makeText(this, "შეავსეთ ცარიელი ველი!", Toast.LENGTH_SHORT).show()
            } else if (newPassword.length < 8) {
                Toast.makeText(
                    this,
                    "პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (newPassword != newPassword1) {
                Toast.makeText(this, "პაროლები არ ემთხვევა!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }


                    }

            }
        }
    }
}
