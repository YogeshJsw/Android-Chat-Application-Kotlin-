package com.yogeshj.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yogeshj.chatapplication.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Removing toolbar
        supportActionBar?.hide()

        //Authentication Reference
        auth = FirebaseAuth.getInstance()



        binding.btnSignup.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            signup(name, email, password)

        }
    }

    //Creating user
    private fun signup(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task->
                if (task.isSuccessful) {   //Sign in successful

                    addUserToDatabase(name, email, auth.currentUser!!.uid)
                    //Code for going to home
                    val intent = Intent(this, MainActivity::class.java)
                    if(baseContext!=this@SignupActivity)
                    {
                        finish()
                    }
                    startActivity(intent)

                }
            }.addOnFailureListener {err->
                Snackbar.make(binding.root, "Error: ${err.message}", Snackbar.LENGTH_LONG).show()
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        //Db reference
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.child(uid).setValue(User(uid, name, email))
    }
}