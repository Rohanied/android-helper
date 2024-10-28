package com.a4b.androidhelper


import com.a4b.androidhelper.utils.*
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
    private val jsonTextField = JTextField()
    private val hintText1 = JLabel("Example : Login")
    private val hintText2 = JLabel("Example : Login with Forget password")
    private val headerText1 = JLabel("AI Helper - Experiment")
    private val headerText2 = JLabel("Options")
    private val checkbox = JCheckBox("Also create package for this feature")
    private val clickableText = JLabel("More options")

    private val okButton = JButton("OK")
    private val cancelButton = JButton("Cancel")

    private var isJsonCreationEnabled = false

    private val divider = JPanel()


    private var createCaching = true


    init {
        init()
        title = "Custom Dialog"
        setSize(500, 400) // Set the desired size

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

        // Two-line text/label
        val label1 = JLabel("Enter the feature name")
        label1.horizontalAlignment = SwingConstants.LEFT
        val label1Panel = JPanel(FlowLayout(FlowLayout.LEFT))
        label1Panel.add(label1)
        panel.add(label1Panel)
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer

        // Create a panel to wrap the JTextField
        val textFieldPanel = JPanel()
        textFieldPanel.layout = BoxLayout(textFieldPanel, BoxLayout.Y_AXIS)
        inputField1.preferredSize = Dimension(200, 30) // Set fixed width and height

        // Add the JTextField to the panel
        textFieldPanel.add(inputField1)
        panel.add(textFieldPanel)
        panel.add(Box.createRigidArea(Dimension(0, 10))) // Spacer

        // Divider
        val divider = JSeparator()
        panel.add(divider)

        // Spacer after the divider
        panel.add(Box.createRigidArea(Dimension(0, 10)))

        // Add checkbox and conditional text field visibility
        val checkBox = JCheckBox("Add Response JSON")
        jsonTextField.isVisible = false // Initially hide the text field

        checkBox.addActionListener {
            jsonTextField.isVisible = checkBox.isSelected // Show/Hide text field based on checkbox state
            panel.revalidate()  // Revalidate the layout to adjust for visibility changes
            panel.repaint()
            isJsonCreationEnabled = checkBox.isSelected// Repaint the panel to reflect changes
        }

        // Add checkbox and text field to the panel
        panel.add(checkBox)
        panel.add(Box.createRigidArea(Dimension(0, 5))) // Spacer between checkbox and text field
        panel.add(jsonTextField)

//        return panel // Return the constructed panel

// Adjust layout to update visibility when components change size
//        val frame = JFrame("UI Example")
//        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//        frame.setSize(400, 300)
//        frame.add(panel)
//        frame.isVisible = true
//        frame.pack() // Adjusts the frame size to accommodate all components


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

            val json = jsonTextField.text


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
                    /*           */RepositoryGenerator.createRepositoryFiles(domainRepoDir, input1,createCaching)
                    /*      */
                    /*      */
                    /*      */val usecaseDir = FileUtil.createDirectory(domainDir, "usecase")
                    /*      */
                    /*      */
                    /*           */UseCaseGenerator.createUsesCaseForRepo(usecaseDir, domainRepoDir, input1)
                    /*           */UseCaseGenerator.createMainUseCaseFile(usecaseDir, input1, domainRepoDir)
                    /**/
                    /**/
                    /*  */val dataDir = FileUtil.createDirectory(newDir, "data")
                    /*  */
                    /*  */
                    /*      */val dataModelDir = FileUtil.createDirectory(dataDir, "model")
                    /*      */
                    /*      */
                    /*           */val dataClassName = DataClassGenerator.createDataClassFile(dataModelDir, json, input1, createCaching)
                    /*      */
                    /*      */
                    /*      */val dataRepoDir = FileUtil.createDirectory(dataDir, "repository")
                    /*      */
                    /*      */
                    /*           */RepositoryImplGenerator.createRepositoryFiles(dataRepoDir, input1, domainRepoDir.url, createCaching, "","")
                    /*      */
                    /*      */
                    /*      */val sourceDir = FileUtil.createDirectory(dataDir, "source")
                    /*      */
                    /*      */
                    /*           */val localSource = FileUtil.createDirectory(sourceDir, "local")
                    /*           */
                    /*           */
                    /*                 */if(createCaching){

                    /*                       */val daoDir = FileUtil.createDirectory(localSource, "dao")
                    /*                       */DaoGenerator.createFragmentFiles(daoDir, input1, dataModelDir, dataClassName)
                    /*                       */
                    /*                       */
                    /*                       */
                    /*                 */}
                    /*           */
                    /*           */
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
                    /*           */ViewModelGenerator.createViewModelFiles(viewmodelDir,input1, usecaseDir.url, createCaching)
                    /*      */
                    /*      */
                    /*      */val stateDir = FileUtil.createDirectory(presentationDir, "state")
                    /*      */
                    /*      */
                    /*           */UIStateGenerator.createUiStateFile(stateDir, input1, createCaching)



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

