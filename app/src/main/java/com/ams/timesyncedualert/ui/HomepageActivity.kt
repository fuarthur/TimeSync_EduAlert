package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.model.Course
import com.ams.timesyncedualert.utils.FileHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class HomepageActivity : AppCompatActivity() {
    private val mCountdown by lazy { findViewById<TextView>(R.id.countdown) }
    private val mNextPeriod1 by lazy { findViewById<TextView>(R.id.next_period1) }
    private val mNextPeriod2 by lazy { findViewById<TextView>(R.id.next_period2) }
    private val mNextPeriod3 by lazy { findViewById<TextView>(R.id.next_period3) }
    private var currentPeriod: Int = 1
    private val context: Context = this
    private lateinit var courseList: MutableList<Course>
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
//            updateCurrentPeriod()
//            updateUI()

        bottomNavigation = findViewById(R.id.bottom_navi)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_Home -> {

                }

                R.id.navigation_Schedule -> {
                    // 切换到 ScheduleFragment
                    navigateToSchedule()
                }

                R.id.navigation_Setting -> {
                    // 切换到 SettingFragment
                    navigateToSetting()
                }
            }
            when (item.itemId) {
                R.id.navigation_Home -> {
                    item.setIcon(if (item.isChecked) R.drawable.home_selected else R.drawable.home_idle)
                }
            }
            true
        }
    }

    private fun updateUI() {
        mNextPeriod1.text = formatCourseText(courseList[currentPeriod - 1])
        mNextPeriod2.text = formatCourseText(courseList[currentPeriod])
        mNextPeriod3.text = formatCourseText(courseList[currentPeriod + 1])
    }

    private fun countDownTick() {
        // todo: WIP
        val timer = Timer()
        val updateCountdownTask = object : TimerTask() {
            // todo: read the following values
            var countdown = 60 // 初始倒计时时间，单位为分钟
            // val timePreset = FileHandler.SettingHandler.readSettings(context.filesDir.toString())[0].toString().toInt()
            val timePreset = 10

            override fun run() {
                runOnUiThread {
                    val minutes = countdown - 1
                    val displayText = "$minutes minutes"
                    mCountdown.text = displayText

                    if (minutes == timePreset) {
                        // todo: show notification
                    }
                }

                countdown-- // 每次执行任务，倒计时减1

                if (countdown <= 0) {
                    timer.cancel() // todo: 倒计时结束后开始下一个任务
                }
            }
        }

        timer.scheduleAtFixedRate(updateCountdownTask, 0, 60 * 1000)
    }


    private fun formatCourseText(course: Course): String {
        return "${course.name} at ${course.classroom}"
    }

    private fun updateCurrentPeriod() {
        val now = Calendar.getInstance()
        val weekday: Int = (now.get(Calendar.DAY_OF_WEEK) - 1)
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val timeMap = when (weekday) {
            1 -> FileHandler.AssetsHandler.readJsonFile("schedule_monday.json")
            2 -> FileHandler.AssetsHandler.readJsonFile("schedule_tuesday.json")
            else -> FileHandler.AssetsHandler.readJsonFile("schedule_rest.json")
        }

        var cPeriod = -1
        for ((period, time) in timeMap) {
            val periodHour = time[0]
            val periodMinute = time[1]
            if ((hour < periodHour) || (hour == periodHour && minute < periodMinute)) {
                cPeriod = period.toInt()
                break
            }
        }

        if (cPeriod != -1) {
            currentPeriod = cPeriod
        }


    }

    private fun navigateToSchedule() {
        val intent = Intent(this, ScheduleActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSetting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }
}

