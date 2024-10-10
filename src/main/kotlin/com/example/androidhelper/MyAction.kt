package com.example.androidhelper

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VirtualFile

class MyAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val virtualFile: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)

        if (project != null && virtualFile != null && virtualFile.isDirectory) {
            val dialog = CustomDialog(event.project!!, virtualFile)
            dialog.show()
        }
    }

    override fun update(event: AnActionEvent) {
        val virtualFile: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = true
    }
}
