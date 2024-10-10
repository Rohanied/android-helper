package com.example.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object UseCaseGenerator {

    fun createUseCaseFiles(baseDir: VirtualFile, input1: String, repoDirUrl : String) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")
        val repoPackageName = repoDirUrl.substring(repoDirUrl.indexOf("com"), repoDirUrl.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        // Create UseCase class
        val useCaseFileName = "${featureName}UseCases.kt"
        val useCaseContent = """
            package $packageName
            
            import $repoPackageName.${featureName}Repository
            
            class ${featureName}UseCases(private val repository: ${featureName}Repository)  { 
            
            }
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, useCaseFileName, useCaseContent)
    }
}