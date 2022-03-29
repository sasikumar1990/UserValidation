package com.sutrix.user.validation.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sutrix.user.validation.R
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        activityScope.launch {
            delay(2000)
            var intent = Intent(this@SplashScreen, ValidationFormActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}