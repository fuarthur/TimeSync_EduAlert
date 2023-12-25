package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.utils.FileHandler
import com.ams.timesyncedualert.utils.NotificationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomepageActivity : AppCompatActivity() {
    private val mCountdown by lazy { findViewById<TextView>(R.id.countdown) }
    private val mNextPeriod1 by lazy { findViewById<TextView>(R.id.next_period1) }
    private val mNextPeriod2 by lazy { findViewById<TextView>(R.id.next_period2) }
    private val mNextPeriod3 by lazy { findViewById<TextView>(R.id.next_period3) }
    private var currentPeriod: Int = 1
    private val context: Context = this
    private lateinit var timeTable: Map<String, List<Int>>
    private lateinit var courseList: MutableList<Course>
    private lateinit var bottomNavigation: BottomNavigationView
    private var timer: Timer? = null
    private val timePreset = 10 // Assuming this is a constant value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        setupBottomNavigation()
        getTimeTableAndCourseList()
        updateCurrentPeriod()
        updateUI()
        countDownTick()
    }

    private fun setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navi)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_Home -> item.setIcon(
                    getIconResource(
                        item,
                        R.drawable.home_selected,
                        R.drawable.home_idle
                    )
                )
                R.id.navigation_Schedule -> navigateToSchedule()
                R.id.navigation_Setting -> navigateToSetting()
            }
            true
        }
    }

    private fun getIconResource(item: MenuItem, selectedRes: Int, idleRes: Int): Int {
        return if (item.isChecked) selectedRes else idleRes
    }

    private fun updateUI() {
        mCountdown.text = timeDiff().toString()
        val n1Course = getNextCourseIfExist(currentPeriod - 1)
        val n2Course = getNextCourseIfExist(currentPeriod)
        val n3Course = getNextCourseIfExist(currentPeriod + 1)
        setNextPeriodText(mNextPeriod1, n1Course)
        setNextPeriodText(mNextPeriod2, n2Course)
        setNextPeriodText(mNextPeriod3, n3Course)
    }

    private fun setNextPeriodText(view: TextView, course: Course?) {
        view.text = if (course == null) "N/A" else formatCourseText(course)
    }

    private fun formatCourseText(course: Course): String {
        return "${course.name} at ${course.classroom}"
    }

    private fun updateCurrentPeriod() {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        var cPeriod = -1
        for ((period, time) in timeTable) {
            val periodHour = time[0]
            val periodMinute = time[1]
            if ((hour < periodHour) || (hour == periodHour && minute < periodMinute)) {
                cPeriod = period.toInt()
                break
            }
        }
        currentPeriod = cPeriod
    }

    private fun navigateToSchedule() {
        startActivity(Intent(this, ScheduleActivity::class.java))
        finish()
    }

    private fun navigateToSetting() {
        startActivity(Intent(this, SettingActivity::class.java))
        finish()
    }

    private fun timeDiff(): Int {
        val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val currentTimeNum = hour * 60 + minute
        if (getNextCourseIfExist(currentPeriod) == null) {
            return -1
        }
        val hourTime = timeTable[currentPeriod.toString()]!![0]
        val minuteTime = timeTable[currentPeriod.toString()]!![1]
        val nextTimeNum = hourTime * 60 + minuteTime
        return nextTimeNum - currentTimeNum
    }

    private fun countDownTick() {
        val countdownValue = timeDiff()
        if (countdownValue <= 0) {
            mCountdown.text = "N/A"
            return
        }

        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                var countdown = countdownValue

                override fun run() {
                    runOnUiThread {
                        updateCountdownDisplay(countdown)
                        countdown--
                        if (countdown <= 0) cancel()
                    }
                }
            }, 0, 60 * 1000)
        }
    }

    private fun updateCountdownDisplay(minutes: Int) {
        val displayText = "$minutes minutes"
        mCountdown.text = displayText
        if (minutes == timePreset) sendNotification()
    }

    private fun sendNotification() {
        val course = courseList.getOrNull(currentPeriod)
        course?.let {
            val title = "${it.name} at room ${it.classroom}"
            val content = "Your class ${it.name} will begin in $timePreset minutes."
            NotificationHelper.sendNotification(context, title, content)
        }
    }

    private fun getNextCourseIfExist(tPeriod: Int): Course? {
        return courseList.find { it.period == tPeriod }
    }

    private fun getTimeTableAndCourseList() {
        val now = Calendar.getInstance()
        val weekday: Int = (now.get(Calendar.DAY_OF_WEEK) - 1)
        timeTable = when (weekday) {
            1 -> FileHandler.AssetsHandler.readJsonFile(context, "schedule_monday.json")
            2 -> FileHandler.AssetsHandler.readJsonFile(context, "schedule_tuesday.json")
            else -> FileHandler.AssetsHandler.readJsonFile(context, "schedule_rest.json")
        }
        courseList = FileHandler.CourseHandler.filterCoursesByWeekDay(
            FileHandler.CourseHandler.readCourseList(context.filesDir.toString()),
            weekday
        )
    }
}
