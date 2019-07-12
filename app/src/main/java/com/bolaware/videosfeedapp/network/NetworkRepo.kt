package com.bolaware.videosfeedapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bolaware.videosfeedapp.model.Post
import com.bolaware.videosfeedapp.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.withTestContext
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class NetworkRepo @Inject constructor(var postService: PostService) {

    suspend fun fetchPosts() : LiveData<NetworkResult<List<Post>>> = withContext(Dispatchers.IO){
        makeCall(call = { postService.getPosts() })
    }

    private suspend fun <T : Any> makeCall(call : suspend () -> Response<T>) : LiveData<NetworkResult<T>>{
        val result = MutableLiveData<NetworkResult<T>>()

        try {
            val response = call()
            if(response.isSuccessful){
                response.body()?.let {
                    result.postValue(NetworkResultSuccess(it))
                }
            } else {
                result.postValue(NetworkResultError(Exception(response.errorBody()?.string())))
            }
        } catch (e : Exception){
            result.postValue(NetworkResultError(e))
        }

        return result
    }
}