package com.yogeshj.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yogeshj.chatapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var userNames:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var dbRef:DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db reference
        dbRef=FirebaseDatabase.getInstance().getReference("Users")
        auth= FirebaseAuth.getInstance()

        //Recycler View

        userNames=ArrayList()
        adapter=UserAdapter(this,userNames)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

        //Reading data from database
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userNames.clear()
                if(snapshot.exists())
                {
                    for(snap in snapshot.children)
                    {
                        val currUser=snap.getValue(User::class.java)!!
                        if(auth.currentUser!!.uid == currUser.uid)
                        {
                            continue
                        }
                        userNames.add(currUser)
                    }
                    adapter=UserAdapter(this@MainActivity,userNames)
                    binding.recyclerView.adapter=adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout)
        {
            auth.signOut()
            val intent=Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}