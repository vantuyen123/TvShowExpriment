package com.example.material.tvshowexperiment.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.episodate.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface TvShowApiService {
    @GET("most-popular")
    suspend fun getMostPopular(@Query("page") page: Int): TvShowResponse

    @GET("show-details")
    suspend fun getTvShowDetail(@Query("q") q: Int): TvShowDetailResponse

    @GET("search")
    suspend fun getTvShowSearch(@Query("q") q: String, @Query("page") page: Int): SearchResponse

    companion object {
        fun create(): TvShowApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TvShowApiService::class.java)
        }
    }
}
