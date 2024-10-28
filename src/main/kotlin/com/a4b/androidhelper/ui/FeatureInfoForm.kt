package com.a4b.androidhelper.ui;

import com.a4b.androidhelper.ui.SuccessDialogBox.SuccessDialogBox
import com.a4b.androidhelper.utils.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Dimension
import java.awt.event.ItemEvent
import javax.swing.*;
import kotlin.system.exitProcess

public class FeatureInfoForm(private val project: Project, private val selectedDirectory: VirtualFile) : JFrame(){
    private lateinit var contentPane: JPanel
    private lateinit var featureName: JTextField
    private lateinit var enableCachingCheckBox: JCheckBox
    private lateinit var jsonText: JTextArea
    private lateinit var apiClientPackage: JTextField
    private lateinit var appDatabaseProviderPackage: JTextField
    private lateinit var gsonImplementationComGoogleTextArea: JTextArea
    private lateinit var okayButton: JButton
    private lateinit var cancelButton: JButton
    private lateinit var jsonScrollPane : JScrollPane
    private lateinit var jsonLabel : JLabel
    private lateinit var apiClientLabel : JLabel
    private lateinit var dependencyLabel : JLabel
    private lateinit var appDatabaseLabel : JLabel
    private lateinit var separator1 : JSeparator
    private lateinit var separator2 : JSeparator

    private var fullDimension : Dimension? = null

    init {
        title = "Android Helper"
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setContentPane(contentPane)
        pack()

        enableCachingCheckBox.isSelected = true

        enableCachingCheckBox.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                showCachingFields()
            } else if (event.stateChange == ItemEvent.DESELECTED) {
                hideCachingFields()
            }
        }

        okayButton.addActionListener {
            onSaveClick()
        }

    }


    private fun hideCachingFields(){

        apiClientPackage.isVisible = false
        appDatabaseProviderPackage.isVisible = false
        gsonImplementationComGoogleTextArea.isVisible = false
        jsonText.isVisible = false
        jsonScrollPane.isVisible = false
        jsonLabel.isVisible = false
        apiClientLabel.isVisible = false
        dependencyLabel.isVisible = false
        appDatabaseLabel.isVisible = false
        separator2.isVisible = false
        separator1.isVisible = false

        resizeWindow()
    }

    private fun showCachingFields(){

        apiClientPackage.isVisible = true
        appDatabaseProviderPackage.isVisible = true
        gsonImplementationComGoogleTextArea.isVisible = true
        jsonText.isVisible = true
        jsonScrollPane.isVisible = true
        jsonLabel.isVisible = true
        apiClientLabel.isVisible = true
        dependencyLabel.isVisible = true
        appDatabaseLabel.isVisible = true
        separator2.isVisible = true
        separator1.isVisible = true

        resizeWindow()
    }

    private fun resizeWindow() {
        pack()
        revalidate() // Refresh layout
        repaint() // Redraw UI components
    }

    private fun onSaveClick(){

        val isCachingEnabled = enableCachingCheckBox.isSelected
        val featureName = featureName.text
        val jsonText = jsonText.text
        val apiClientPackage = apiClientPackage.text
        val appDatabaseProviderPackage = appDatabaseProviderPackage.text

        ApplicationManager.getApplication().runWriteAction {
            try {
                val newDir = FileUtil.createDirectory(selectedDirectory, featureName)
                /**/
                /**/
                /*  */val domainDir = FileUtil.createDirectory(newDir, "domain")
                /*  */
                /*  */
                /*      */val domainModelDir = FileUtil.createDirectory(domainDir, "model")
                /*      */val domainRepoDir = FileUtil.createDirectory(domainDir, "repository")
                /*      */
                /*      */
                /*           */RepositoryGenerator.createRepositoryFiles(domainRepoDir, featureName,isCachingEnabled)
                /*      */
                /*      */
                /*      */val usecaseDir = FileUtil.createDirectory(domainDir, "usecase")
                /*      */
                /*      */
                /*           */UseCaseGenerator.createUsesCaseForRepo(usecaseDir, domainRepoDir, featureName)
                /*           */UseCaseGenerator.createMainUseCaseFile(usecaseDir, featureName, domainRepoDir)
                /**/
                /**/
                /*  */val dataDir = FileUtil.createDirectory(newDir, "data")
                /*  */
                /*  */
                /*      */val dataModelDir = FileUtil.createDirectory(dataDir, "model")
                /*      */
                /*      */
                /*           */var dataClassName = ""
                /*           */if(isCachingEnabled){
                /*           */     dataClassName = DataClassGenerator.createDataClassFile(dataModelDir, jsonText, featureName, isCachingEnabled)
                /*           */}
                /*           */
                /*      */
                /*      */
                /*      */val dataRepoDir = FileUtil.createDirectory(dataDir, "repository")
                /*      */
                /*      */
                /*           */RepositoryImplGenerator.createRepositoryFiles(dataRepoDir, featureName, domainRepoDir.url, isCachingEnabled, apiClientPackage, appDatabaseProviderPackage)
                /*      */
                /*      */
                /*      */val sourceDir = FileUtil.createDirectory(dataDir, "source")
                /*      */
                /*      */
                /*           */val localSource = FileUtil.createDirectory(sourceDir, "local")
                /*           */
                /*           */
                /*                 */if(isCachingEnabled){

                    /*                       */val daoDir = FileUtil.createDirectory(localSource, "dao")
                    /*                       */DaoGenerator.createFragmentFiles(daoDir, featureName, dataModelDir, dataClassName)
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
                /*           */FragmentGenerator.createFragmentFiles(viewDir, featureName)
                /*      */
                /*      */
                /*           */val adapterDir = FileUtil.createDirectory(uiDir, "adapter")
                /*  */
                /*  */
                /*      */val viewmodelDir = FileUtil.createDirectory(presentationDir, "viewmodel")
                /*      */
                /*      */
                /*           */ViewModelGenerator.createViewModelFiles(viewmodelDir,featureName, usecaseDir.url, isCachingEnabled)
                /*      */
                /*      */
                /*      */val stateDir = FileUtil.createDirectory(presentationDir, "state")
                /*      */
                /*      */
                /*           */UIStateGenerator.createUiStateFile(stateDir, featureName, isCachingEnabled)


                val successMessage = "AppMode : \n${InjectionsGenerator.generateAppModule(featureName, isCachingEnabled, domainRepoDir)}\n\nViewModelModule :\n${InjectionsGenerator.generateViewModel(featureName)}"

                ApplicationManager.getApplication().invokeLater {
                    dispose()
                    val dialog = SuccessDialogBox(InjectionsGenerator.generateAppModule(featureName, isCachingEnabled, domainRepoDir), InjectionsGenerator.generateViewModel(featureName))
                    dialog.pack()
                    dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
                    dialog.isVisible = true
                    exitProcess(0)
                }


            } catch (e: Exception) {
                ApplicationManager.getApplication().invokeLater {
                    Messages.showErrorDialog(project, "Failed to create directory or file: ${e.message}", "Error")
                    println(e.stackTrace)
                }
            }
        }

    }

}
