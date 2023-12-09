package com.ams.timesyncedualert.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SwitchCompat
import com.ams.timesyncedualert.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity : ComponentActivity() {
    private lateinit var buttonTimeChange: Button
    private lateinit var editTextMinute: EditText
    private lateinit var editTextSecond: EditText
    private lateinit var switchReminder: SwitchCompat
    private var minute: Int = 0
    private var second: Int = 0
    private lateinit var bottomNavigation: BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        buttonTimeChange = findViewById(R.id.submit_button_time)
        editTextMinute = findViewById(R.id.minute_interact)
        editTextSecond = findViewById(R.id.second_interact)
        switchReminder = findViewById(R.id.switchOfReminder)

        buttonTimeChange.setOnClickListener {
        }

        bottomNavigation = findViewById(R.id.bottom_navi)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_Home -> {
                    navigateToHomepage()
                }

                R.id.navigation_Schedule -> {
                    navigateToSchedule()
                }

                R.id.navigation_Setting -> {

                }
            }

            when (item.itemId) {
                R.id.navigation_Home -> {
                    item.setIcon(if (item.isChecked) R.drawable.setting_select else R.drawable.setting_idle)
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
}