package com.test.series.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var intValue: Int by mutableStateOf(0)
}