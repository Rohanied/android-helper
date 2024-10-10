package com.example.androidhelper.utils


import com.intellij.openapi.vfs.VirtualFile

object RepositoryGenerator {

    fun createRepositoryFiles(dir: VirtualFile, input1: String) {

        val packageName = dir.url.substring(dir.url.indexOf("com"), dir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        // Create Repository Interface
        val repoFileName = "${featureName}Repository"
        val repoContent = "package $packageName\n\ninterface ${featureName}Repository { \n\n}"
        FileUtil.createFileWithContent(dir, "$repoFileName.kt", repoContent)

    }
}