package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

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
        getTimeTableAndCourseList()
        updateCurrentPeriod()
        updateUI()
        countDownTick()
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
        view.text = if (course == null) {
            "N/A"
        } else {
            formatCourseText(course)
        }
    }

    private fun countDownTick() {
        val countdownValue = timeDiff()

        if (countdownValue != -1) {
            val timer = Timer()
            val updateCountdownTask = object : TimerTask() {
                var countdown = countdownValue

                // val timePreset = FileHandler.SettingHandler.readSettings(context.filesDir.toString())[0].toString().toInt()
                val timePreset = 10

                override fun run() {
                    runOnUiThread {
                        val minutes = countdown - 1
                        val displayText = "$minutes minutes"
                        mCountdown.text = displayText

                        if (minutes == timePreset) {
                            val course = courseList[currentPeriod]
                            val title = course.name + " at room " + course.classroom
                            val content = "Your class " + course.name + " will begin in " + timePreset + " minutes."
                            NotificationHelper.sendNotification(context, title, content)
                        }
                    }

                    countdown-- // 每次执行任务，倒计时减1

                    if (countdown <= 0) {
                        timer.cancel() // todo: 倒计时结束后开始下一个任务
                        getTimeTableAndCourseList()
                        updateCurrentPeriod()
                        updateUI()
                        countDownTick()
                    }
                }
            }

            timer.scheduleAtFixedRate(updateCountdownTask, 0, 60 * 1000)
        } else {
            // 如果 timeDiff() 返回 -1，直接将 mCountdown.text 设置为 "N/A"
            mCountdown.text = "N/A"
        }
    }


    private fun formatCourseText(course: Course): String {
        return "${course.name} at ${course.classroom}"
    }

    private fun updateCurrentPeriod() {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        var cPeriod = -1
        var debugV = 0
        for ((period, time) in timeTable) {
            debugV++
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
        val intent = Intent(this, ScheduleActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSetting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun timeDiff(): Int {

        // 获取 Calendar 对象的实例
        val calendar = Calendar.getInstance()

        // 通过 Calendar 对象获取当前的小时、分钟和秒
        val hour = calendar.get(Calendar.HOUR_OF_DAY) // 获取小时，24小时制
        val minute = calendar.get(Calendar.MINUTE)      // 获取分钟

        // 构造时间字符串
        val currentTimeNum = hour * 60 + minute
        if (getNextCourseIfExist(currentPeriod) == null) {
            return -1
        }
        val hourTime = timeTable[currentPeriod.toString()]!![0]
        val minuteTime = timeTable[currentPeriod.toString()]!![1]
        val nextTimeNum = hourTime * 60 + minuteTime
        return currentTimeNum - nextTimeNum
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

    private fun getNextCourseIfExist(tPeriod: Int): Course? {
        for (course in courseList) {
            if (course.period == tPeriod) {
                return course
            }
        }
        return null
    }
}
