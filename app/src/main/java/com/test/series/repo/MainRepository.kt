package com.test.series.repo

import com.test.series.dataclass.DetailsBasedata
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
        emit(apiService.getPosts("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZTc2OTNkNmQ3MDhlY2Y3NDcyYTU4NzFmNDliMjZjNyIsInN1YiI6IjY1YWM4MDkxYmMyY2IzMDBjYzk5NTUwYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.g5CqQpKU5D8wMTPweevfxSwvrAW64GaNyIIc9qBLg90",
            "en",1))
    }.flowOn(Dispatchers.IO)


}