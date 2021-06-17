package com.xuebinduan.retrofit

sealed class NetworkResult<T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure(val code: Int, val message: String) : NetworkResult<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$message]"
        }
    }
}