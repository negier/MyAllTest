package com.xuebinduan.customviewnestedscroll.nest

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xuebinduan.customviewnestedscroll.R
import java.util.*

class MyRecyclerAdapter: RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView = itemView.findViewById(R.id.text_view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecyclerAdapter.ViewHolder, position: Int) {
        holder.textView.text = "文字$position"

        val random = Random()
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        holder.itemView.setBackgroundColor(Color.rgb(r,g,b))
    }

    override fun getItemCount(): Int {
        return 30
    }
}