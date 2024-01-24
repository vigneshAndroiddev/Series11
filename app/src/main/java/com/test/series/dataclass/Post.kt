package com.test.series.dataclass

data class Post(
    val page : Int,
    val results : List<ResultsPopularSeries>,
    val total_pages : Int,
    val total_results : Int
)
