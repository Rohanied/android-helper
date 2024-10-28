package com.a4b.androidhelper.utils

import com.intellij.execution.ui.utils.fragments
import com.intellij.openapi.vfs.VirtualFile

object DaoGenerator {

    fun createFragmentFiles(baseDir: VirtualFile, input1: String, dataClassDir : VirtualFile, dataClass : String) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")
        val dataClassPackage = dataClassDir.url.substring(dataClassDir.url.indexOf("com"), dataClassDir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        val daoFileName = "${featureName}Dao.kt"

        val tableName = "${camelToSnake(dataClass)}_table"

        val content = """
            package $packageName
            
            import androidx.room.Dao
            import androidx.room.Insert
            import androidx.room.OnConflictStrategy
            import androidx.room.Query
            import androidx.room.Transaction
            import ${dataClassPackage}.${featureName}Data
            
            @Dao
            interface ${featureName}Dao {
            
                @Query("SELECT * FROM $tableName")
                suspend fun getAll() : List<${dataClass}>
                 
                 @Insert(onConflict = OnConflictStrategy.REPLACE)
                 suspend fun insert(data : List<${dataClass}>)
                 
                 @Query("DELETE FROM $tableName")
                 suspend fun deleteAll()
                 
                 
            }
            
            
            @Transaction
            suspend fun ${featureName}Dao.replaceAll(data : List<$dataClass>){
                    
                deleteAll()
                
                insert(data)
                    
             }                                               
       
                   
        """.trimIndent()

        FileUtil.createFileWithContent(baseDir, daoFileName, content)

    }

    fun camelToSnake(camelCase: String): String {
        return camelCase.replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()
    }

}