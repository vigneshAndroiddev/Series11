package com.test.series.repo

import com.test.series.BuildConfig
import com.test.series.dataclass.DetailsBasedata
import com.test.series.retrofit.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DetailsRepo @Inject constructor(val apiService: ApiService) {
    fun getDetails(seriesid: Int): Flow<DetailsBasedata> = flow {
        emit(apiService.getDetails(
            BuildConfig.API_KEY,
            seriesid,ApiService.Lanuguage))
    }.flowOn(Dispatchers.IO)

}