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

//        debug-only
//        val fileDir = applicationContext.filesDir
//        fileDir?.let { dir ->
//            val fileList = dir.listFiles()
//            if (fileList != null) {
//                for (file in fileList) {
//                    file.delete()
//                }
//            }
//        }

        // Initialize data file
        val context: Context = this
        FileHandler.CourseHandler.checkAndCreateFile(context.filesDir.toString())
        FileHandler.SettingHandler.checkAndCreateFile(context.filesDir.toString())

        Handler(Looper.myLooper()!!).postDelayed({
            navigateToHomepage()
        }, resources.getInteger(R.integer.cover_delay_milis).toLong())
    }

    private fun navigateToHomepage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

}
