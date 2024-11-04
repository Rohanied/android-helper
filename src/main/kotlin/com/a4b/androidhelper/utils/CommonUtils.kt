package com.a4b.androidhelper.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

object CommonUtils {

    fun isValidJson(jsonString: String): Boolean {
        if (jsonString.isBlank()) return false

        return try {
            JsonParser.parseString(jsonString) // Parse the string to a JsonElement
            true // If parsing succeeds, it's valid JSON
        } catch (e: JsonSyntaxException) {
            false // If parsing fails, it's not valid JSON
        }
    }

    fun hasSingleDataProperty(jsonString: String): Boolean {
        if (jsonString.isBlank()) return false

        return try {
            val jsonElement = JsonParser.parseString(jsonString)
            if (jsonElement is JsonObject) {
                // Check if there is only one property named "data"
                jsonElement.keySet().size == 1 && jsonElement.has("data") && jsonElement["data"].isJsonObject
            } else {
                false
            }
        } catch (e: JsonSyntaxException) {
            false // Invalid JSON syntax
        }
    }

}