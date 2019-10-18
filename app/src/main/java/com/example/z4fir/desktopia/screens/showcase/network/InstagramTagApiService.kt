package com.example.z4fir.desktopia.screens.showcase.network

import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://instagram.com/explore/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

//Changing Call to Deferred for Room/ Repo implementation
interface InstagramTagApiService {
    @GET("tags/{tag}/?__a=1&max_id=")
    fun getInstagramTagData(@Path("tag") hashtag: String): Call<InstagramResponse>

    @GET("tags/{tag}/?__a=1&max_id=")
    fun getInstagramTagDataNext(@Path("tag") hashtag: String,
        @Query("max_id") endCursor: String = ""): Call<InstagramResponse>

}

object ApiService {

    val retrofitService: InstagramTagApiService by lazy {
        retrofit.create(InstagramTagApiService::class.java)
    }
}