package com.example.covid19_map.data.local.dao

import androidx.room.*
import com.example.covid19_map.data.remote.response.CentersModel


@Dao
interface CovidDao {
    @Query("SELECT * FROM CovidCenterTable")
    fun getAll(): List<CentersModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(centersModel: CentersModel)

    @Delete
    fun delete(covidEntity: CentersModel)
}