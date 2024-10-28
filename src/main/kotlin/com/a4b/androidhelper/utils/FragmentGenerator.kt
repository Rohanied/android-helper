package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object FragmentGenerator {

    fun createFragmentFiles(baseDir: VirtualFile, input1: String) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        // Create Fragment class
        val fragmentFileName = "${featureName}Fragment.kt"
        val fragmentContent = """
            package $packageName        
                       
            import androidx.fragment.app.Fragment
            
            class ${featureName}Fragment : Fragment() {
            
            
            
            }
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, fragmentFileName, fragmentContent)
    }
}




