package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile
import java.nio.charset.StandardCharsets

object UseCaseGenerator {

    fun createMainUseCaseFile(baseDir: VirtualFile, input1: String, repoDir : VirtualFile, isCachingEnabled : Boolean = false) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")
        val repoPackageName = repoDir.url.substring(repoDir.url.indexOf("com"), repoDir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        val repoFileName = "${featureName}Repository"

        val repoFile = repoDir.findChild("${repoFileName}.kt") ?: return

        val repoFileContent = String(repoFile.contentsToByteArray(), StandardCharsets.UTF_8)

        val methods = FileUtil.extractMethodDetails(repoFileContent)

        val dataClass = if(methods.isNotEmpty()){
            methods.joinToString {
                "\n     val ${it.methodName} : ${it.methodName.first().uppercase()+it.methodName.substring(1)}\n"
            }
        }else {
            ""
        }

        // Create UseCase class
        val useCaseFileName = "${featureName}UseCases.kt"
        val useCaseContent = """
package $packageName
            
data class ${featureName}UseCases($dataClass){
            
}
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, useCaseFileName, useCaseContent)
    }

    fun createUsesCaseForRepo(dir : VirtualFile, repoDirUrl: VirtualFile, input1: String){

        val featureName = input1[0].uppercase()+input1.substring(1)

        val repoFileName = "${featureName}Repository"

        val repoFile = repoDirUrl.findChild("${repoFileName}.kt") ?: return

        val repoFileContent = String(repoFile.contentsToByteArray(), StandardCharsets.UTF_8)

        val methods = FileUtil.extractMethodDetails(repoFileContent)


        for (method in methods){
            createFileForUseCase(dir, method, repoDirUrl.url, repoFileName,featureName,input1)
        }
    }


    private fun createFileForUseCase(dir : VirtualFile, methodInfo: MethodInfo, repoDirUrl: String, repoName : String, featureName : String, input1: String){

        val packageName = dir.url.substring(dir.url.indexOf("com"), dir.url.length).replace("/", ".")
        val repoPackageName = repoDirUrl.substring(repoDirUrl.indexOf("com"), repoDirUrl.length).replace("/", ".")

        val fileName = methodInfo.methodName.first().uppercase() + methodInfo.methodName.substring(1)

        val dataClassPackage = "${packageName.substring(0,packageName.indexOf(".${ input1 }."))}.${input1}.data.model"

        val useCaseContent = """
            package $packageName
            
            import $repoPackageName.${repoName}
            import ${dataClassPackage}.${featureName}Data
            import com.mandir.netowrk.model.NetworkResult
            import kotlinx.coroutines.flow.Flow
            
            class ${fileName}(private val repository: $repoName)  { 
            
                suspend operator fun invoke(${methodInfo.parameters.joinToString(", ") { "${it.paramName} : ${it.paramType}" }}) : ${methodInfo.returnType}{
                    return repository.${methodInfo.methodName}(${methodInfo.parameters.joinToString(", ") { it.paramName }} )
                }
            
            }
        """.trimIndent()

        FileUtil.createFileWithContent(dir, "${fileName}.kt", useCaseContent)

    }
}