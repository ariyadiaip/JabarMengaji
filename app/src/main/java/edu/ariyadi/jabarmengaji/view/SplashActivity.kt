package edu.ariyadi.jabarmengaji.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.databinding.ActivitySplashBinding
import androidx.core.content.edit

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val TOTAL_SPLASH_TIME_OUT: Long = 3000
    private val PROGRESSBAR_DELAY: Long = 500

    private val SHARED_PREFS_NAME = "shared_prefs"
    private val KEY_IS_FIRST_TIME = "is_first_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.loadingSplash.visibility = View.VISIBLE
        }, PROGRESSBAR_DELAY)

        val sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean(KEY_IS_FIRST_TIME, true)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstTime) {
                sharedPreferences.edit() {
                    putBoolean(KEY_IS_FIRST_TIME, false)
                }
                startActivity(Intent(this, WalkthroughActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, TOTAL_SPLASH_TIME_OUT)

    }
}