package com.example.covid19_map.data.local

import android.content.Context
import androidx.room.Room

private var instance: CovidDatabase? = null

fun provideCovidDao(context: Context): CovidDao = provideDatabase(context).covidDao()

private fun provideDatabase(context: Context): CovidDatabase {
    if (null == instance) {
        instance = Room.databaseBuilder(
            context.applicationContext,
            CovidDatabase::class.java, "covid.db"
        ).build()
    }

    return instance!!
}