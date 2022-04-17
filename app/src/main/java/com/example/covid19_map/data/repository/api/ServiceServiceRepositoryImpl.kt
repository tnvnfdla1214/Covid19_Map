package com.example.covid19_map.data.repository.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.covid19_map.data.local.dao.CovidDao
import com.example.covid19_map.data.remote.network.RetrofitInterface
import com.example.covid19_map.data.remote.response.CentersApi

import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceServiceRepositoryImpl (
    private val covidDao: CovidDao,
    private val api: RetrofitInterface,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ServiceRepository {

    override suspend fun getCenterList(count: MutableLiveData<Int>) {
        val responseData: MutableLiveData<CentersApi> = MutableLiveData()
        for(i in 1..10){
            api.getCovidCenter(i + 1, 10).enqueue(object : Callback<CentersApi> {
                override fun onResponse(
                    call: Call<CentersApi>,
                    response: Response<CentersApi>
                ) {
                    if (response.isSuccessful) {
                        // code == 200
                        responseData.value = response.body()
                        CoroutineScope(ioDispatcher).launch {
                            for (i in responseData.value!!.centersModel.indices) {
                                covidDao.insertAll(responseData.value!!.centersModel[i])
                            }
                            withContext(mainDispatcher) {
                                // n회 완료
                                count.value = i + 1
                            }

                        }
                    } else {
                        // code == 400
                        Log.d("getCenterList", response.toString())
                    }
                }

                override fun onFailure(call: Call<CentersApi>, t: Throwable) {
                    //실패
                    Log.d("getCenterList", t.toString())
                    count.value = 500
                }
            })
            delay(120L)
        }
    }
}