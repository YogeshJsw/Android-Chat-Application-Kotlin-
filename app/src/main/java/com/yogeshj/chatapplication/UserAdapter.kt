package com.yogeshj.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context:Context, private val arr:ArrayList<User>):RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val name: TextView =itemView.findViewById(R.id.name_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_recycler_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=arr[position].name

        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",arr[position].name)
            intent.putExtra("uid",arr[position].uid)
            context.startActivity(intent)
        }
    }
}