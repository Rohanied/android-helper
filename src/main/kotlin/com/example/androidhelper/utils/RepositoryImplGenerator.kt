package com.example.androidhelper.utils


import com.intellij.openapi.vfs.VirtualFile

object RepositoryImplGenerator {

    fun createRepositoryFiles(dir: VirtualFile, input1: String, domainRepoDirUrl : String) {

        val packageName = dir.url.substring(dir.url.indexOf("com"), dir.url.length).replace("/", ".")
        val repoPackageName = domainRepoDirUrl.substring(domainRepoDirUrl.indexOf("com"), domainRepoDirUrl.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        // Create Repository Interface
        val repoFileName = "${featureName}Repository"

        // Create Repository Implementation
        val repoImplFileName = "${featureName}RepositoryImpl.kt"
        val repoImplContent = """
            package $packageName
            
            import $repoPackageName.${featureName}Repository
            
            class ${featureName}RepositoryImpl : ${featureName}Repository { 
            
            }
        """.trimIndent()
        FileUtil.createFileWithContent(dir, repoImplFileName, repoImplContent)
    }
}