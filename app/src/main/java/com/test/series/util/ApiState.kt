package com.test.series.util

import com.test.series.dataclass.DetailsBasedata
import com.test.series.dataclass.Post

sealed class ApiState  {
    class Success(val data: Post) : ApiState()
    class Successdetails(val data: DetailsBasedata) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading:ApiState()
    object Empty: ApiState()
    object NoNetwork:ApiState()
}