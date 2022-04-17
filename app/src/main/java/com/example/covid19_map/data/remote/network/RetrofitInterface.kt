package com.example.covid19_map.data.remote.network

import com.example.covid19_map.BuildConfig
import com.example.covid19_map.data.remote.response.CentersApi
import com.example.covid19_map.data.remote.url.Url.BASE_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(BASE_URL)
    fun getCovidCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Call<CentersApi>
}