package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object FileUtil {
    fun createDirectory(parentDir: VirtualFile, dirName: String): VirtualFile {
        return parentDir.createChildDirectory(this, dirName)
    }

    fun createFileWithContent(dir: VirtualFile, fileName: String, content: String): VirtualFile {
        val file = dir.createChildData(this, fileName)
        file.setBinaryContent(content.toByteArray())
        return file
    }

    fun generatePackagePath(basePackage: String, dirName: String): String {
        return "com.demo.${dirName}".substring(basePackage.indexOf("com")).replace("/", ".")
    }

    fun extractMethodDetails(interfaceString: String): List<MethodInfo> {
        // Define a regex to match the method name, parameters (empty or otherwise), and return type
        val methodRegex = """suspend\s+fun\s+(\w+)\((.*?)\)\s*:\s*([^\n]+)""".toRegex()

        // Define a regex to match individual parameters (name: type)
        val parameterRegex = """(\w+)\s*:\s*([\w<>\?,\s]+)""".toRegex()

        // Find all method matches
        return methodRegex.findAll(interfaceString).map { matchResult ->
            val methodName = matchResult.groupValues[1]  // The method name
            val parameterString = matchResult.groupValues[2]  // Parameters as a string
            val returnType = matchResult.groupValues[3]  // Return type

            // Extract individual parameters, or return an empty list if there are no parameters
            val parameters = if (parameterString.isNotBlank()) {
                parameterRegex.findAll(parameterString).map { paramMatch ->
                    val paramName = paramMatch.groupValues[1]  // Parameter name
                    val paramType = paramMatch.groupValues[2]  // Parameter type
                    ParameterInfo(paramName, paramType)
                }.toList()
            } else {
                emptyList()  // No parameters
            }

            // Return MethodInfo with method name, parameters, and return type
            MethodInfo(methodName, parameters, returnType)
        }.toList()
    }

    fun capitalize(input: String) = input.first().uppercase() + input.substring(1)


}

data class MethodInfo(
    val methodName: String,
    val parameters: List<ParameterInfo>,
    val returnType: String
)

data class ParameterInfo(
    val paramName: String,
    val paramType: String
)
