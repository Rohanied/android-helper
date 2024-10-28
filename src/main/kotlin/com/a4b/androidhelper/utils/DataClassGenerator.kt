package com.a4b.androidhelper.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import java.lang.reflect.Type
import java.util.Locale

object DataClassGenerator {

    var dataClassName = ""
    var isFirstRecursiveCall = true

    fun toCamelCase(input: String): String {
        return input.split("_").mapIndexed { index, word ->
            if (index == 0) word.lowercase() else word.capitalise()
        }.joinToString("")
    }

    // A map to keep track of generated class names to avoid duplicates
    val generatedClasses = mutableSetOf<String>()

    // Function to recursively generate data classes
    fun generateDataClassesFromJson(json: String, className: String = "GeneratedClass"): String {
        val gson = Gson()
        val mapType: Type = object : TypeToken<Map<String, Any?>>() {}.type

        var isFirstDataKeyFound = false

        // Parse JSON string into a map
        val map: Map<String, Any?> = gson.fromJson(json, mapType)

        // Avoid generating the same class twice
        if (!generatedClasses.add(className)) return ""

        val builder = StringBuilder()
        val childClasses = StringBuilder()

        // Append the data class for the current JSON object
        if(isFirstRecursiveCall){
            builder.append("data class ${className}Response(\n\n")
            isFirstRecursiveCall = false
        }else{
            builder.append("data class ${className}(\n\n")
        }

        map.forEach { (key, value) ->
            var camelCaseKey = toCamelCase(key)

            if (camelCaseKey == "data" && !isFirstDataKeyFound) {
                camelCaseKey = "${className.first().lowercase()+className.substring(1)}Data"
                isFirstDataKeyFound = true
                dataClassName = camelCaseKey.first().uppercase()+camelCaseKey.substring(1)
            }

            val kotlinType = when (value) {
                is String -> "String?"
                is Int -> "Int?"
                is Double -> "Double?"
                is Float -> "Float?"
                is Boolean -> "Boolean?"
                is Map<*, *> -> {
                    // Generate a nested class name based on the key
                    val childClassName = camelCaseKey.capitalise()
                    // Recursively generate the child class and append it to childClasses
                    childClasses.append(generateDataClassesFromJson(gson.toJson(value), childClassName))
                    "$childClassName?" // Use the child class name as the type
                }

                is List<*> -> {
                    // Check if the list contains a map (object), if so handle nested list of objects
                    if (value.isNotEmpty() && value.first() is Map<*, *>) {
                        val childClassName = camelCaseKey.capitalise() + "Item"
                        childClasses.append(generateDataClassesFromJson(gson.toJson(value.first()), childClassName))
                        "List<$childClassName?>?"
                    } else if (value.isNotEmpty()) {
                        val kotlinType = when (value.first()) {
                            is String -> "String?"
                            is Number -> "Int?" // This can be expanded for Double, Long, etc.
                            is Boolean -> "Boolean?"
                            else -> "String?"
                        }
                        "List<$kotlinType?>?"
                    } else {
                        "List<Any?>?"
                    }
                }

                else -> "Any?"
            }
            builder.append("    @SerializedName(\"$key\")\n")
            if(key == "primaryKey"){
                builder.append("    @PrimaryKey(autoGenerate = true)\n")
                builder.append("    val $camelCaseKey: Long = 0,\n\n")
            }else {
                builder.append("    var $camelCaseKey: $kotlinType = null,\n\n")
            }
        }
        builder.append(") : Serializable\n\n")

        // Return the parent class followed by any child classes
        return builder.toString() + childClasses.toString()
    }


    fun createDataClassFile(
        baseDir: VirtualFile,
        json: String,
        input1: String,
        shouldCreateEntity: Boolean = false
    ): String {

        val packageName = baseDir.url.substring(baseDir.url.indexOf("com"), baseDir.url.length).replace("/", ".")

        val featureName = input1[0].uppercase() + input1.substring(1)

        val className = "${featureName}Response"

        dataClassName = featureName

        val entityCode = if (shouldCreateEntity) {
            """
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "${camelToSnake(dataClassName)}_data_table")

"""
        }else {
            "\n\n"
        }

        val updatedJson = if(shouldCreateEntity){
            addKeyValueToJson(json, "primaryKey", 0.toDouble())
        } else json

        val generatedClass = generateDataClassesFromJson(
            updatedJson,
            featureName
        )
        val content =
            "package $packageName\n\nimport com.google.gson.annotations.SerializedName\nimport java.io.Serializable${entityCode}${generatedClass}".trimIndent()

        FileUtil.createFileWithContent(baseDir, "${className}.kt", content)

        return dataClassName
    }

    fun String.capitalise(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun camelToSnake(camelCase: String): String {
        return camelCase.replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()
    }

    fun addKeyValueToJson(jsonString: String, key: String, value: Any): String {
        val jsonElement = Json.parseToJsonElement(jsonString) // Parse the JSON string to JsonElement
        val jsonObject = jsonElement.jsonObject // Convert JsonElement to JsonObject

        // Convert the value to a JsonElement (JsonPrimitive in most cases)
        val jsonValue = when (value) {
            is String -> JsonPrimitive(value)
            is Int -> JsonPrimitive(value)
            is Boolean -> JsonPrimitive(value)
            is Double -> JsonPrimitive(value)
            else -> JsonPrimitive(value.toString()) // Default to string if type is unknown
        }

        // Create a mutable copy of the original JsonObject
        val updatedJsonObject = JsonObject(jsonObject + (key to jsonValue))

        // Convert the updated JsonObject back to a JSON string
        return updatedJsonObject.toString()
    }

}