package com.example.covid19_map.data.remote.api

import com.example.covid19_map.BuildConfig
import com.example.covid19_map.data.remote.response.CentersInfo
import com.example.covid19_map.data.remote.response.RetrofitDataClass.BASE_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CenterService {
    @GET(BASE_URL)
    fun getCovidCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Call<CentersInfo>
}