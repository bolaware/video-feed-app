package com.bolaware.videosfeedapp.di

import android.app.Application
import com.danikula.videocache.HttpProxyCacheServer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MediaModule {
    @Provides
    @Singleton
    fun providePostRecyclerAdapter(application : Application) : HttpProxyCacheServer {
        return HttpProxyCacheServer(application.applicationContext)
    }
}