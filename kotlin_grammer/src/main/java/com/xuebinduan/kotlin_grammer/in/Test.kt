package com.xuebinduan.kotlin_grammer.`in`

fun main() {
    //Read-only list
    val emailList = listOf("xiaoming@gmail.com", "dawang@gmail.com", "xiaohong@gmail.com")
    val targetEmail = "duanxuebin@gmail.com"
    val targetEmail2 = emailList[0]

    if (targetEmail !in emailList) {
        println("$targetEmail 不在列表里！")
    }

    if (targetEmail2 in emailList){
        println("$targetEmail2 在列表里！")
    }
}