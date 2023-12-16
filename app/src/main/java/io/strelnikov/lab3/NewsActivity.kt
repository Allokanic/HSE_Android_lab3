package io.strelnikov.lab3

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activty)

        val titleTextView: TextView = findViewById(R.id.textViewTitle)
        titleTextView.text = intent.getStringExtra("title")
        val linkTextView: TextView = findViewById(R.id.textViewLink)
        linkTextView.text = intent.getStringExtra("link")
        val contentTextView: TextView = findViewById(R.id.textViewContent)
        contentTextView.text = intent.getStringExtra("content")
        val countriesIntent = intent.getStringArrayExtra("countries")
        if (countriesIntent != null) {
            val countryTextView: TextView = findViewById(R.id.textViewCountry)
            val countries = TextUtils.join(", ", countriesIntent)
            countryTextView.text = countries
        }
        val categoriesIntent = intent.getStringArrayExtra("categories")
        if (categoriesIntent != null) {
            val categoryTextView: TextView = findViewById(R.id.textViewCategory)
            val categories = TextUtils.join(", ", categoriesIntent)
            categoryTextView.text = categories
        }
        val authorsIntent = intent.getStringArrayExtra("authors")
        if (authorsIntent != null) {
            val authorTextView: TextView = findViewById(R.id.textViewAuthor)
            val authors = TextUtils.join(", ", authorsIntent)
            authorTextView.text = authors
        }
        val dateTextView: TextView = findViewById(R.id.textViewDate)
        dateTextView.text = intent.getStringExtra("date")
    }
}