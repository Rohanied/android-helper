package com.a4b.androidhelper.utils


import com.intellij.openapi.vfs.VirtualFile

object RepositoryGenerator {

    fun createRepositoryFiles(dir: VirtualFile, input1: String, isCachingEnabled : Boolean = false) {

        val packageName = dir.url.substring(dir.url.indexOf("com"), dir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        val dataClassPackage = "${packageName.substring(0,packageName.indexOf(".${ input1 }."))}.${input1}.data.model"

        val getDataMethod = if (isCachingEnabled) {
            "suspend fun getData() : Flow<NetworkResult<${featureName}Data?>>"
        }else {
            ""
        }

        val cachingImports = if(isCachingEnabled){

            """import ${dataClassPackage}.${featureName}Data
import com.mandir.netowrk.model.NetworkResult
import kotlinx.coroutines.flow.Flow
            """.trimMargin()
        }else {
            "\n"
        }

        // Create Repository Interface
        val repoFileName = "${featureName}Repository"
        val repoContent =
            """package $packageName                            
                
$cachingImports

interface ${featureName}Repository { 
                 
    $getDataMethod
                 
}
           """.trimMargin()
        FileUtil.createFileWithContent(dir, "$repoFileName.kt", repoContent)

    }
}