package com.example.z4fir.desktopia.screens.showcase.reddit.repo

import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.z4fir.desktopia.screens.showcase.instagram.repository.Listing
import com.example.z4fir.desktopia.screens.showcase.reddit.model.RedditPost
import com.example.z4fir.desktopia.screens.showcase.reddit.network.RedditPostApiService
import com.example.z4fir.desktopia.screens.showcase.reddit.ui.PageDataSourceFactoryReddit

class InMemoryRepoReddit {

    fun redditPost(subreddit: String, listing: String, apiService: RedditPostApiService):
            Listing<RedditPost> {

        val factory = PageDataSourceFactoryReddit(subreddit, listing, apiService)

        val pagedList = factory.toLiveData(config = Config(
            pageSize = 20,
            enablePlaceholders = false))

        val refreshState = Transformations.switchMap(factory.source) { it.initialLoad }
        val networkState = Transformations.switchMap(factory.source) { it.networkState }

        return Listing(
            pagedList = pagedList,
            networkState = networkState,
            refreshState = refreshState,
            refresh = { factory.source.value?.invalidate() }
        )
    }
}