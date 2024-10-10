package com.example.androidhelper.utils

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
}