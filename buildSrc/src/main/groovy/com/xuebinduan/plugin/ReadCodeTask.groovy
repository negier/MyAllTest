package com.xuebinduan.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReadCodeTask extends DefaultTask {

    @TaskAction
    def read() {
        def appDir = getProject().projectDir
        appDir.eachFileRecurse { file ->
            if (!file.absolutePath.startsWith("${appDir.absolutePath}${File.separator}build$File.separator")) {
                println file
//                if (file.isFile()) {
//                    println file.text
//                }
            }
        }
    }

}