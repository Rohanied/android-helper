package com.example.androidhelper


import ai.grazie.text.find
import com.example.androidhelper.utils.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import javax.swing.*
import java.awt.*

class CustomDialog(private val project: Project, private val selectedDirectory: VirtualFile) : DialogWrapper(true) {
    private val inputField1 = JTextField()
    private val inputField2 = JTextField()
    private val hintText1 = JLabel("Example : Login")
    private val hintText2 = JLabel("Example : Login with Forget password")
    private val headerText1 = JLabel("AI Helper - Experiment")
    private val headerText2 = JLabel("Options")
    private val checkbox = JCheckBox("Also create package for this feature")
    private val clickableText = JLabel("More options")

    private val okButton = JButton("OK")
    private val cancelButton = JButton("Cancel")

    private val divider = JPanel()

    init {
        init()
        title = "Custom Dialog"
        setSize(500, 100) // Set the desired size

        // Customize hint text properties
        hintText1.font = hintText1.font.deriveFont(11f) // Set font size
        hintText1.foreground = JBColor.GRAY // Set font color

        hintText2.font = hintText2.font.deriveFont(11f) // Set font size
        hintText2.foreground = JBColor.GRAY // Set font color

        divider.border = JBUI.Borders.emptyBottom(1)
        divider.preferredSize = Dimension(divider.preferredSize.width, 1)
        divider.background = JBColor.GRAY
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        // Two line text/label
        val label1 = JLabel("Enter the feature name")
        label1.horizontalAlignment = SwingConstants.LEFT
        val label1Panel = JPanel(FlowLayout(FlowLayout.LEFT))
        label1Panel.add(label1)
        panel.add(label1Panel)
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer

        // Input layout with hint text
        val inputPanel1 = JPanel(BorderLayout())
        inputPanel1.add(inputField1, BorderLayout.CENTER)
        val hintPanel1 = JPanel(BorderLayout())
        hintPanel1.add(hintText1, BorderLayout.CENTER)
        inputPanel1.add(hintPanel1, BorderLayout.SOUTH)
        panel.add(inputPanel1)
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer

        panel.add(divider, BorderLayout.SOUTH)

//        // Header text with divider
//        val headerPanel1 = JPanel(BorderLayout())
//        headerPanel1.add(headerText1, BorderLayout.WEST)
//
//        headerPanel1.add(divider, BorderLayout.EAST)
//        panel.add(Box.createRigidArea(Dimension(0, 6)))
//        panel.add(headerPanel1)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer
//
//        // Two line text/label
//        val label2 = JLabel("Enter the Prompt")
//        label2.horizontalAlignment = SwingConstants.LEFT
//        val label2Panel = JPanel(FlowLayout(FlowLayout.LEFT))
//        label2Panel.add(label2)
//        panel.add(label2Panel)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer
//
//        // Input layout with hint text
//        val inputPanel2 = JPanel(BorderLayout())
//        inputPanel2.add(inputField2, BorderLayout.CENTER)
//        val hintPanel2 = JPanel(BorderLayout())
//        hintPanel2.add(hintText2, BorderLayout.CENTER)
//        inputPanel2.add(hintPanel2, BorderLayout.SOUTH)
//        panel.add(inputPanel2)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer
//
//        // Header text with divider
//        val headerPanel2 = JPanel(BorderLayout())
//        headerPanel2.add(headerText2, BorderLayout.WEST)
//        headerPanel2.add(divider, BorderLayout.SOUTH)
//        panel.add(headerPanel2)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer
//
//        // Checkbox and clickable text
//        val checkboxPanel = JPanel()
//        checkboxPanel.add(checkbox)
//        panel.add(checkboxPanel)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer
//
//        val clickablePanel = JPanel()
//        clickablePanel.add(clickableText)
//        panel.add(clickablePanel)
//        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer

        return panel
    }

    override fun createSouthPanel(): JComponent {
        val buttonPanel = JPanel()
        buttonPanel.layout = BorderLayout()

        val buttonsPanel = JPanel()
        buttonsPanel.add(cancelButton)
        buttonsPanel.add(okButton)
        buttonPanel.add(buttonsPanel, BorderLayout.EAST)

        // Button actions
        okButton.addActionListener {
            // Handle OK button click
            val input1 = inputField1.text[0].lowercase() + inputField1.text.substring(1)
            val input2 = inputField2.text
            val checkboxSelected = checkbox.isSelected


            ApplicationManager.getApplication().runWriteAction {
                try {
                    val newDir = FileUtil.createDirectory(selectedDirectory, input1)
                    /**/
                    /**/
                    /*  */val domainDir = FileUtil.createDirectory(newDir, "domain")
                    /*  */
                    /*  */
                    /*      */val domainModelDir = FileUtil.createDirectory(domainDir, "model")
                    /*      */val domainRepoDir = FileUtil.createDirectory(domainDir, "repository")
                    /*      */
                    /*      */
                    /*           */RepositoryGenerator.createRepositoryFiles(domainRepoDir, input1)
                    /*      */
                    /*      */
                    /*      */val usecaseDir = FileUtil.createDirectory(domainDir, "usecase")
                    /*      */
                    /*      */
                    /*           */UseCaseGenerator.createUseCaseFiles(usecaseDir, input1, domainRepoDir.url)
                    /**/
                    /**/
                    /*  */val dataDir = FileUtil.createDirectory(newDir, "data")
                    /*  */
                    /*  */
                    /*      */val dataModelDir = FileUtil.createDirectory(dataDir, "model")
                    /*      */val dataRepoDir = FileUtil.createDirectory(dataDir, "repository")
                    /*      */
                    /*      */
                    /*           */RepositoryImplGenerator.createRepositoryFiles(dataRepoDir, input1, domainRepoDir.url)
                    /*      */
                    /*      */
                    /*      */val sourceDir = FileUtil.createDirectory(dataDir, "source")
                    /*      */
                    /*      */
                    /*           */val localSource = FileUtil.createDirectory(sourceDir, "local")
                    /*           */val remoteSource = FileUtil.createDirectory(sourceDir, "remote")
                    /**/
                    /**/
                    /*  */val presentationDir = FileUtil.createDirectory(newDir, "presentation")
                    /*  */
                    /*  */
                    /*      */val uiDir = FileUtil.createDirectory(presentationDir, "ui")
                    /*      */
                    /*      */
                    /*           */val viewDir = FileUtil.createDirectory(uiDir, "view")
                    /*      */
                    /*      */
                    /*           */FragmentGenerator.createFragmentFiles(viewDir, input1)
                    /*      */
                    /*      */
                    /*           */val adapterDir = FileUtil.createDirectory(uiDir, "adapter")
                    /*  */
                    /*  */
                    /*      */val viewmodelDir = FileUtil.createDirectory(presentationDir, "viewmodel")
                    /*      */
                    /*      */
                    /*           */ViewModelGenerator.createViewModelFiles(viewmodelDir,input1, usecaseDir.url)
                    /*      */
                    /*      */
                    /*      */val state = FileUtil.createDirectory(presentationDir, "state")



                    ApplicationManager.getApplication().invokeLater {
                        Messages.showMessageDialog(
                            project,
                            "Directory and file created successfully.",
                            "Success",
                            Messages.getInformationIcon()
                        )
                    }

                } catch (e: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        Messages.showErrorDialog(project, "Failed to create directory or file: ${e.message}", "Error")
                        println(e.stackTrace)
                    }
                }
            }




            close(OK_EXIT_CODE)
        }

        cancelButton.addActionListener {
            close(CANCEL_EXIT_CODE)
        }

        return buttonPanel
    }
}

