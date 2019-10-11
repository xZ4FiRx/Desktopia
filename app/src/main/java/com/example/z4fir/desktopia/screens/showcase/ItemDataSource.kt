package com.example.z4fir.desktopia.screens.showcase

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.z4fir.desktopia.screens.showcase.model.EdgesResponse
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.network.InstagramTagApiService
import kotlinx.coroutines.CoroutineScope
import java.lang.Exception

class ItemDataSource(private val hashTag: String,
    private val apiService: InstagramTagApiService) : PageKeyedDataSource<String, EdgesResponse>() {

    lateinit var endCursor: String

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, EdgesResponse>) {

        try {
            val call = apiService.getInstagramTagData(hashTag)
            val response = call.execute().body()
            if (response!!.graphql.hashtag.edgeHashtagToMedia.pageInfo.hasNextPage) {
                endCursor = response.graphql.hashtag.edgeHashtagToMedia.pageInfo.endCursor
            }
            callback.onResult(responseFilter(response), null, endCursor)
            Log.i("ItemDataSource\n", "LoadInitial response successful\n" +
                    "List size: ${responseFilter(response).size}\n" +
                    "End Cursor: $endCursor")

        } catch (e: Exception) {
            Log.e("ItemDataSource", "Failed to get data\n $e")
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, EdgesResponse>) {

        try {

            val call = apiService.getInstagramTagDataNext(hashTag, endCursor)
            val response = call.execute().body()

            if (response!!.graphql.hashtag.edgeHashtagToMedia.pageInfo.hasNextPage) {
                endCursor = response.graphql.hashtag.edgeHashtagToMedia.pageInfo.endCursor
            }
            callback.onResult(responseFilter(response), endCursor)
            Log.i("ItemDataSource\n", "loadAfter response successful\n" +
                    "List size: ${responseFilter(response).size}\n" +
                    "End Cursor: $endCursor")

        } catch (e: Exception) {
            Log.e("ItemDataSourceLoadAfter", "Failed to get data\n$e")
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, EdgesResponse>) {
    }


    //TODO Revise filter
    private fun responseFilter(data: InstagramResponse): List<EdgesResponse> {

        val edgesResponse = data.graphql.hashtag.edgeHashtagToMedia.edges

        val filterTypeName = edgesResponse.filter { it.node.typename == "GraphImage" }
        val captionFilter = filterTypeName.filter {!it.node.accessibilityCaption.contains("people standing")}
        val captionFilter2 = captionFilter.filter {!it.node.accessibilityCaption.contains("text")}
        val captionFilter3 = captionFilter2.filter {!it.node.accessibilityCaption.contains("food")}
        val captionFilter4 = captionFilter3.filter {!it.node.accessibilityCaption.contains("shoes")}
        val captionFilter5 = captionFilter4.filter {!it.node.accessibilityCaption.contains("No photo description available.")}
        val captionFilter6 = captionFilter5.filter {!it.node.accessibilityCaption.contains("standing")}

        return captionFilter6
    }
}


