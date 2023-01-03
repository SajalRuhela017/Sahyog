package com.example.Sahyog

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

class SignupPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        var button = findViewById<Button>(R.id.signup)
        val backSignin = findViewById<TextView>(R.id.back_to_signin)

        backSignin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener() {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.email)
        val mobileNumber = findViewById<EditText>(R.id.mobile_no)
        val initialPassword = findViewById<EditText>(R.id.password1)
        val confirmPassword = findViewById<EditText>(R.id.password2)

        if(email.text.isEmpty() || mobileNumber.text.isEmpty() || initialPassword.text.isEmpty() || confirmPassword.text.isEmpty())
        {
            Toast.makeText(this, "Please fill all the details.", Toast.LENGTH_SHORT).show()
            return
        }
        val inputEmail = email.text.toString()
        lateinit var inputPassword: String
        if(initialPassword.text.toString() == confirmPassword.text.toString())
            inputPassword = initialPassword.text.toString()
        else
        {
            Toast.makeText(this, "Password does not matched!", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(this)
        {
            task ->
            if (task.isSuccessful)
            {
                val intent = Intent(this, WelcomeScreen::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Successful signup!", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
    }
}
