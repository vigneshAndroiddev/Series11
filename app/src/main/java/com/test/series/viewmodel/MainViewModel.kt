package com.test.series.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.series.repo.MainRepository
import com.test.series.util.ApiState
import com.test.series.util.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MainViewModel
@Inject constructor(private val mainRepository: MainRepository,
                    networkChecker: NetworkChecker) : ViewModel() {
    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)


    init {
        if(networkChecker.isNetWorkAviliable()){
            getPost()
        }else{
            response.value= ApiState.NoNetwork
        }

    }
    fun getPost() =
        viewModelScope.launch {
            mainRepository.getPost().onStart {
                response.value= ApiState.Loading
            }.catch {
                response.value= ApiState.Failure(it)
            }.collect {
                response.value=ApiState.Success(it)
            }
        }
}