package com.example.Sahyog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        var email = findViewById<EditText>(R.id.email)
        var mobileNumber = findViewById<EditText>(R.id.mobile_no)
        var initialPassword = findViewById<EditText>(R.id.password1)
        var confirmPassword = findViewById<EditText>(R.id.password2)
        var button = findViewById<Button>(R.id.signup)
        val backSignin = findViewById<TextView>(R.id.back_to_signin)

        backSignin.setOnClickListener {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }

        button.setOnClickListener{
            val first = email.text;
            val second = mobileNumber.text;
            val third = initialPassword.text;
            val forth = confirmPassword.text;
            if(email.text.isEmpty() || mobileNumber.text.isEmpty() || initialPassword.text.isEmpty() || confirmPassword.text.isEmpty())
            {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val Intent = Intent(this, WelcomeScreen::class.java)
                startActivity(Intent)
            }
        }
    }

}