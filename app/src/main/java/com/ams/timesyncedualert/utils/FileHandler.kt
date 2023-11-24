package com.ams.timesyncedualert.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import com.ams.timesyncedualert.model.Course
import java.io.File

class FileHandler {

    object CourseHandler {
        const val FILENAME = "/course_list.json"

        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)
            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        fun readCourseList(filePath: String): MutableList<Course> {
            val fileContent = java.io.File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        fun writeCourseList(courseList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(courseList)
            java.io.File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }

    }

    object SettingHandler {
        const val FILENAME = "/setting.json"

        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)

            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        fun readCourseList(filePath: String): MutableList<Course> {
            val fileContent = java.io.File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        fun writeCourseList(courseList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(courseList)
            java.io.File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }
    }
}
