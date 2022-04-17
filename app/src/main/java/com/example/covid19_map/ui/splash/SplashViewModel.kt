package com.example.covid19_map.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19_map.data.repository.api.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val serviceRepository: ServiceRepository) : ViewModel() {

    val count: MutableLiveData<Int> = MutableLiveData()// center 데이터 의 개수
    val nextActivity: LiveData<Int> get() = count

    suspend fun callRetrofit() {
        serviceRepository.getCenterList(count)
    }

    companion object {
        private const val TAG = "Splash ViewModel"
    }
}