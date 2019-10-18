package com.example.z4fir.desktopia.screens.showcase

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.screens.showcase.network.ApiService

class ShowcaseViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var _data: LiveData<PagedList<Edges>>
    val data: LiveData<PagedList<Edges>>
        get() = _data

    private val dataService = ApiService.retrofitService
    private val itemDataSourceFactory = ItemDataSourceFactory("battlestation", dataService)


    val networkState = Transformations.switchMap(itemDataSourceFactory.sourceLiveData) {
        it.networkState
    }
    val refreshState = Transformations.switchMap(itemDataSourceFactory.sourceLiveData) {
        it.initialLoad
    }

    init {
        initializedPagedListBuilder()
    }

    private fun initializedPagedListBuilder() {

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .build()

        _data = LivePagedListBuilder(itemDataSourceFactory, config).build()
    }

    fun refreshList() {
        itemDataSourceFactory.sourceLiveData.value!!.invalidate()
    }

}

//    fun refreshFromRepo() {
//
//        viewModelScope.launch {
//
//            try {
//                postRepository.refreshPost()
//
//            } catch (networkError: IOException) {
//
//                if (posts.value!!.isEmpty()) {
//                    Log.i("ShowcaseViewModel", "$networkError")
//                }
//            }
//        }
//    }

//    private val postRepository = PostRepository(getDatabase(application))
//    val posts = postRepository.posts

