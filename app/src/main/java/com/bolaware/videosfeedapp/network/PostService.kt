package com.bolaware.videosfeedapp.network

import com.bolaware.videosfeedapp.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("/api/v1/posts/newsfeed/")
    suspend fun getPosts() : Response<List<Post>>
}