package io.strelnikov.lab3

class News(
    val title: String,
    val link: String,
    val creator: Array<String>,
    val description: String,
    val content: String,
    val pubDate: String,
    val country: Array<String>,
    val category: Array<String>
) {
}