package com.example.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object ViewModelGenerator {

    fun createViewModelFiles(baseDir: VirtualFile, input1: String, useCaseDirUrl : String) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")
        val useCasePackageName = useCaseDirUrl.substring(useCaseDirUrl.indexOf("com"), useCaseDirUrl.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        // Create ViewModel class
        val viewModelFileName = "${featureName}ViewModel.kt"
        val viewModelContent = """
            package $packageName
            
            import androidx.lifecycle.ViewModel
            import ${useCasePackageName}.${featureName}UseCases
            
            class ${featureName}ViewModel(private val useCases: ${featureName}UseCases) : ViewModel() { 
            
            }
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, viewModelFileName, viewModelContent)
    }
}