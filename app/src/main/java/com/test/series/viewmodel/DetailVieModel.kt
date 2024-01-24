package com.test.series.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.series.repo.DetailsRepo
import com.test.series.util.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.series.util.ApiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class DetailVieModel @Inject constructor(val detailsRepo: DetailsRepo,val networkChecker: NetworkChecker): ViewModel(){
    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    private val _details = MutableLiveData<Int>()
    val details: LiveData<Int> get() = _details

    public fun getValue(seriesid: Int){
        seriesid.also { this._details.value = it }
        if(networkChecker.isNetWorkAviliable()){
            getPost(this.details.value as Int)
        }else{
            response.value= ApiState.NoNetwork
        }

    }

    private fun getPost(seriesid: Int) {
        viewModelScope.launch {
            detailsRepo.getDetails(seriesid).onStart {
                response.value= ApiState.Loading
            }.catch {
                response.value= ApiState.Failure(it)
            }.collect {
                response.value=ApiState.Successdetails(it)
            }
        }
    }

}