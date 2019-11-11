package com.example.z4fir.desktopia.screens.showcase.reddit.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.z4fir.desktopia.screens.showcase.reddit.model.RedditPost
import com.example.z4fir.desktopia.screens.showcase.reddit.network.RedditPostApiService
import com.example.z4fir.desktopia.util.NetworkState
import java.io.IOException


class PageDataSourceReddit(private val subreddit: String, private val listing: String,
    private val apiService: RedditPostApiService) : PageKeyedDataSource<String, RedditPost>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {

        val request = apiService.getTop(subreddit, listing, params.requestedLoadSize)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {

            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            val filterInput = items.filter { !it.stickied }

            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            callback.onResult(filterInput, data?.before, data?.after)
        } catch (io: IOException) {

            val error = NetworkState.error(io.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {

        val request = apiService.getTopAfter(subreddit, listing, params.key, params.requestedLoadSize)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {

            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            val filterInput = items.filter { !it.stickied }


            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            Log.i("PagedDataSourceReddit", data?.before + " after: " + data?.after)

            callback.onResult(filterInput, data?.after)
        } catch (io: IOException) {

            val error = NetworkState.error(io.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
    }
}