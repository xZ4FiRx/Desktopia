package com.example.z4fir.desktopia.screens.showcase

import android.app.Application
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.z4fir.desktopia.screens.showcase.adapter.InstagramTagAdapter
import com.example.z4fir.desktopia.screens.showcase.database.getDatabase
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import com.example.z4fir.desktopia.screens.showcase.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class ShowcaseViewModel(application: Application) : AndroidViewModel(application),
    InstagramTagAdapter.InstagramTagViewHolder.OnItemListener
{
    private val postRepository = PostRepository(getDatabase(application))
    private val postList = postRepository.posts

    private val _instagramTagResponses = MutableLiveData<List<String>>()
    val instagramTagResponse: LiveData<List<String>>
        get() = _instagramTagResponses

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init
    {
        val tags = listOf(
            "battlestations",
            "workstation",
            "pcsetups"
        )
        val randoTags = tags.shuffled()[0]

        Log.i("ShowcaseViewModel-Init", randoTags)

        getInstagramData(randoTags)
        refreshDataFromRepo(randoTags)
    }

    private fun getInstagramData(hashTag: String)
    {
        coroutineScope.launch {

            val jsonResponseList = mutableListOf<String>()

            try
            {
                val getData = ApiService.retrofitService.getInstagramTagData(hashTag).await()
                getData.graphql.hashtag.edgeHashtagToMedia.edges.forEach {
                    jsonResponseList.add(it.node.thumbnailResources[3].src)
                }
                _instagramTagResponses.value = jsonResponseList
            } catch (e: Exception)
            {
                Log.e("ShowcaseViewModel", e.toString())
            }
        }
    }

    private fun refreshDataFromRepo(hashTag: String)
    {
        viewModelScope.launch {
            try
            {
                postRepository.refreshPost(hashTag)

            } catch (networkError: IOException)
            {
                if (postList.value!!.isEmpty())
                {

                }
            }
        }
    }

    override fun onItemClick(position: Int)
    {
        Toast.makeText(getApplication(), "Item at $position", Toast.LENGTH_SHORT).show()
    }

    fun jsonResponseFilter(response: InstagramResponse?): InstagramResponse?
    {
        //TODO Filter response

        return response
    }

    //TODO Create a method to randomize hashTags.


    override fun onCleared()
    {
        super.onCleared()
        viewModelJob.cancel()
    }
}