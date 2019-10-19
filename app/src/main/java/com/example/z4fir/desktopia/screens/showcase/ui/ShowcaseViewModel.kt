package com.example.z4fir.desktopia.screens.showcase.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.*
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import com.example.z4fir.desktopia.screens.showcase.repository.InMemoryRepo

class ShowcaseViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var _data: LiveData<PagedList<Edges>>
    val data: LiveData<PagedList<Edges>>
        get() = _data

    private val dataService = ApiService.retrofitService

    private val hashtagString = MutableLiveData<String>()
    private val repo = InMemoryRepo()
    private val hashtagResult = map(hashtagString) {
        repo.tagPostOfInstagram(it, dataService)
    }

    val post = switchMap(hashtagResult) { it.pagedList }
    val networkState = switchMap(hashtagResult) { it.networkState }
    val refreshState = switchMap(hashtagResult) { it.refreshState }

    fun addingHashtag(hashtag: String) {

        if (hashtagString.value == null) {
            hashtagString.value = "battlestation"
        }

        hashtagString.value = hashtag
    }

    fun refreshList() {
        hashtagResult.value?.refresh?.invoke()
    }

    fun defaultHashtag(): String? = hashtagString.value
}


