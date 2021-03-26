package com.example.material.tvshowexperiment.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowDetail(
    @Json(name = "url")
    val url: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "runtime")
    val runtime: Int,

    @Json(name = "image_path")
    val imagePath: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "episodes")
    val episodes: List<Episode>,

    @Json(name = "genres")
    val genres: List<String>,

    @Json(name = "id")
    val id: Int,

    @Json(name = "pictures")
    val pictures: List<String>,

    @Json(name = "rating")
    val rating: String,
)