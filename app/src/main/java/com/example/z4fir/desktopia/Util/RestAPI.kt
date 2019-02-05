package com.example.z4fir.desktopia.Util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RestAPI
{
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://instagram.com/explore/"

    val retrofitInstance: Retrofit?
        get()
        {
            if(retrofit == null)
            {
                retrofit = retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }
}