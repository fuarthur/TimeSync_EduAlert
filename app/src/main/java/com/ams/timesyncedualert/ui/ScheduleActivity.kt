package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.utils.FileHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

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
    private val context: Context = this
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var homeItem: MenuItem
    private lateinit var scheduleItem: MenuItem
    private lateinit var settingItem: MenuItem
    private var primaryColor: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blackColor = ContextCompat.getColor(this, R.color.black)
        primaryColor = ContextCompat.getColor(this, R.color.color_primary)
        setContentView(R.layout.activity_schedule)

        mScheduleSetting.setOnClickListener {
            navigateToScheduleEnter()
        }


        courseList = FileHandler.CourseHandler.readCourseList(context.filesDir.toString())

        val dayButtons =
            listOf(mScheduleMonday, mScheduleTuesday, mScheduleWednesday, mScheduleThursday, mScheduleFriday)

        for ((index, button) in dayButtons.withIndex()) {
            button.setOnClickListener {
                weekDay = index + 1
                updateUI()
            }
        }
        updateUI()
        bottomNavigation = findViewById(R.id.bottom_navi)
        homeItem = bottomNavigation.menu.findItem(R.id.navigation_Home)
        scheduleItem = bottomNavigation.menu.findItem(R.id.navigation_Schedule)
        settingItem = bottomNavigation.menu.findItem(R.id.navigation_Setting)

        updatenavi()

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_Home -> {
                    updatenavi()
                    navigateToHomepage()
                }
                R.id.navigation_Schedule -> {
                    updatenavi()
                }
                R.id.navigation_Setting -> {
                    updatenavi()
                    navigateToSetting()
                }
            }
            true
        }

        for ((index, button) in dayButtons.withIndex()) {
            button.setOnClickListener {
                resetButtonColors(dayButtons)
                weekDay = index + 1
                updateUI()
                button.setBackgroundColor(blackColor)
            }
        }
    }

    private fun updateUI() {
        val context: Context = this
        courseList = FileHandler.CourseHandler.filterCoursesByWeekDay(
            FileHandler.CourseHandler.readCourseList(context.filesDir.toString()),
            weekDay
        )

        val periodTextViews =
            listOf(mPeriod1, mPeriod2, mPeriod3, mPeriod4, mPeriod5, mPeriod6, mPeriod7, mPeriod8, mPeriod9, mPeriod10)

        for (view in periodTextViews) view.text = getString(R.string.free_period)

        for (course in courseList) {
            val period = course.period
            if (period in 1..10) {
                val displayText = "${course.name} in Room ${course.classroom}"
                periodTextViews[period - 1].text = displayText
            }
        }
    }

    private fun navigateToScheduleEnter() {
        val intent = Intent(this, ScheduleEnterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHomepage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSetting() {
        val intent = Intent(this, SettingActivity()::class.java)
        startActivity(intent)
        finish()
    }
    private fun updatenavi() {
        homeItem.setIcon(R.drawable.home_idle)
        scheduleItem.setIcon(R.drawable.schedule_select)
        settingItem.setIcon(R.drawable.setting_idle)
    }


    private fun resetButtonColors(buttons: List<Button>) {
        buttons.forEach { it.setBackgroundColor(primaryColor) }
    }

}