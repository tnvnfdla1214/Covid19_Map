package com.example.covid19_map.data.repository.db

import com.example.covid19_map.data.local.dao.CovidDao
import com.example.covid19_map.data.remote.response.CentersModel


class RoomRepositoryImpl(
    private val covidDao: CovidDao,
    ):RoomRepository {

    override suspend fun getCenterList(): List<CentersModel> = covidDao.getAll()
}