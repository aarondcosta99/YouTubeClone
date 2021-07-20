package com.example.youtubeclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeclone.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recylerView.layoutManager=LinearLayoutManager(this)
        fetchJson()
    }
    fun fetchJson(){
        println("Attempting to Fetch Json")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body,HomeFeed::class.java)
                runOnUiThread{
                    binding.recylerView.adapter = MainAdapter(homeFeed)
                }
            }

        })
    }
}
class HomeFeed(val videos: List<Video>)
class Video(val id: Int, val name: String, val link: String, val imageUrl: String, val numberOfViews: String, val channel: Channel)
class Channel(val name: String, val profileimageUrl: String)