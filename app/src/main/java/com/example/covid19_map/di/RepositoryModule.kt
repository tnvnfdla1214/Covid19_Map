package com.example.covid19_map.di


import com.example.covid19_map.data.local.dao.CovidDao
import com.example.covid19_map.data.remote.network.RetrofitInterface
import com.example.covid19_map.data.repository.api.ServiceRepository
import com.example.covid19_map.data.repository.api.ServiceServiceRepositoryImpl
import com.example.covid19_map.data.repository.db.RoomRepository
import com.example.covid19_map.data.repository.db.RoomRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideServiceRepository(
        covidDao: CovidDao,
        retrofitInterface: RetrofitInterface,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher,
        @DispatcherModule.MainDispatcher minDispatcher: CoroutineDispatcher
    ): ServiceRepository
            = ServiceServiceRepositoryImpl(covidDao,retrofitInterface,ioDispatcher,minDispatcher)

    @Singleton
    @Provides
    fun provideRoomRepository(covidDao: CovidDao): RoomRepository = RoomRepositoryImpl(covidDao)

}