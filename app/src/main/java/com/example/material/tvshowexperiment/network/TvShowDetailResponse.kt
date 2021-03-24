package com.example.material.tvshowexperiment.network


import com.example.material.tvshowexperiment.data.TvShowDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowDetailResponse(
    @Json(name = "tvShow")
    val tvShowDetail: TvShowDetail
)