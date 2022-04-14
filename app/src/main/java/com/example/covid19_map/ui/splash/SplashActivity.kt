package com.example.covid19_map.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.covid19_map.ui.main.MainActivity
import com.example.covid19_map.R
import com.example.covid19_map.databinding.ActivitySplashBinding
import com.example.covid19_map.ui.SplashViewModelFactory
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SplashActivity"
        const val SPLASH_TIME = 1300L
    }

    private lateinit var activityLogoBinding: ActivitySplashBinding
    internal val viewModelFactory by lazy { SplashViewModelFactory(application) }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLogoBinding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash).apply {
            lifecycleOwner = this@SplashActivity
        }
        setHandler()

    }

    private fun setHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            activityLogoBinding.pbSplashLoading.progress = 0
            activityLogoBinding.pbSplashLoading.progress = 50
            moveMain()
        }, SPLASH_TIME)
    }



    private fun moveMain() {
        viewModel.callRetrofit()
        // 관찰하여 데이터 값이 변경되면 호출
        viewModel.nextActivity.observe(this, Observer { count ->
            if(count < 10){
                activityLogoBinding.pbSplashLoading.progress = 80
            }else{
                Thread.sleep(700L)
                activityLogoBinding.pbSplashLoading.progress = 100
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }

}