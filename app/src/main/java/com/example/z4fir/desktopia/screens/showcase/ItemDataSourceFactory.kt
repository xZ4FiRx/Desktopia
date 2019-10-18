package com.example.z4fir.desktopia.screens.showcase

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.screens.showcase.network.InstagramTagApiService


class ItemDataSourceFactory(private val hashtag: String,
    private val apiService: InstagramTagApiService) : DataSource.Factory<String, Edges>() {

    val sourceLiveData = MutableLiveData<ItemDataSource>()
    override fun create(): DataSource<String, Edges> {
        val source = ItemDataSource(hashtag, apiService)
        sourceLiveData.postValue(source)

        return source
    }
}