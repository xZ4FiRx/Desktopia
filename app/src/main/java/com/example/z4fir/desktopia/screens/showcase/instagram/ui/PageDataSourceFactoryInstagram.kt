package com.example.z4fir.desktopia.screens.showcase.instagram.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.z4fir.desktopia.screens.showcase.instagram.model.Edges
import com.example.z4fir.desktopia.screens.showcase.instagram.network.InstagramTagApiService


class PageDataSourceFactoryInstagram(private val hashtag: String,
    private val apiService: InstagramTagApiService) : DataSource.Factory<String, Edges>() {

    val source = MutableLiveData<PageDataSourceInstagram>()
    override fun create(): DataSource<String, Edges> {
        val source = PageDataSourceInstagram(hashtag, apiService)
        this.source.postValue(source)

        return source
    }
}