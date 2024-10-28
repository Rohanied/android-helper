package com.a4b.androidhelper.utils


import com.intellij.openapi.vfs.VirtualFile

object RepositoryImplGenerator {

    fun createRepositoryFiles(dir: VirtualFile, input1: String, domainRepoDirUrl : String,  isCachingEnabled : Boolean = false, apiClientPackage : String, appDatabaseProviderPackage : String) {

        val packageName = dir.url.substring(dir.url.indexOf("com"), dir.url.length).replace("/", ".")
        val repoPackageName = domainRepoDirUrl.substring(domainRepoDirUrl.indexOf("com"), domainRepoDirUrl.length).replace("/", ".")

        val featureName = input1[0].uppercase()+input1.substring(1)

        val dataClassPackage = "${packageName.substring(0,packageName.indexOf(".${ input1 }."))}.${input1}.data.model"

        val getDataMethod = if (isCachingEnabled) {
            """override suspend fun getData() : Flow<NetworkResult<${featureName}Data?>> {
                
        val dao = appDatabaseProvider.get${featureName}Dao()
   
        val networkResource = NetworkResource<${featureName}Data>(
            {apiClient.getV2Service.get${featureName}Data()},
            {dao.getAll()},
            {dao.replace()},
            {dao.deleteAll()}
        )
            
        return networkResource.query()
   
   }
            """.trimMargin()
        }else {
            ""
        }

        val parameters = if(isCachingEnabled){
            "private val apiClient : ApiClient, private val appDatabaseProvider : AppDatabaseProvider"
        }else {
            ""
        }

        val cachingImports = if(isCachingEnabled){

            """import ${dataClassPackage}.${featureName}Data
import com.mandir.netowrk.model.NetworkResult
import com.mandir.netowrk.NetworkResource
import kotlinx.coroutines.flow.Flow
import $apiClientPackage
import $appDatabaseProviderPackage
            """.trimMargin()
        }else {
            "\n"
        }

        // Create Repository Implementation
        val repoImplFileName = "${featureName}RepositoryImpl.kt"
        val repoImplContent = """
package $packageName

import $repoPackageName.${featureName}Repository

$cachingImports

class ${featureName}RepositoryImpl(${parameters}) : ${featureName}Repository { 

    $getDataMethod

}
        """.trimIndent()
        FileUtil.createFileWithContent(dir, repoImplFileName, repoImplContent)
    }
}