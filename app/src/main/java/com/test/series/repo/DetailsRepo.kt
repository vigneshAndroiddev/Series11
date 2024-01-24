package com.test.series.repo

import com.test.series.dataclass.DetailsBasedata
import com.test.series.retrofit.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DetailsRepo @Inject constructor(val apiService: ApiService) {
    fun getDetails(seriesid: Int): Flow<DetailsBasedata> = flow {
        emit(apiService.getDetails("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZTc2OTNkNmQ3MDhlY2Y3NDcyYTU4NzFmNDliMjZjNyIsInN1YiI6IjY1YWM4MDkxYmMyY2IzMDBjYzk5NTUwYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.g5CqQpKU5D8wMTPweevfxSwvrAW64GaNyIIc9qBLg90",
            seriesid,ApiService.Lanuguage))
    }.flowOn(Dispatchers.IO)

}