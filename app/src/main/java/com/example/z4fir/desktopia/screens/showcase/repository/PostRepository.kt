package com.example.z4fir.desktopia.screens.showcase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.z4fir.desktopia.screens.showcase.database.Post
import com.example.z4fir.desktopia.screens.showcase.database.PostDatabase
import com.example.z4fir.desktopia.screens.showcase.database.asDomainModel
import com.example.z4fir.desktopia.screens.showcase.model.HashtagToMedia
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.model.asDataBaseModel
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//class PostRepository(private val database: PostDatabase) {
//
//    val posts: LiveData<PagedList<Post>> = Transformations.map(database.postDao().getAllPost()) {
//        it.asDomainModel()
//    }
//
//    /**
//     * This function uses the IO dispatcher to ensure the database insert database operation
//     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
//     * function is now safe to call from any thread including the Main thread.
//     */
//    suspend fun refreshPost() {
//        withContext(Dispatchers.IO) {
//
//            val hashTagResponse = ApiService.retrofitService
//                .getInstagramTagData("battlestation").await()
//
//            val edgeList = responseFilter(hashTagResponse)
//            database.postDao().insertAllPost(edgeList.asDataBaseModel())
//        }
//    }
//
//    private fun responseFilter(data: InstagramResponse): HashtagToMedia {
//
//        val edgesResponse = data.graphql.hashtag.edgeHashtagToMedia
//        edgesResponse.edges.filter { it.node.typename == "GraphImage" }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("people standing") }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("text") }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("food") }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("shoes") }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("No photo description available.") }
//        edgesResponse.edges.filter { !it.node.accessibilityCaption.contains("standing") }
//        return edgesResponse
//    }
//}
