package com.example.covid19_map.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covid19_map.data.local.dao.CovidDao
import com.example.covid19_map.data.remote.response.CentersModel


@Database(entities = [CentersModel::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}