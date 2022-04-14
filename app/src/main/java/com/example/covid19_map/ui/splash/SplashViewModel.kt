package com.example.covid19_map.ui.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19_map.data.local.provideCovidDao
import com.example.covid19_map.data.remote.response.CentersInfo
import com.example.covid19_map.data.remote.response.RetrofitDataClass
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "Splash ViewModel"
    }

    val count: MutableLiveData<Int> = MutableLiveData()// center 데이터 의 개수
    val nextActivity: LiveData<Int> get() = count

    val responseData: MutableLiveData<CentersInfo> = MutableLiveData() //center 데이터

    private val api = RetrofitDataClass.startRetrofit() //retrofit 으로 처음 받아오는거 시작

    internal val covidDao by lazy { provideCovidDao(application.baseContext) } //room에 바로 꼳아놓는 상황 -> repository를 통해 넣아야함

    fun callRetrofit() {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(10) { a ->    // 1부터 10까지(1P당 서브 페이지 10, 총 100)
                api.getCovidCenter(a + 1, 10).enqueue(object : Callback<CentersInfo> {
                    override fun onResponse(//개별 center의 정보들을 받아오는 곳
                        call: Call<CentersInfo>,
                        response: Response<CentersInfo>
                    ) {
                        if (response.isSuccessful) {
                            // code == 200
                            responseData.value = response.body()
                            Log.d(TAG, responseData.value.toString())
                            CoroutineScope(Dispatchers.IO).launch {
                                for (i in responseData.value!!.centerList.indices) {
                                    covidDao.insertAll(responseData.value!!.centerList[i])
                                    Log.d(TAG, responseData.value!!.centerList[i].id.toString())
                                }

                                withContext(Dispatchers.Main) {
                                    // n회 완료
                                    count.value = a + 1
                                }
                            }
                        } else {
                            // code == 400
                            Log.d(TAG, response.toString())
                        }
                    }

                    override fun onFailure(call: Call<CentersInfo>, t: Throwable) {
                        // code == 500
                        Log.d(TAG, t.toString())
                        cancel()
                        count.value = 500
                    }
                })
                delay(120L)
            }

        }
    }


}