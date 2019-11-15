package com.example.z4fir.desktopia.screens.showcase.reddit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.z4fir.desktopia.screens.showcase.reddit.network.ApiServiceReddit
import com.example.z4fir.desktopia.screens.showcase.reddit.repo.InMemoryRepoReddit

class RedditViewModel : ViewModel() {

    private val service = ApiServiceReddit.retrofitService
    val subredditString = MutableLiveData<String>()
    private val listingString = MutableLiveData<String>()
    private val repo = InMemoryRepoReddit()
    private val subredditResult = map(subredditString) {
        repo.redditPost(it, listingString.value!!, service)
    }

    //
    val post = Transformations.switchMap(subredditResult) { it.pagedList }
    val networkState = Transformations.switchMap(subredditResult) { it.networkState }
    val refreshState = Transformations.switchMap(subredditResult) { it.refreshState }


    fun selectSubreddit(subreddit: String) {
        if (subredditString.value == subreddit) {

        }
        subredditString.value = subreddit
    }

    fun selectListing(listing: String) {
        if (listingString.value == listing) {

        }
        listingString.value = listing
    }

    fun refreshList() {
        subredditResult.value?.refresh?.invoke()
    }

    fun currentSubreddit(): String? = subredditString.value?.capitalize()
    fun currentListing(): String? = listingString.value

    fun setListing(listing: String) {

        when (listing) {

            "hot" -> {
                listingString.value = "hot"
            }
            "new" -> {
                listingString.value = "new"
            }
            "rising" -> {
                listingString.value = "rising"
            }
            "top" -> {
                listingString.value = "top"
            }
        }
    }
}