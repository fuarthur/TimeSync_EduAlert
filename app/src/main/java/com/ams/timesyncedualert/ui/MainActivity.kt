package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.utils.FileHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover)
        Log.d("user", "enter cover")

        // Initialize data file
        val context: Context = this
        FileHandler.CourseHandler.checkAndCreateFile(context.filesDir.toString())
        FileHandler.SettingHandler.checkAndCreateFile(context.filesDir.toString())
        Log.d("user", "23")

        Handler(Looper.myLooper()!!).postDelayed({
            Log.d("user", "enter loop")
            navigateToHomepage()
        }, resources.getInteger(R.integer.cover_delay_milis).toLong())
    }

    private fun navigateToHomepage() {
        Log.d("user", "enter homepage 0")
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

}
