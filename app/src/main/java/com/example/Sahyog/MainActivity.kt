package com.example.Sahyog

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)
        var button = findViewById<Button>(R.id.signinbutton)
        var signupButton = findViewById<TextView>(R.id.signup1)
        var forgotEmail = findViewById<TextView>(R.id.forget)

        button.setOnClickListener {
            val first = email.text
            val second = password.text
            if(email.text.isEmpty() || password.text.isEmpty())
            {
                Toast.makeText(this, "Please fill all the fields" , Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val Intent = Intent(this, WelcomeScreen::class.java)
                startActivity(Intent)
            }
        }
        signupButton.setOnClickListener {
            performSignIn()
        }
        forgotEmail.setOnClickListener {
            val Intent = Intent(this, ForgotPassword::class.java)
            startActivity(Intent)
        }
    }

    private fun performSignIn()
    {
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        if(email.text.isEmpty() || password.text.isEmpty())
        {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            return
        }
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()
        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    getCurrentUser()
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getCurrentUser() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val emailVerified = user.isEmailVerified
            val uid = user.uid
        }
    }
}