package com.yogeshj.chatapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.yogeshj.chatapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Removing toolbar
        supportActionBar?.hide()

        auth=FirebaseAuth.getInstance()

        //Signup Button Clicked
        binding.btnSignup.setOnClickListener{
            val intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        //Login Button is pressed
        binding.btnLogin.setOnClickListener {
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                login(email,password)
            }
        }
    }

    //Logging in user
    @RequiresApi(Build.VERSION_CODES.S)
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    if(baseContext!=this@LoginActivity)
                    {
                        finish()
                    }



                } else {
                    Snackbar.make(binding.root,"Wrong email or password",Snackbar.LENGTH_LONG).show()
                }
            }.addOnFailureListener {err->
                Snackbar.make(binding.root, "Error: ${err.message}", Snackbar.LENGTH_LONG).show()
            }
    }
}