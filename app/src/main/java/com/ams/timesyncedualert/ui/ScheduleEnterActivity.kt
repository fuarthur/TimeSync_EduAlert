package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.ams.timesyncedualert.R
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.utils.FileHandler

/**
 * Activity for entering schedule details.
 */
class ScheduleEnterActivity : ComponentActivity() {
    private val mClassNameEnter by lazy { findViewById<EditText>(R.id.class_name_enter) }
    private val mClassPeriodEnter by lazy { findViewById<EditText>(R.id.class_period_enter) }
    private val mClassWeekdayEnter by lazy { findViewById<EditText>(R.id.class_weekday_enter) }
    private val mClassClassroomEnter by lazy { findViewById<EditText>(R.id.class_classroom_enter) }
    private val mScheduleEditEnterButton by lazy { findViewById<Button>(R.id.schedule_edit_enter_button) }
    private val mScheduleEditBackButton by lazy { findViewById<Button>(R.id.schedule_edit_back_button) }
    private lateinit var courseList: MutableList<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_enter)

        // Set click listener for back button
        mScheduleEditBackButton.setOnClickListener {
            navigateToSchedule()
        }

        val context: Context = this
        courseList = FileHandler.CourseHandler.readCourseList(context.filesDir.toString())

        // Set click listener for enter button
        mScheduleEditEnterButton.setOnClickListener {
            if (isEnterLegal(
                    mClassNameEnter.text.toString(),
                    mClassPeriodEnter.text.toString().toInt(),
                    mClassWeekdayEnter.text.toString().toInt(),
                    mClassClassroomEnter.text.toString()
                )
            ) {
                val courseAdd: Course = Course(
                    mClassNameEnter.text.toString(),
                    mClassPeriodEnter.text.toString().toInt(),
                    mClassWeekdayEnter.text.toString().toInt(),
                    mClassClassroomEnter.text.toString(),
                )
                courseList.add(courseAdd)
                Log.d("user", courseList.toString())
                FileHandler.CourseHandler.writeCourseList(courseList, context.filesDir.toString())
                Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show()
                navigateToSchedule()
            }
        }
    }

    /**
     * Navigates to the Schedule Activity.
     */
    private fun navigateToSchedule() {
        val intent = Intent(this, ScheduleActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Checks if the entered details are legal.
     * @param name The name of the course.
     * @param period The period of the course.
     * @param weekDay The weekday of the course.
     * @param classroom The classroom of the course.
     * @return True if the entered details are legal, false otherwise.
     */
    private fun isEnterLegal(name: String, period: Int, weekDay: Int, classroom: String): Boolean {
        val isNameNotBlank = name.isNotBlank()
        val isNameLengthValid = name.length in 1..20
        val isPeriodValid = period in 1..10
        val isWeekDayValid = weekDay in 1..5
        val isClassroomNotBlank = classroom.isNotBlank()

        if (!isNameNotBlank) {
            Toast.makeText(this, R.string.valid_name, Toast.LENGTH_SHORT).show()
        }

        if (!isNameLengthValid) {
            Toast.makeText(this, R.string.name_length, Toast.LENGTH_SHORT).show()
        }

        if (!isPeriodValid) {
            Toast.makeText(this, R.string.valid_period, Toast.LENGTH_SHORT).show()
        }

        if (!isWeekDayValid) {
            Toast.makeText(this, R.string.valid_weekday, Toast.LENGTH_SHORT).show()
        }

        if (!isClassroomNotBlank) {
            Toast.makeText(this, R.string.valid_classroom, Toast.LENGTH_SHORT).show()
        }

        return isNameNotBlank && isNameLengthValid && isPeriodValid && isWeekDayValid && isClassroomNotBlank
    }
}
