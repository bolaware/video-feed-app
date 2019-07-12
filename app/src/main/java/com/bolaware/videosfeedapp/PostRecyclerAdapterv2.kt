package com.bolaware.videosfeedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bolaware.videosfeedapp.customviews.VideoPlayerViewHolder
import com.bolaware.videosfeedapp.model.MediaObject
import com.bolaware.videosfeedapp.model.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_list_item2.view.*

class PostRecyclerAdapterv2 (var posts : MutableList<Post>, val marginBottom : Int) : RecyclerView.Adapter<VideoPlayerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayerViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_list_item2, parent, false))
    }

    override fun onBindViewHolder(holder: VideoPlayerViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(itemView : View) : VideoPlayerViewHolder(itemView){
        lateinit var post : Post

        init {
            val params = itemView.postLay.layoutParams as ConstraintLayout.LayoutParams

            params.bottomMargin = marginBottom

            itemView.postLay.layoutParams = params
        }

        fun bind(position : Int){
            this.post = posts.get(position)

            initNonMediaView()

            onBind(MediaObject(post.title, post.media_url, post.thumbnail_url, post.description))
        }

        private fun initNonMediaView(){
            itemView.usernameTV.text = "@${post.user_info.username}"
            itemView.postTV.text = post.description
            itemView.likeCountTV.text = post.like_count
            itemView.commentCountTV.text = post.comment_count
            itemView.shareCountTV.text = post.post_shares_count

            Picasso.get().load(post.user_info.avatar).into(itemView.profilePicIV)
        }
    }
}