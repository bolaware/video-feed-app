package com.bolaware.videosfeedapp.customviews


import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bolaware.videosfeedapp.R
import com.bolaware.videosfeedapp.model.MediaObject


import com.squareup.picasso.Picasso


abstract class VideoPlayerViewHolder(@param:NonNull internal var parent: View) : RecyclerView.ViewHolder(parent) {
    var mediaContainer : FrameLayout
    var thumbnail: ImageView
    var progressBar: ProgressBar

    init {
        mediaContainer = parent.findViewById(R.id.media_container)
        thumbnail = parent.findViewById(R.id.thumbnail)
        progressBar = parent.findViewById(R.id.progressBar)
    }

    fun onBind(mediaObject: MediaObject) {
        parent.tag = this
        Picasso.get().load(mediaObject.thumbnail).into(thumbnail)
    }

}