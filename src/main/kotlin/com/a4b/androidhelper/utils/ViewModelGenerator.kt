package com.a4b.androidhelper.utils

import com.intellij.openapi.vfs.VirtualFile

object ViewModelGenerator {

    fun createViewModelFiles(baseDir: VirtualFile, input1: String, useCaseDirUrl : String, isCachingEnabled : Boolean = false) {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")
        val useCasePackageName = useCaseDirUrl.substring(useCaseDirUrl.indexOf("com"), useCaseDirUrl.length).replace("/", ".")

        val uiStatePackage = "${packageName.substring(0,packageName.indexOf(".${ input1 }."))}.${input1}.presentation.state"

        val featureName = input1[0].uppercase()+input1.substring(1)


        val cacheMethod = if(isCachingEnabled){
            """fun getData() = viewModelScope.launch {
        uiState.value = ${featureName}UiState.Loading
        useCases.getData().collect {
            if (it.status == NetworkResultStatus.SUCCESS) {
                uiState.value = ${featureName}UiState.Success(it.data)
            }else if (it.status == NetworkResultStatus.ERROR){
                uiState.value = ${featureName}UiState.Error(it.message)
            }
        }
    }        """.trimMargin()
        }else{
            ""
        }

        // Create ViewModel class
        val viewModelFileName = "${featureName}ViewModel.kt"
        val viewModelContent = """
package $packageName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import com.mandir.netowrk.model.NetworkResultStatus
import ${useCasePackageName}.${featureName}UseCases
import ${uiStatePackage}.${featureName}UiState

class ${featureName}ViewModel(private val useCases: ${featureName}UseCases) : ViewModel() { 

    fun uiState(): LiveData<${featureName}UiState> = uiState
    protected val uiState: MutableLiveData<${featureName}UiState> = MutableLiveData()

    $cacheMethod

}
        """.trimIndent()
        FileUtil.createFileWithContent(baseDir, viewModelFileName, viewModelContent)
    }
}