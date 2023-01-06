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

class ForgotPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        var submitEmail = findViewById<Button>(R.id.email_submit)
        var email = findViewById<EditText>(R.id.forget_email)
        var backToSigning = findViewById<TextView>(R.id.forgot_to_signin)

        backToSigning.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        auth = FirebaseAuth.getInstance()

        submitEmail.setOnClickListener {
            if(email.text.isNotEmpty()) {
                Firebase.auth.sendPasswordResetEmail(email.text.toString()).addOnSuccessListener {
                    Toast.makeText(this,"Mail Sent.\nPlease signin again", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this , "Email not found!\nEnter correct email." , Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(this , "Please enter your registered email", Toast.LENGTH_SHORT).show()
        }
    }
}