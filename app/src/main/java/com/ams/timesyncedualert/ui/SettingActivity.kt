package com.ams.timesyncedualert.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SwitchCompat
import com.ams.timesyncedualert.R

class SettingActivity : ComponentActivity() {
    private lateinit var buttonTimeChange: Button
    private lateinit var editTextMinute: EditText
    private lateinit var editTextSecond: EditText
    private lateinit var switchReminder: SwitchCompat
    private var minute: Int = 0;
    private var second: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        buttonTimeChange = findViewById(R.id.submit_button_time)
        editTextMinute = findViewById(R.id.minute_interact)
        editTextSecond = findViewById(R.id.second_interact)
        switchReminder = findViewById(R.id.switchOfReminder)

        buttonTimeChange.setOnClickListener {
        }

    }
}