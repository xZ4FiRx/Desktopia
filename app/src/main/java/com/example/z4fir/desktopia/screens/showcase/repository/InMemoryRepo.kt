package com.example.z4fir.desktopia.screens.showcase.repository

import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.screens.showcase.network.InstagramTagApiService
import com.example.z4fir.desktopia.screens.showcase.ui.ItemDataSourceFactory

class InMemoryRepo {



    fun tagPostOfInstagram(hashtag: String, apiService: InstagramTagApiService): Listing<Edges>{

        val factory = ItemDataSourceFactory(hashtag, apiService)

        val pagedList = factory.toLiveData(config = Config(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSizeHint = 40
        ))

        val refreshState = Transformations.switchMap(factory.source){it.initialLoad}
        val networkState = Transformations.switchMap(factory.source){it.networkState}

        return Listing(
            pagedList = pagedList,
            networkState = networkState,
            refreshState = refreshState,
            refresh = { factory.source.value?.invalidate() }
        )
    }
}