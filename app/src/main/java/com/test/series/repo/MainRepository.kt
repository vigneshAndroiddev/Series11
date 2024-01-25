package com.test.series.repo

import com.test.series.BuildConfig
import com.test.series.dataclass.Post
import com.test.series.retrofit.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiService: ApiService){
    fun getPost(): Flow<Post> = flow {
        emit(apiService.getPosts(
            BuildConfig.API_KEY,
            "en",1))
    }.flowOn(Dispatchers.IO)

    fun getSearch(s: String): Flow<Post> = flow {
        emit(apiService.searchSeries(BuildConfig.API_KEY,
            s))
    }.flowOn(Dispatchers.IO)
}