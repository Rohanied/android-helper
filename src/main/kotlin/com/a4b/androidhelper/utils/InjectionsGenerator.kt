package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile
import java.nio.charset.StandardCharsets

object InjectionsGenerator {

    fun generateAppModule(featureName : String, isCachingEnabled : Boolean = false, repoDir : VirtualFile) : String{
        val res = StringBuilder()

        val featureNameCapitalized = FileUtil.capitalize(featureName)

        res.append("factory<${featureNameCapitalized}Repository> { ${featureNameCapitalized}Impl(get(), get())\n")
        res.append("\n")

        val repoFileName = "${featureName}Repository"

        val repoFile = repoDir.findChild("${repoFileName}.kt")

        var useCases = ""

        if(repoFile != null) {

            val repoFileContent = String(repoFile.contentsToByteArray(), StandardCharsets.UTF_8)

            val methods = FileUtil.extractMethodDetails(repoFileContent)

            useCases = methods.joinToString { "\t"+FileUtil.capitalize(it.methodName)+"(get()),\n" }

        }


        if(useCases.isEmpty()){
            res.append("factory { ${featureNameCapitalized}UseCases() }")
        }else{
            res.append("""factory { 
                ${featureNameCapitalized}UseCases(
                    $useCases
                                    )
                }""".trimMargin())
        }

        return res.toString()
    }

    fun generateViewModel(featureName : String) : String{
        val res = StringBuilder()

        res.append("factory { ${FileUtil.capitalize(featureName)}ViewModel(get()) }")

        return res.toString()

    }


}