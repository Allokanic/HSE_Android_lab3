package io.strelnikov.lab3


data class NewsResponse (
    val status: String,
    val totalResults: Int,
    val results: ArrayList<News>
)