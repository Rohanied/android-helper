package com.a4b.androidhelper.ui
import javax.swing.*
import java.awt.Dimension

class Window {

    fun createAndShowWindow() {
        // Create the main frame (window)
        val frame = JFrame("API Configuration")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        frame.extendedState = JFrame.MAXIMIZED_BOTH

        // Create main panel with vertical layout
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        // "Enter the feature name" label and input
        panel.add(JLabel("Enter the feature name:"))
        val featureNameInput = JTextField()
        featureNameInput.maximumSize = Dimension(Int.MAX_VALUE, featureNameInput.preferredSize.height)
        panel.add(featureNameInput)

        // Divider
        panel.add(JSeparator())

        // Enable caching checkbox
        val enableCachingCheckbox = JCheckBox("Enable caching")
        panel.add(enableCachingCheckbox)

        // "Enter the JSON of API response" label and input
        panel.add(JLabel("Enter the JSON of API response:"))
        val jsonApiResponseInput = JTextArea(10, 50)
        jsonApiResponseInput.lineWrap = true
        jsonApiResponseInput.wrapStyleWord = true
        val jsonScrollPane = JScrollPane(jsonApiResponseInput)
        jsonScrollPane.maximumSize = Dimension(Int.MAX_VALUE, 100)
        panel.add(jsonScrollPane)

        // Divider
        panel.add(JSeparator())

        // Dependencies info
        val dependenciesLabel = JLabel("<html><b>Make sure to add the following dependencies in your project:</b></html>")
        panel.add(dependenciesLabel)

        val dependenciesText = """
            
        Gson:
            implementation("com.google.code.gson:gson:x.y.z")
            
        Network:
            implementation("com.appsforbharat:network:x.y.z")
            
        Coroutines:
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:x.y.z")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:x.y.z")
        
        Lifecycle:
            implementation("androidx.lifecycle:lifecycle-extensions:x.y.z")
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:x.y.z")
            
    """.trimIndent()
        val dependenciesArea = JTextArea(dependenciesText)
        dependenciesArea.isEditable = false
        val dependenciesScrollPane = JScrollPane(dependenciesArea)
        dependenciesScrollPane.maximumSize = Dimension(Int.MAX_VALUE, 150)
        panel.add(dependenciesScrollPane)

        // Divider
        panel.add(JSeparator())

        // "Enter ApiClient Package" label and input
        panel.add(JLabel("Enter ApiClient Package:"))
        val apiClientInput = JTextField()
        apiClientInput.maximumSize = Dimension(Int.MAX_VALUE, apiClientInput.preferredSize.height)
        panel.add(apiClientInput)

        // Divider
        panel.add(JSeparator())

        // "Enter AppDatabaseProvider Package" label and input
        panel.add(JLabel("Enter AppDatabaseProvider Package:"))
        val appDatabaseProviderInput = JTextField()
        appDatabaseProviderInput.maximumSize = Dimension(Int.MAX_VALUE, appDatabaseProviderInput.preferredSize.height)
        panel.add(appDatabaseProviderInput)

        // Divider
        panel.add(JSeparator())

        // Submit and Cancel buttons
        val buttonPanel = JPanel()
        val submitButton = JButton("Submit")
        val cancelButton = JButton("Cancel")
        buttonPanel.add(submitButton)
        buttonPanel.add(cancelButton)
        panel.add(buttonPanel)

        // Add the panel to the frame's content pane
        frame.contentPane.add(panel)

        // Make the window visible
        frame.isVisible = true
    }
}

fun main(){
    SwingUtilities.invokeLater {
        Window3()
    }
}