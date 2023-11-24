package com.ams.timesyncedualert.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import com.ams.timesyncedualert.model.Course
import java.io.File

class FileHandler {

    object CourseHandler {
        // Filename for the course list
        private const val FILENAME = "/course_list.json"

        // Check if the file exists, create it if not, and initialize with an empty JSON array
        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)

            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        // Read the course list from the specified file path
        fun readCourseList(filePath: String): MutableList<Course> {
            val fileContent = File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        // Write the course list to the specified file path
        fun writeCourseList(courseList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(courseList)
            File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }

    }

    object SettingHandler {
        // Filename for the settings
        private const val FILENAME = "/setting.json"

        // Check if the file exists, create it if not, and initialize with an empty JSON array
        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)

            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        // Read the settings from the specified file path
        fun readCourseList(filePath: String): MutableList<Course> {
            val fileContent = File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        // Write the settings to the specified file path
        fun writeCourseList(courseList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(courseList)
            File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }
    }
}
