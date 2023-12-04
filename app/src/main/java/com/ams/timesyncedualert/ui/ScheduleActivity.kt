package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.ams.timesyncedualert.R
import android.widget.Button
import android.widget.TextView
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.utils.FileHandler

class ScheduleActivity : ComponentActivity() {
    private val mScheduleMonday by lazy { findViewById<Button>(R.id.Schedule_monday) }
    private val mScheduleTuesday by lazy { findViewById<Button>(R.id.Schedule_tuesday) }
    private val mScheduleWednesday by lazy { findViewById<Button>(R.id.Schedule_wednesday) }
    private val mScheduleThursday by lazy { findViewById<Button>(R.id.Schedule_thursday) }
    private val mScheduleFriday by lazy { findViewById<Button>(R.id.Schedule_friday) }
    private val mScheduleSetting by lazy { findViewById<Button>(R.id.Schedule_setting) }
    private val mPeriod1 by lazy { findViewById<TextView>(R.id.period1) }
    private val mPeriod2 by lazy { findViewById<TextView>(R.id.period2) }
    private val mPeriod3 by lazy { findViewById<TextView>(R.id.period3) }
    private val mPeriod4 by lazy { findViewById<TextView>(R.id.period4) }
    private val mPeriod5 by lazy { findViewById<TextView>(R.id.period5) }
    private val mPeriod6 by lazy { findViewById<TextView>(R.id.period6) }
    private val mPeriod7 by lazy { findViewById<TextView>(R.id.period7) }
    private val mPeriod8 by lazy { findViewById<TextView>(R.id.period8) }
    private val mPeriod9 by lazy { findViewById<TextView>(R.id.period9) }
    private val mPeriod10 by lazy { findViewById<TextView>(R.id.period10) }
    private lateinit var courseList: MutableList<Course>
    private var weekDay: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        mScheduleSetting.setOnClickListener {
            navigateToScheduleEnter()
        }

        val dayButtons =
            listOf(mScheduleMonday, mScheduleTuesday, mScheduleWednesday, mScheduleThursday, mScheduleFriday)

        for ((index, button) in dayButtons.withIndex()) {
            button.setOnClickListener {
                weekDay = index + 1
                updateUI()
            }
        }
        updateUI()
    }

    private fun updateUI() {
        val context: Context = this

        courseList = FileHandler.CourseHandler.filterCoursesByWeekDay(
            FileHandler.CourseHandler.readCourseList(context.filesDir.toString()),
            weekDay
        )

        val periodTextViews =
            listOf(mPeriod1, mPeriod2, mPeriod3, mPeriod4, mPeriod5, mPeriod6, mPeriod7, mPeriod8, mPeriod9, mPeriod10)

        for (course in courseList) {
            val period = course.period
            if (period in 1..10) {
                periodTextViews[period - 1].text = course.name
            }
        }
    }

    private fun navigateToScheduleEnter() {
        val intent = Intent(this, ScheduleEnterActivity::class.java)
        startActivity(intent)
        finish()
    }
}