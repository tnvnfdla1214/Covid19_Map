package com.example.covid19_map.data.repository.db

import androidx.lifecycle.MutableLiveData
import com.example.covid19_map.data.remote.response.CentersModel


interface RoomRepository {

    suspend fun getCenterList() : List<CentersModel>

}