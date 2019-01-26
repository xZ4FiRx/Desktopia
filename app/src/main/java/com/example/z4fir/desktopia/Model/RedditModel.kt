package com.example.z4fir.desktopia.Model

object RedditModel
{

    class RedditNewsResponse(val data: RedditDataResponse)

    class RedditDataResponse(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
    )

    class RedditChildrenResponse(val data: RedditNewsDataResponse)

    //TODO Get more data values from Reddit JSON.
    class RedditNewsDataResponse(
        val author: String,
        val title: String,
        val num_comments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
    )
}
