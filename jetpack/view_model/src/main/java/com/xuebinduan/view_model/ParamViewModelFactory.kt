package com.xuebinduan.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParamViewModelFactory(private val param:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ParamViewModel(param) as T
    }
}