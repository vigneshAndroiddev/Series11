package com.test.series.retrofit.network

import com.test.series.dataclass.DetailsBasedata
import com.test.series.dataclass.Post
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        val ImageURL="http://image.tmdb.org/t/p/w780"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val Lanuguage = "en"
        const val DefaultPageCount = 1
    }

    @GET("tv/popular")
    suspend fun getPosts(
        @Header("Authorization") token:String,
        @Query("country") country: String = Lanuguage,
        @Query("page") pageNum: Int = DefaultPageCount,
    ): Post

    @GET("tv/{series_id}")
    suspend fun getDetails(
        @Header("Authorization") token:String,
        @Path("series_id") country: Int,
        @Query("language") pageNum: String = Lanuguage,
    ): DetailsBasedata
}