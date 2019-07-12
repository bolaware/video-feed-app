package com.bolaware.videosfeedapp.network


sealed class NetworkResult<T>


class NetworkResultSuccess<T>(val data: T) : NetworkResult<T>()


class NetworkResultError<T>(val error: Throwable) : NetworkResult<T>()