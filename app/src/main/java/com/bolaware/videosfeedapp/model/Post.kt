package com.bolaware.videosfeedapp.model

data class Post (
    val title : String,
    val description : String,
    var media_url : String,
    val thumbnail_url : String,
    val created_at : String,
    val comment_count : String,
    val like_count : String,
    val post_shares_count : String,
    val user_info : UserInfo
)

data class UserInfo(
    val fullname : String,
    val username : String,
    val avatar : String
)

