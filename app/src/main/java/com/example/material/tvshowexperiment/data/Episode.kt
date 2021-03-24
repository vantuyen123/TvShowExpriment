package com.example.material.tvshowexperiment.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "episode")
    val episode: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "season")
    val season: Int
)