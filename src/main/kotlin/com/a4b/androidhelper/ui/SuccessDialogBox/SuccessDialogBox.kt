package com.a4b.androidhelper.ui.SuccessDialogBox;

import javax.swing.*;
import kotlin.system.exitProcess

class SuccessDialogBox(appModule: String, viewModelModule : String) : JDialog() {
    private lateinit var contentPane: JPanel
    private lateinit var buttonOK: JButton
    private lateinit var appModuleArea: JTextArea
    private lateinit var viewModelModuleArea: JTextArea

    init {
        setContentPane(contentPane)
        isModal = true
        rootPane.defaultButton = buttonOK

        appModuleArea.text = appModule
        viewModelModuleArea.text = appModule

        buttonOK.addActionListener {
            onOK()
        }
    }

    private fun onOK() {
        // Add your code here
        dispose()
    }
}

fun main() {
    val dialog = SuccessDialogBox("Success message", "Test")
    dialog.pack()
    dialog.isVisible = true
    exitProcess(0)
}

