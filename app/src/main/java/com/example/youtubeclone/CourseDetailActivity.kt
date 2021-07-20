package com.example.youtubeclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeclone.databinding.ActivityCourseDetailBinding
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {
    private lateinit var bindin: ActivityCourseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(bindin.root)
        bindin.rec.layoutManager = LinearLayoutManager(this)
        val navBarTitle = intent.getStringExtra(ViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle
        fetchJSON()
    }
    private fun fetchJSON(){
        val videoId = intent.getIntExtra(ViewHolder.VIDEO_ID_KEY,1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$videoId"
        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)
                runOnUiThread {
                    bindin.rec.adapter = CourseDetailAdapter(courseLessons)
                }
            }

        })
    }
    class CourseLesson(
        val name: String,
        val duration: String,
        val imageUrl: String,
        val link: String
    )

    private class CourseDetailAdapter(val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<CourseLessonViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.course_lesson_row,parent,false)
            return CourseLessonViewHolder(customView)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons[position]
            holder.courseLessonTitle.text = courseLesson.name
            holder.courseDuration.text = courseLesson.duration
            Picasso.get().load(courseLesson.imageUrl).into(holder.courseImageView)
            holder.courseLesson = courseLesson
        }

        override fun getItemCount(): Int {
            return courseLessons.size
        }
    }

}
class CourseLessonViewHolder(val customView: View, var courseLesson: CourseDetailActivity.CourseLesson?=null):RecyclerView.ViewHolder(customView){
    val courseImageView:ImageView = customView.findViewById(R.id.courseImageView)
    val courseLessonTitle:TextView = customView.findViewById(R.id.courseLessonTitle)
    val courseDuration:TextView = customView.findViewById(R.id.courseDuration)
    companion object {
        val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
    }

    init {
        customView.setOnClickListener {
            println("Attempt to load webview somehow???")

            val intent = Intent(customView.context, CourseLessonActivity::class.java)

            intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)

            customView.context.startActivity(intent)
        }
    }
}
