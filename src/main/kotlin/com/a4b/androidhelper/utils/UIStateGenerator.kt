package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object UIStateGenerator {

    fun createUiStateFile(baseDir: VirtualFile, input1: String, isCachingEnabled : Boolean = false) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        val dataClassPackage = "${packageName.substring(0,packageName.indexOf(".${ input1 }."))}.${input1}.data.model"

        val successParameter = if(isCachingEnabled){
            "val data : ${featureName}Data"
        }else {
            ""
        }

        val cachingImports = if(isCachingEnabled){
            """import ${dataClassPackage}.${featureName}Data""".trimMargin()
        }else {
            "\n"
        }

        // Create Fragment class
        val fragmentFileName = "${featureName}UiState.kt"
        val className = "${featureName}UiState()"
        val fragmentContent = """
            package $packageName    
                      
            $cachingImports
            
            sealed class $className {
                
                data object Loading : $className
                
                data class Success($successParameter?) : $className
                
                data class Error(val message : String?) : $className
            
            }
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, fragmentFileName, fragmentContent)
    }
}