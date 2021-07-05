package com.xuebinduan.paging3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textName: TextView
    init{
        textName = itemView.findViewById(R.id.text_name)
    }
}

class UserAdapter(diffCallback:DiffUtil.ItemCallback<User>) : PagingDataAdapter<User,UserViewHolder>(diffCallback){
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position) as User
        holder.textName.text = user.username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return UserViewHolder(view)
    }



}