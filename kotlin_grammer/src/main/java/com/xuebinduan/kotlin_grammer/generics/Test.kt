package com.xuebinduan.kotlin_grammer.generics

class Test {
    fun main(){
        //<out Fruit> 等于java的 <? extends Fruit>，只会拿不会装，所以参数不能为T
        val shop : Shop<out Fruit> = Shop<Apple>()
        //<in Apple> 等于java的 <? super Apple>，只会装不会拿，所以返回值不能为T
        val shop2 : Shop<in Apple> = Shop<Fruit>()
        //<*> 等于 java的 <?>，只会拿不会装，所以参数不能为T
        val shop3:Shop<*> = Shop<Apple>()


        val shopList:MutableList<out Fruit> = mutableListOf()
        shopList.forEach {  }
        val shopList2:MutableList<in Fruit> = mutableListOf()
        shopList2.add(Fruit())
        val shopList3:MutableList<*> = mutableListOf<Fruit>()
        shopList3.forEach {  }
    }
}