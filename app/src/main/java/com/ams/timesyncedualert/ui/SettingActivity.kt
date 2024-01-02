package com.ams.timesyncedualert.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SwitchCompat
import com.ams.timesyncedualert.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity : ComponentActivity() {
    private lateinit var editTextMinute: EditText
    private lateinit var editTextSecond: EditText
    private lateinit var switchReminder: SwitchCompat
    private var minute: Int = 0
    private var second: Int = 0
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var homeItem: MenuItem
    private lateinit var scheduleItem: MenuItem
    private lateinit var settingItem: MenuItem




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wip)
        //buttonTimeChange = findViewById(R.id.submit_button_time)
        //editTextMinute = findViewById(R.id.minute_interact)
        //editTextSecond = findViewById(R.id.second_interact)
        //switchReminder = findViewById(R.id.switchOfReminder)



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
                    // 处理导航到 Home 页面的逻辑
                }
                R.id.navigation_Schedule -> {
                    updatenavi()
                    navigateToSchedule()
                    // 处理导航到 Schedule 页面的逻辑
                }
                R.id.navigation_Setting -> {
                    updatenavi()
                    // 处理导航到 Setting 页面的逻辑
                }
            }
            true
        }

    }
    private fun navigateToSchedule() {
        val intent = Intent(this, ScheduleActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHomepage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun updatenavi() {
        homeItem.setIcon(R.drawable.home_idle)
        scheduleItem.setIcon(R.drawable.schedule_idle)
        settingItem.setIcon(R.drawable.setting_select)
    }
}