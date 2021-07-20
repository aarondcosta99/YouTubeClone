package com.example.youtubeclone

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtubeclone.databinding.ActivityCourseLessonBinding

class CourseLessonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseLessonBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.example.youtubeclone.databinding.ActivityCourseLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val courseLink = intent.getStringExtra(CourseLessonViewHolder.COURSE_LESSON_LINK_KEY)


        binding.webviewCourseLesson.settings.javaScriptEnabled = true
        binding.webviewCourseLesson.settings.loadWithOverviewMode = true
        binding.webviewCourseLesson.settings.useWideViewPort = true

        if (courseLink != null) {
            binding.webviewCourseLesson.loadUrl(courseLink)
        }
    }
}