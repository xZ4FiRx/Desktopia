package com.example.z4fir.desktopia.screens.showcase.network

import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://instagram.com/explore/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Call adapters add the ability for Retrofit to create APIs that return something other than the
 * default Call class. CoroutineCallAdapterFactory allows us to replace the Call object that
 * getInstagramTagData returns with a Deferred object instead.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


/**
 * The Deferred interface defines a coroutine job that returns a result value (Deferred inherits from Job).
 * The Deferred interface includes a method called await(), which causes your code to wait without blocking
 * until the value is ready, and then that value is returned.
 */
interface InstagramTagApiService
{
    @GET("tags/{tag}/?__a=1&max_id=")
    fun getInstagramTagData(@Path("tag") hashtag: String): Deferred<InstagramResponse>
}

object ApiService
{
    /**
     * The create() creates the Retrofit service itself with the InstagramTagApiService interface.
     * The app only needs one Retrofit instance, this exposes the service to the rest of the app using
     * a public object call ApiService, and lazily initialize the Retrofit service there.
     *
     * Each time the application calls ApiService.retrofitService it will get a singleton Retrofit
     * object that implements InstagramTagApiService.
     */
    val retrofitService: InstagramTagApiService by lazy {
        retrofit.create(InstagramTagApiService::class.java)
    }
}