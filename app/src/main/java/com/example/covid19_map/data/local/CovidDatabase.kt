package com.example.covid19_map.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covid19_map.data.remote.response.CenterApi

@Database(entities = [CenterApi::class], version = 1, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}