package com.yogeshj.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, private val arr:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val messageSent: TextView =itemView.findViewById(R.id.sent_text)
    }
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val messageReceived: TextView =itemView.findViewById(R.id.receive_text)
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().currentUser!!.uid == arr[position].senderId) //Implies we are sending the message
            1
        else
            2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1)
            SentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_sent,parent,false))
        else
            ReceiveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_receive,parent,false))

    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass==SentViewHolder::class.java)
        {
            val viewHolder=holder as SentViewHolder
            viewHolder.messageSent.text=arr[position].message
        }
        else
        {
            val viewHolder=holder as ReceiveViewHolder
            viewHolder.messageReceived.text=arr[position].message
        }

    }
}