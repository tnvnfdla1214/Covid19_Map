package com.example.covid19_map.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.covid19_map.R
import com.example.covid19_map.databinding.ActivitySplashBinding
import com.example.covid19_map.ui.base.BindingActivity
import com.example.covid19_map.ui.main.MainActivity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun initView() {
        setHandler()
    }

    private fun setHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.pbSplashLoading.progress = 0
            binding.pbSplashLoading.progress = 50
            CoroutineScope(Dispatchers.Main).launch {
                moveMain()
            }
        }, SPLASH_TIME)
    }

    private suspend fun moveMain() {
        viewModel.callRetrofit()
        // 관찰하여 데이터 값이 변경되면 호출
        viewModel.nextActivity.observe(this, Observer { count ->
            if(count < 10){
                binding.pbSplashLoading.progress = 80
            }else{
                Thread.sleep(700L)
                binding.pbSplashLoading.progress = 100
                startMain()
            }
        })
    }

    private fun startMain() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val TAG = "SplashActivity"
        const val SPLASH_TIME = 1300L
    }
}