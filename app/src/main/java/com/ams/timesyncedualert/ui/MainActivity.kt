package com.ams.timesyncedualert.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ams.timesyncedualert.R
import com.ams.timesyncedualert.utils.FileHandler
import com.ams.timesyncedualert.utils.NotificationHelper

class MainActivity : ComponentActivity() {

    private val POST_NOTIFICATIONS_PERMISSION_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover)

        // Initialize data file
        val context: Context = this
        FileHandler.CourseHandler.checkAndCreateFile(context.filesDir.toString())
        FileHandler.SettingHandler.checkAndCreateFile(context.filesDir.toString())

        // Check and request permission
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            // If permission has not been granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.POST_NOTIFICATIONS"),
                POST_NOTIFICATIONS_PERMISSION_REQUEST
            )
        } else {
            // Permission has already been granted
            sendNotificationAndNavigate()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            POST_NOTIFICATIONS_PERMISSION_REQUEST -> {
                // If permission request is cancelled, the result arrays are empty
                if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, send notification and navigate
                    sendNotificationAndNavigate()
                } else {
                    showToastForPermissionDenied()
                }
                return
            }
        }
    }

    private fun showToastForPermissionDenied() {
        // You can customize the message based on your application's needs
        val message = "Please enable the permission in the settings to use all features."

        // Display a Toast to inform the user about the importance of the denied permission
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun sendNotificationAndNavigate() {
        NotificationHelper.sendNotification(this, "TimeSync EduAlert", "Test")
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
