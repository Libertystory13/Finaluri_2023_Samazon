package com.example.samazon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPassword1: EditText
    private lateinit var userName: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        init()
        registerListeners()


    }
    private fun init() {
        editTextEmail = findViewById(R.id.editEmail)
        editTextPassword = findViewById(R.id.editPassword)
        editTextPassword1 = findViewById(R.id.editPass)
        buttonRegister = findViewById(R.id.buttonRegister)
        userName = findViewById(R.id.userName)
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)
    }
    private fun registerListeners() {

        buttonRegister.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val password1 = editTextPassword1.text.toString()
            val userName = userName.text.toString()

            if (emailValidate(email) && passwordValidate(password, password1 ) && userNameValidate(userName)) {
                textView1.text = ""
                textView2.text = ""
                textView3.text = ""
                textView4.text = ""



                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userInfo = com.example.samazon.data.UserInfo(userName, email)
                            db.child(auth.currentUser?.uid!!).setValue(userInfo)
                            startActivity(Intent(this, LoginActivity::class.java))

                            finish()
                        }
                    }



            }
        }
    }

    private fun userNameValidate(userName: String): Boolean{
         if (userName.isEmpty()){
            textView4.text = "შეიყვანეთ სახელი!"
            return false
        }
        else if (userName.length > 10 || userName.length < 2){
            textView4.text = "სახელი უნდა შეიცავდეს 2-10 ასომდე"
            return false
        }
        else {
            return true
         }


    }

    private fun emailValidate(email: String): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textView1.text = ""
            true
        } else {
            textView1.text = "E-mail არასწორია"
            false
        }
    }

    private fun passwordValidate(password1: String, password2: String): Boolean {

        if (password1.length < 8) {
            textView2.text = "პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს!"
            return false
        } else if (password1 != password2) {
            textView3.text = "პაროლები ერთმანეთს არ ემთხვევა"
            return false
        }
        else return true
    }
}