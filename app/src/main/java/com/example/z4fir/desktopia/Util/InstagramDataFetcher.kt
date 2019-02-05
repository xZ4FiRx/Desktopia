package com.example.z4fir.desktopia.Util

import com.example.z4fir.desktopia.Model.InstagramResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InstagramDataFetcher
{
    @GET("tags/{tag}/?__a=1&max_id=")
    fun getInstagramData(@Path("tag") hashtag: String) : Call <InstagramResponse>
}