package com.bolaware.videosfeedapp.di

import com.bolaware.videosfeedapp.BuildConfig
import com.bolaware.videosfeedapp.network.NetworkRepo
import com.bolaware.videosfeedapp.network.PostService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkRepo(postService: PostService) : NetworkRepo{
        return NetworkRepo(postService)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://35.226.14.35:8080/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .apply {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addNetworkInterceptor(logging)
            }
            .build()
    }


    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit) : PostService{
        return retrofit.create(PostService::class.java)
    }

}