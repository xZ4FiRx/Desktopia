package com.example.z4fir.desktopia.screens.showcase.reddit.network

import com.example.z4fir.desktopia.screens.showcase.reddit.model.ListingResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://www.reddit.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface RedditPostApiService {

    @GET("/r/{subreddit}/{listing}.json")
    fun getTop(
        @Path("subreddit") subreddit: String,
        @Path("listing") listing: String,
        @Query("limit") limit: Int): Call<ListingResponse>

    @GET("/r/{subreddit}/{listing}.json")
    fun getTopAfter(
        @Path("subreddit") subreddit: String,
        @Path("listing") listing: String,
        @Query("after") after: String,
        @Query("limit") limit: Int): Call<ListingResponse>

    @GET("/r/{subreddit}/hot.json")
    fun getTopBefore(
        @Path("subreddit") subreddit: String,
        @Query("before") before: String,
        @Query("limit") limit: Int): Call<ListingResponse>

}

object ApiServiceReddit {

    val retrofitService: RedditPostApiService by lazy {
        retrofit.create(RedditPostApiService::class.java)
    }
}