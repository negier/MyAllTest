package com.xuebinduan.io

import org.junit.Test
import java.io.File

class KotlinMain {
    //这是kotlin快捷复制文件的方式，当然我们也可以使用下FileUtils.copy(fileInputStream,fileOutputStream)，注意呀Test环境使用不了FileUtils会报错，综合下来还是Kotlin舒服。
    @Test
    fun main(){
        val file = File("./text.txt")
        file.copyTo(File("./textcopy.txt"))
    }
}