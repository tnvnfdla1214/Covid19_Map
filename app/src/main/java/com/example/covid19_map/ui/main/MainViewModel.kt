package com.example.covid19_map.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19_map.data.remote.response.CentersModel
import com.example.covid19_map.data.repository.db.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    private val _CenterList: MutableLiveData<List<CentersModel>> = MutableLiveData()// center 데이터 의 개수
    val CenterList: LiveData<List<CentersModel>> get() = _CenterList

    fun getCenterList(){
        CoroutineScope(Dispatchers.IO).launch {
            _CenterList.postValue(roomRepository.getCenterList())
        }
    }
}