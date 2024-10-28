package com.a4b.androidhelper

import com.a4b.androidhelper.ui.FeatureInfoForm
import com.a4b.androidhelper.ui.Window
import com.a4b.androidhelper.ui.Window3
import com.a4b.androidhelper.utils.DataClassGenerator
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.serialization.json.*
import java.util.*
import javax.swing.SwingUtilities

class MyAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val virtualFile: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)

        if (project != null && virtualFile != null && virtualFile.isDirectory) {
//            val dialog = CustomDialog(event.project!!, virtualFile)
//            dialog.show()
            SwingUtilities.invokeLater {
                val featureInfoForm = FeatureInfoForm(event.project!!, virtualFile)
                featureInfoForm.isVisible = true
            }
        }
    }

    override fun update(event: AnActionEvent) {
        val virtualFile: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = true
    }

}



