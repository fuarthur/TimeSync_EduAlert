package com.ams.timesyncedualert.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.model.Setting
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Utility class for handling files related to courses and settings.
 */
class FileHandler(private val context: Context) {

    /**
     * Object responsible for handling operations related to courses, such as reading, writing, and filtering.
     */
    object CourseHandler {
        // Filename for the course list
        private const val FILENAME = "/course_list.json"

        /**
         * Checks if the file exists, creates it if not, and initializes it with an empty JSON array.
         *
         * @param filePath The path to the directory where the file should be created.
         */
        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)

            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        /**
         * Reads the course list from the specified file path.
         *
         * @param filePath The path to the directory where the file is located.
         * @return A mutable list of courses read from the file.
         */
        fun readCourseList(filePath: String): MutableList<Course> {
            val fileContent = File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        /**
         * Writes the course list to the specified file path.
         *
         * @param courseList The list of courses to be written to the file.
         * @param filePath The path to the directory where the file should be written.
         */
        fun writeCourseList(courseList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(courseList)
            File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }

        /**
         * Filters courses based on the specified week day.
         *
         * @param courses The list of courses to be filtered.
         * @param weekDay The target week day for filtering the courses.
         * @return A new list containing only the courses that occur on the specified week day.
         */
        fun filterCoursesByWeekDay(courses: MutableList<Course>, weekDay: Int): MutableList<Course> {
            val filteredCourses = mutableListOf<Course>()
            for (course in courses) {
                if (course.weekDay == weekDay) {
                    filteredCourses.add(course)
                }
            }
            return filteredCourses
        }
    }

    /**
     * Object responsible for handling operations related to settings, such as reading and writing.
     */
    object SettingHandler {
        // Filename for the settings
        private const val FILENAME = "/setting.json"

        /**
         * Checks if the file exists, creates it if not, and initializes it with an empty JSON array.
         *
         * @param filePath The path to the directory where the file should be created.
         */
        fun checkAndCreateFile(filePath: String) {
            val file = File(filePath + FILENAME)

            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]", Charset.defaultCharset())
            }
        }

        /**
         * Reads the settings from the specified file path.
         *
         * @param filePath The path to the directory where the file is located.
         * @return A mutable list of settings read from the file.
         */
        fun readSettings(filePath: String): MutableList<Setting> {
            val fileContent = File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Setting>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        /**
         * Writes the settings to the specified file path.
         *
         * @param settingsList The list of settings to be written to the file.
         * @param filePath The path to the directory where the file should be written.
         */
        fun writeSettings(settingsList: MutableList<Setting>, filePath: String) {
            val json = Gson().toJson(settingsList)
            File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }
    }

    /**
     * This object provides functions for handling assets.
     */
    object AssetsHandler {
        /**
         * Reads the content of a JSON file and returns it as a map of string keys and integer arrays.
         *
         * @param fileName The name of the JSON file to be read.
         * @return A map object with string keys and integer arrays as values. */
        fun readJsonFile(context: Context, fileName: String): Map<String, Array<Int>> {
            val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            val typeToken = object : TypeToken<Map<String, Array<Int>>>() {}.type
            return Gson().fromJson(json, typeToken)
        }

        /**
         * Convert period into time
         *
         * @param period The period needed to convert
         * @param weekDay The current weekDay
         * @return An array with hour and minute as values. */
        fun period2Time(context: Context, period: Int, weekDay: Int): Array<Int>? {
            val timeMap: Map<String, Array<Int>> = when (weekDay) {
                1 -> FileHandler.AssetsHandler.readJsonFile(context,"schedule_monday.json")
                2 -> FileHandler.AssetsHandler.readJsonFile(context,"schedule_tuesday.json")
                else -> FileHandler.AssetsHandler.readJsonFile(context,"schedule_rest.json")
            }
            return timeMap[period.toString()]
        }
    }
}
