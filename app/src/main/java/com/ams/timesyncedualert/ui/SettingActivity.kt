package com.ams.timesyncedualert.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.ui.fragment.HomeFragment
import com.ams.timesyncedualert.ui.fragment.ScheduleFragment
import com.ams.timesyncedualert.ui.fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity(val supportFragmentManager: FragmentManager) : ComponentActivity() {
    private lateinit var buttonTimeChange: Button
    private lateinit var editTextMinute: EditText
    private lateinit var editTextSecond: EditText
    private lateinit var switchReminder: SwitchCompat
    private var minute: Int = 0;
    private var second: Int = 0;
    private lateinit var bottomNavigation: BottomNavigationView

    @SuppressLint("MissingInflatedId")
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
                    // 切换到 HomeFragment
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.navigation_Schedule -> {
                    // 切换到 ScheduleFragment
                    replaceFragment(ScheduleFragment())
                    true
                }

                R.id.navigation_Setting -> {
                    // 切换到 SettingFragment
                    replaceFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            androidx.fragment.R.id.fragment_container_view_tag,
            fragment
        ) // R.id.fragment_container 是你的 Fragment 容器的 ID
        transaction.addToBackStack(null)
        transaction.commit()
    }
}