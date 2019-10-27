package com.example.z4fir.desktopia.screens.showcase.instagram.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.example.z4fir.desktopia.screens.showcase.instagram.network.ApiService
import com.example.z4fir.desktopia.screens.showcase.instagram.repository.InMemoryRepo

class ShowcaseViewModel(application: Application) : AndroidViewModel(application) {


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

        if (hashtagString.value == hashtag) {

        }
        hashtagString.value = hashtag
    }

    fun refreshList() {
        hashtagResult.value?.refresh?.invoke()
    }

    fun currentHashtag(): String? = hashtagString.value
}


