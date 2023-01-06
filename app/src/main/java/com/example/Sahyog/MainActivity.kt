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

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signInButton = findViewById<Button>(R.id.signinbutton)
        val signupButton = findViewById<TextView>(R.id.signup1)
        val forgotEmail = findViewById<TextView>(R.id.forget)

        signInButton.setOnClickListener {
            if(email.text.isEmpty() || password.text.isEmpty())
            {
                Toast.makeText(this, "Please fill all the fields" , Toast.LENGTH_SHORT).show()
            }
            else
            {
                performSignIn()
            }
        }
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
            finish()
        }
        forgotEmail.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun performSignIn()
    {
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()
        auth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(this)
        {
            task ->
            if (task.isSuccessful)
            {
                val intent = Intent(this, WelcomeScreen::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Authentication failed. \nUsername/Password incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}