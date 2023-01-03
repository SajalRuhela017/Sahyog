package com.example.Sahyog


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ForgotPassword : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        var submitEmail = findViewById<Button>(R.id.email_submit)
        var email = findViewById<EditText>(R.id.forget_email)
        var backToSigning = findViewById<TextView>(R.id.forgot_to_signin)

        submitEmail.setOnClickListener {
            if(!email.text.isEmpty()) {
                Toast.makeText(this,"If you have an account, mail has been sent to reset your password.\n", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(this , "Please enter your registered email", Toast.LENGTH_SHORT).show()
        }
        backToSigning.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}