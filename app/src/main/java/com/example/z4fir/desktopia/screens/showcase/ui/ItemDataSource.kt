package com.example.z4fir.desktopia.screens.showcase.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.network.InstagramTagApiService
import com.example.z4fir.desktopia.screens.showcase.network.NetworkState
import java.lang.Exception

class ItemDataSource(private val hashTag: String,
    private val apiService: InstagramTagApiService) : PageKeyedDataSource<String, Edges>() {

    private lateinit var endCursor: String
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Edges>) {

        val call = apiService.getInstagramTagData(hashTag)
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {

            val response = call.execute().body()
            if (response!!.graphql.hashtag.edgeHashtagToMedia.pageInfo.hasNextPage) {
                endCursor = response.graphql.hashtag.edgeHashtagToMedia.pageInfo.endCursor
            }
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(responseFilter(response), null, endCursor)
            Log.i("ItemDataSource\n", "LoadInitial response successful\n" +
                    "List size: ${responseFilter(response).size}\n" +
                    "End Cursor: $endCursor")

        } catch (e: Exception) {
            Log.e("ItemDataSource", "Failed to get data\n $e")
            val error = NetworkState.error(e.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Edges>) {

        networkState.postValue(NetworkState.LOADING)
        try {

            val call = apiService.getInstagramTagDataNext(hashTag, endCursor)
            val response = call.execute().body()

            if (response!!.graphql.hashtag.edgeHashtagToMedia.pageInfo.hasNextPage) {
                endCursor = response.graphql.hashtag.edgeHashtagToMedia.pageInfo.endCursor
            }
            callback.onResult(responseFilter(response), endCursor)
            networkState.postValue(NetworkState.LOADED)
            Log.i("ItemDataSource\n", "loadAfter response successful\n" +
                    "List size: ${responseFilter(response).size}\n" +
                    "End Cursor: $endCursor")

        } catch (e: Exception) {
            Log.e("ItemDataSourceLoadAfter", "Failed to get data\n$e")
            val error = NetworkState.error(e.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Edges>) {
    }


    //TODO Revise filter
    private fun responseFilter(data: InstagramResponse): List<Edges> {

        val edgesResponse = data.graphql.hashtag.edgeHashtagToMedia.edges

        val filterTypeName = edgesResponse.filter { it.node.typename == "GraphImage" }
        val captionFilter = filterTypeName.filter {!it.node.accessibilityCaption.contains("people standing")}
        val captionFilter2 = captionFilter.filter {!it.node.accessibilityCaption.contains("text")}
        val captionFilter3 = captionFilter2.filter {!it.node.accessibilityCaption.contains("food")}
        val captionFilter4 = captionFilter3.filter {!it.node.accessibilityCaption.contains("shoes")}
        val captionFilter5 = captionFilter4.filter {!it.node.accessibilityCaption.contains("No photo description available.")}
        return captionFilter5.filter {!it.node.accessibilityCaption.contains("standing")}
    }
}