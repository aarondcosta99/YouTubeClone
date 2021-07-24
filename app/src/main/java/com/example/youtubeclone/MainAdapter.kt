package com.example.youtubeclone

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainAdapter(val homeFeed: HomeFeed):RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val cellForRow = layoutInflater.inflate(R.layout.video_row,parent,false)

        return ViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {

        return homeFeed.videos.count()

    }

    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val video = homeFeed.videos[position]

        holder.videoTitle.text=video.name

        holder.channelName.text=video.channel.name + " * " + "20K Views" + "\n4 days ago"

        Picasso.get().load(video.imageUrl).into(holder.thumbNailImageView)

        Picasso.get().load(video.channel.profileimageUrl).into(holder.channelImageView)

        holder.video = video

    }

}

class ViewHolder(view: View, var video: Video?=null): RecyclerView.ViewHolder(view){

    val thumbNailImageView: ImageView = view.findViewById(R.id.imageV)

    val channelImageView: CircleImageView = view.findViewById(R.id.channelP)

    val videoTitle:TextView = view.findViewById(R.id.videoT)

    val channelName:TextView = view.findViewById(R.id.channelN)

    companion object{

        val VIDEO_TITLE_KEY = "VIDEO_TITLE"

        val VIDEO_ID_KEY = "VIDEO_ID"

    }

    init {

        view.setOnClickListener {

            val intent = Intent(view.context,CourseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY,video?.name)

            intent.putExtra(VIDEO_ID_KEY,video?.id)

            view.context.startActivity(intent)

        }

    }

}