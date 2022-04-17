package com.example.covid19_map.data.repository.api

import androidx.lifecycle.MutableLiveData

interface ServiceRepository {

    suspend fun getCenterList(count: MutableLiveData<Int>)

}