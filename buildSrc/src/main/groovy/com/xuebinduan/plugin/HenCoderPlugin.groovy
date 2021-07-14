package com.xuebinduan.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class HenCoderPlugin implements Plugin<Project> {
    /**
     * 留心
     * @param project todo 这里变量名注意一下，有的项目习惯是project有的是target，如果写错，groovy可不太好能找到错误
     */
    @Override
    void apply(Project project) {
        //扩展
        def extension = project.extensions.create("hencoder2", HenCoderExtension2)
        project.afterEvaluate {
            println "Hi ${extension.name}!"
        }
        //android gradle transform
        /**
         * Transform是Android Gradle Plugin提供给我们的，让我们在android程序构建过程中进行一些操作。
         * 它提供的是编译之后的文件（在打包之前），开放给我们，让我们可以去修改它们。
         */
        def transform = new HenCoderTransform()
        def baseExtension = project.extensions.getByType(BaseExtension)
        //注册进打包过程
        //不注册就会跳过，如果注册那么我们就需要自己好好的好好搬运文件。这个的好处就是我们可以CRUD文件，我们编辑文件一般使用javassist、ASM
        baseExtension.registerTransform(transform)
        //注册一个任务task
        project.tasks.register('readCode',ReadCodeTask)
    }
}