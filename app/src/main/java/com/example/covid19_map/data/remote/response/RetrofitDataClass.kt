package com.example.covid19_map.data.remote.response

import com.example.covid19_map.data.remote.api.CenterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitDataClass {
    const val BASE_URL: String =
        "https://api.odcloud.kr/api/15077586/v1/centers/"

    fun startRetrofit(): CenterService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CenterService::class.java)
    }
}