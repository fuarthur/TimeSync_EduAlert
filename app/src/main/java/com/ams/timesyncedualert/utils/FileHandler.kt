package com.ams.timesyncedualert.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import com.ams.timesyncedualert.model.Course
import java.io.File

/**
 * Utility class for handling files related to courses and settings.
 */
class FileHandler {

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
        fun readSettings(filePath: String): MutableList<Course> {
            val fileContent = File(filePath + FILENAME).readText(Charset.defaultCharset())
            val listType = object : TypeToken<MutableList<Course>>() {}.type
            return Gson().fromJson(fileContent, listType)
        }

        /**
         * Writes the settings to the specified file path.
         *
         * @param settingsList The list of settings to be written to the file.
         * @param filePath The path to the directory where the file should be written.
         */
        fun writeSettings(settingsList: MutableList<Course>, filePath: String) {
            val json = Gson().toJson(settingsList)
            File(filePath + FILENAME).writeText(json, Charset.defaultCharset())
        }
    }
}
