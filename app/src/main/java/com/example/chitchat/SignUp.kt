package com.example.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtFirstName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        // Device Back Button
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@SignUp, Login::class.java)
                finish()
                startActivity(intent)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        mAuth = FirebaseAuth.getInstance()

        edtEmail =  findViewById(R.id.edt_email)
        edtFirstName =  findViewById(R.id.edt_first_name)
        edtLastName =  findViewById(R.id.edt_last_name)
        edtPassword =  findViewById(R.id.edt_password)
        btnSignUp =  findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val firstName = edtFirstName.text.toString()
            val lastName = edtLastName.text.toString()
            val email = edtEmail.text.toString()
            val password =  edtPassword.text.toString()

            if(edtFirstName.text.isNullOrEmpty()){
                Toast.makeText(this@SignUp, "Please enter a first name", Toast.LENGTH_SHORT).show()
            }
            else if(edtLastName.text.isNullOrEmpty()){
                Toast.makeText(this@SignUp, "Please enter a last name", Toast.LENGTH_SHORT).show()
            }
            else if(edtEmail.text.isNullOrEmpty()){
                Toast.makeText(this@SignUp, "Please enter an email", Toast.LENGTH_SHORT).show()
            }
            else if(edtPassword.text.isNullOrEmpty()){
                Toast.makeText(this@SignUp, "Please enter an password", Toast.LENGTH_SHORT).show()
            }
            else {
                signUp(firstName, lastName, email, password)
            }
        }
    }

    private fun signUp(fname: String, lname: String, email: String, password: String) {
        // logic of account creation
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val fName = fname.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    }
                    val lName = lname.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                        else it.toString()
                    }
                    addUserToDatabase(fName, lName, email, mAuth.currentUser?.uid!!)
                    // To home activity
                    val intent = Intent(this@SignUp, Login::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(firstName: String, lastName: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(firstName, lastName, email, uid))
    }
}