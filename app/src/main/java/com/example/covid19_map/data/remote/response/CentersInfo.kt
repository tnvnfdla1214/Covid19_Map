package com.example.covid19_map.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CentersInfo(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("perPage")
    @Expose
    val perPage: Int,
    @SerializedName("totalCount")
    @Expose
    val totalCount: Int,
    @SerializedName("currentCount")
    @Expose
    val currentCount: Int,
    @SerializedName("data")
    @Expose
    val centerList: List<CenterApi>
)
