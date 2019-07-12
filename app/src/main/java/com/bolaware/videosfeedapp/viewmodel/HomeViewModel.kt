package com.bolaware.videosfeedapp.viewmodel

import androidx.lifecycle.*
import com.bolaware.videosfeedapp.model.Post
import com.bolaware.videosfeedapp.network.NetworkResultSuccess
import com.bolaware.videosfeedapp.network.NetworkRepo
import com.bolaware.videosfeedapp.network.NetworkResultError
import com.danikula.videocache.HttpProxyCacheServer
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(val networkRepo : NetworkRepo, val httpProxyCacheServer: HttpProxyCacheServer) : ViewModel() {


    val _postsLd : MediatorLiveData<List<Post>> = MediatorLiveData()
    private var _toastLd : MutableLiveData<String> = MutableLiveData()

    val toastLd : LiveData<String>
        get() = _toastLd


    fun fetchPosts(){

        viewModelScope.launch {
            val result = networkRepo.fetchPosts()

            _postsLd.addSource(result){
                when(it){
                    is NetworkResultSuccess -> {
                        it.data.forEach { it.media_url = httpProxyCacheServer.getProxyUrl(it.media_url) }
                        _postsLd.value = it.data
                    }
                    is NetworkResultError -> _toastLd.value = it.error.localizedMessage
                }
            }
        }
    }
}