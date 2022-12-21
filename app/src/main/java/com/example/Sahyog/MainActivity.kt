package com.example.Sahyog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username = findViewById<EditText>(R.id.username)
        var password = findViewById<EditText>(R.id.password)
        var button = findViewById<Button>(R.id.signinbutton)
        var signupButton = findViewById<TextView>(R.id.signup1)
        var forgotEmail = findViewById<TextView>(R.id.forget)

        button.setOnClickListener {
            val first = username.text
            val second = password.text
            if(username.text.isEmpty() || password.text.isEmpty())
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
            val Intent = Intent(this, SignupPage::class.java)
            startActivity(Intent)
        }
        forgotEmail.setOnClickListener {
            val Intent = Intent(this, ForgotPassword::class.java)
            startActivity(Intent)
        }
    }
}