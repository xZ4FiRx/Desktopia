package com.example.z4fir.desktopia.screens.showcase

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.z4fir.desktopia.screens.showcase.database.getDatabase
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import com.example.z4fir.desktopia.screens.showcase.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class ShowcaseViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository = PostRepository(getDatabase(application))
    private val postList = postRepository.posts
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _hashTagDataResponse = MutableLiveData<List<InstagramResponse>>()
    val hashtagDataResponse: LiveData<List<InstagramResponse>>
        get() = _hashTagDataResponse

    init {
        val randoTags = "cats"

        Log.i("ShowcaseViewModel-Init", randoTags)

        getInstagramData()
        refreshDataFromRepo(randoTags)
    }

    private fun getInstagramData() {
        coroutineScope.launch {
            try {
                val hashTag1 = ApiService.retrofitService.getInstagramTagData("battlestation").await()
                val hashTag2 = ApiService.retrofitService.getInstagramTagData("setups").await()
                val hashTag3 = ApiService.retrofitService.getInstagramTagData("pcbuild").await()
                val hashTagList = listOf(hashTag1, hashTag2, hashTag3)

                _hashTagDataResponse.value = hashTagFilter(hashTagList)


            } catch (e: Exception) {
                Log.e("ShowcaseViewModel", e.toString())
            }
        }
    }

    private fun refreshDataFromRepo(hashTag: String) {
        viewModelScope.launch {
            try {
                postRepository.refreshPost(hashTag)

            } catch (networkError: IOException) {
                if (postList.value!!.isEmpty()) {

                }
            }
        }
    }

    private fun hashTagFilter(response: List<InstagramResponse>): List<InstagramResponse> {

        val filterWords = listOf("text", "people", "food", "drink",
            "shoes", "standing", "outdoor", "meme", "beard", "people", "closeup")

        response.forEach { it ->
            it.graphql.hashtag.edgeHashtagToMedia.edges.forEach {
                it.node.typename.filter { typeName -> typeName.equals("GraphImage") }
                it.node.accessibilityCaption.filter { caption -> caption.toString() in filterWords }
            }
        }

        val typenames = mutableListOf<String>()

        response.forEach { it.graphql.hashtag.edgeHashtagToMedia.edges.forEach {
            typenames.add(it.node.typename)
        } }
        Log.i("hashTagFilter", "${typenames.size}")

        return response.shuffled()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}