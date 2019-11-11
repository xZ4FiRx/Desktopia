package com.example.z4fir.desktopia.screens.showcase.reddit.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.z4fir.desktopia.screens.showcase.reddit.model.RedditPost
import com.example.z4fir.desktopia.screens.showcase.reddit.network.RedditPostApiService


class PageDataSourceFactoryReddit(private val subreddit: String,
    private val listing: String, private val apiService: RedditPostApiService) :
    DataSource.Factory<String, RedditPost>() {

    val source = MutableLiveData<PageDataSourceReddit>()
    override fun create(): DataSource<String, RedditPost> {
        val source = PageDataSourceReddit(subreddit, listing, apiService)
        this.source.postValue(source)

        return source
    }
}