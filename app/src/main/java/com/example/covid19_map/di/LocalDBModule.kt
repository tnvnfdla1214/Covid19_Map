package com.example.covid19_map.di

import android.content.Context
import androidx.room.Room
import com.example.covid19_map.data.local.ApplicationDatabase
import com.example.covid19_map.data.local.dao.CovidDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDBModule {
    @Provides
    @Singleton
    fun providesCenterDao(appdatabase: ApplicationDatabase): CovidDao = appdatabase.covidDao()

    @Provides
    @Singleton
    fun providesCenterDatabase(@ApplicationContext context: Context): ApplicationDatabase
            = Room.databaseBuilder(context, ApplicationDatabase::class.java,"covid.db").build()

}