package com.example.z4fir.desktopia.Util

import com.example.z4fir.desktopia.Model.InstaResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RestAPI
{

    @GET("tags/{tag}/?__a=1&max_id=")
    fun getInstaData(@Path("tag") hashtag: String): Deferred<Response<InstaResponse>>

    companion object
    {
        fun getDataApi(): RestAPI
        {
            return Retrofit.Builder()
                .baseUrl("https://instagram.com/explore/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(RestAPI::class.java)
        }
    }


//    val retrofitInstance: Retrofit?
//        get()
//        {
//            if (retrofit == null)
//            {
//                retrofit = retrofit2.Retrofit
//                    .Builder()
//                    .baseUrl(BASE_URL)
//                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//
//            return retrofit
//        }
}