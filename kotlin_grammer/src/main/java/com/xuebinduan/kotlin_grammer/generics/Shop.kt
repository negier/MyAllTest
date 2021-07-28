package com.xuebinduan.kotlin_grammer.generics

//<T:Object>相当于java的<T extends Object>
class Shop<T> {
    fun buy(): T {
        return null as T
    }
    fun refund(item: T): Float{
        return 1f
    }
}