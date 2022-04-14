package com.example.covid19_map.data.local

import androidx.room.*
import com.example.covid19_map.data.remote.response.CenterApi

@Dao
interface CovidDao {
    @Query("SELECT * FROM CovidCenterTable")
    fun getAll(): List<CenterApi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(centerApi: CenterApi)

    @Delete
    fun delete(covidEntity: CenterApi)
}