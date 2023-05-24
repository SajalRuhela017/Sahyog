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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val instance = FirebaseAuth.getInstance()
        if(instance.currentUser != null) {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WelcomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signInButton = findViewById<Button>(R.id.signinbutton)
        val signupButton = findViewById<TextView>(R.id.signup1)
        val forgotEmail = findViewById<TextView>(R.id.forget)

        signInButton.setOnClickListener {
            if(email.text.isEmpty() || password.text.isEmpty())
                Toast.makeText(this, "Please fill all the fields" , Toast.LENGTH_SHORT).show()
            else
                performSignIn()
        }
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
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
                if(Firebase.auth.currentUser!!.isEmailVerified)
                {
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Please verify your email.", Toast.LENGTH_SHORT).show()
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            Toast.makeText(this , "Verification link resent." , Toast.LENGTH_SHORT).show()
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(this , it.toString() , Toast.LENGTH_SHORT).show()
                        }
                }
            }
            else
            {
                Toast.makeText(this, "Authentication failed. \nUsername/Password incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}