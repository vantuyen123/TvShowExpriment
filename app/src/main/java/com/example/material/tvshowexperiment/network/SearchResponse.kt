package com.example.material.tvshowexperiment.network


import com.example.material.tvshowexperiment.data.TvShow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "total")
    val total: String,
    @Json(name = "tv_shows")
    val tvShows: List<TvShow>
)