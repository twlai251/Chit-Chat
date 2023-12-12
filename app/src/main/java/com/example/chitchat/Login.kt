package com.example.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail =  findViewById(R.id.edt_email)
        edtPassword =  findViewById(R.id.edt_password)
        btnLogin =  findViewById(R.id.btnLogin)
        btnSignUp =  findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            if(edtEmail.text.isNullOrEmpty()){
                Toast.makeText(this@Login, "Please enter an email", Toast.LENGTH_SHORT).show()
            }
            else if(edtPassword.text.isNullOrEmpty()){
                Toast.makeText(this@Login, "Please enter an password", Toast.LENGTH_SHORT).show()
            }
            else {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        // logic of login user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Logic for user login
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_SHORT).show()

                }
            }
    }
}