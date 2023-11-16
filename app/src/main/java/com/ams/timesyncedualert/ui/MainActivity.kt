package com.ams.timesyncedualert.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.ams.cspapplication.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover)

        Handler().postDelayed({
            navigateToHomepage()
        }, R.integer.cover_delay_milis.toLong())
    }

    private fun navigateToHomepage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
