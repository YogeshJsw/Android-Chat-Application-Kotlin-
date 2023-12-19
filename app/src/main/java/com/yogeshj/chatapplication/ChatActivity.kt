package com.yogeshj.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yogeshj.chatapplication.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding
    private lateinit var adapter:MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var dbRef:DatabaseReference

    private var senderRoom:String?=null
    private var receiverRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef=FirebaseDatabase.getInstance().getReference("chats")

        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")

        val senderUid=FirebaseAuth.getInstance().currentUser!!.uid

        senderRoom=receiverUid+senderUid
        receiverRoom=senderUid+receiverUid

        //Setting the name of the person with whom we are chatting
        supportActionBar?.title=name

        //RecyclerView
        messageList= ArrayList()
        adapter= MessageAdapter(this,messageList)

        binding.recycler.layoutManager=LinearLayoutManager(this)
        binding.recycler.adapter=adapter

        //Adding data to recycler view
        dbRef.child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (snap in snapshot.children)
                {
                    val message=snap.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                adapter= MessageAdapter(this@ChatActivity,messageList)
                binding.recycler.adapter=adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //Sending the message
        binding.send.setOnClickListener {
            val message=binding.message.text.toString()
            val msgObject=Message(message,senderUid)

            dbRef.child(senderRoom!!).child("messages").push()
                .setValue(msgObject).addOnSuccessListener {
                    dbRef.child(receiverRoom!!).child("messages").push()
                        .setValue(msgObject)
                }
            binding.message.setText("")
        }


    }
}