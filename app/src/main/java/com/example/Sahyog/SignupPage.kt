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

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
//        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        auth = Firebase.auth
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
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            val Intent = Intent(this, WelcomeScreen::class.java)
            startActivity(Intent)
        }
        performSignUp()
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.email)
        val phNumber = findViewById<EditText>(R.id.mobile_no)
        val pass1 = findViewById<EditText>(R.id.password1)
        val pass2 = findViewById<EditText>(R.id.password2)

        val inputEmail = email.text.toString()
        val inputPhNumber = phNumber.text.toString()
        val inputPassword = pass1.text.toString()
        val confirmPassword = pass2.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPhNumber)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

}