package com.xuebinduan.paging3

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(loadState: LoadState) {
//        if (loadState is LoadState.Error) {
//            errorMsg.text = loadState.error.localizedMessage
//        }

//        itemView.isVisible = loadState is LoadState.Loading

//        progressBar.isVisible = loadState is LoadState.Loading
//        retry.isVisible = loadState is LoadState.Error
//        errorMsg.isVisible = loadState is LoadState.Error
    }

}