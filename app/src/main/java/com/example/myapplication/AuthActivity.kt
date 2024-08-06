package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.classes.Joueur
import com.google.firebase.auth.FirebaseAuth

import android.util.Log
import android.widget.TextView

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var register: TextView

    companion object {
        private const val TAG = "AuthActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentify_activity)

        auth = FirebaseAuth.getInstance()
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val Sign_In: Button = findViewById(R.id.sign_in)
        //val Sign_Up: Button = findViewById(R.id.sign_up)


        Sign_In.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signIn(email, password)
        }
        // Passage sur une autre activité

        register = findViewById(R.id.register)
        register.setOnClickListener{
            Intent(this, resgisterActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    openNextActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun openNextActivity() {
        val intent = Intent(this, ModeActivity::class.java)
        val newJoueur = Joueur("Test")
        intent.putExtra("joueur",newJoueur)
        startActivity(intent)
    }
}
