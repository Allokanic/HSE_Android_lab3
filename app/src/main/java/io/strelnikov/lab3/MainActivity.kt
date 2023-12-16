package io.strelnikov.lab3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val apiKey: String = "pub_347109b6f1028623c0bec71bcb521883c44e6";
    private val adapter = RecyclerAdapter()
    private val BASE_URL = "https://newsdata.io/api/1/news"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val editTextKeyword: EditText = findViewById(R.id.editTextKeyword)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNews)
        val layoutManager = GridLayoutManager(this, 1)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val buttonSearch: Button = findViewById(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val keyword = editTextKeyword.text.toString()
            getNews(keyword)
        }
    }

    private fun getNews(keyword: String) {
        val url = "$BASE_URL?q=$keyword&apikey=$apiKey"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val newsResponse: NewsResponse = processNewsResponse(response)
                    adapter.updateItems(newsResponse.results)
                } catch (e: JSONException) {
                    Log.e("MainActivity", "JSON parsing error: $e")
                }
            },
            { error: VolleyError ->
                Log.e("MainActivity", "Error: $error")
            })

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    private fun processNewsResponse(response: JSONObject): NewsResponse {
        val status = response.optString("status", "")
        val totalResults = response.optInt("totalResults", 0)

        val newsList = ArrayList<News>()
        if (response.has("results")) {
            val resultsArray = response.getJSONArray("results")
            for (i in 0 until resultsArray.length()) {
                val resultObject = resultsArray.getJSONObject(i)
                val title = resultObject.optString("title", "")
                val link = resultObject.optString("link", "")
                val creatorArray = getStringArray(resultObject, "creator")
                val description = resultObject.optString("description", "")
                val content = resultObject.optString("content", "")
                val pubDate = resultObject.optString("pubDate", "")
                val countryArray = getStringArray(resultObject, "country")
                val categoryArray = getStringArray(resultObject, "category")

                val news = News(title, link, creatorArray, description, content, pubDate, countryArray, categoryArray)
                newsList.add(news)
            }
        }

        return NewsResponse(status, totalResults, newsList)
    }

    private fun getStringArray(jsonObject: JSONObject, key: String): Array<String> {
        return try {
            val jsonArray = jsonObject.getJSONArray(key)
            Array(jsonArray.length()) { i -> jsonArray.optString(i, "") }
        } catch (e: JSONException) {
            emptyArray()
        }
    }
}