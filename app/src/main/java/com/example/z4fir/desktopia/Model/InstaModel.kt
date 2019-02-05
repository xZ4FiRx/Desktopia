package com.example.z4fir.desktopia.Model

import android.os.Parcelable


data class InstagramResponse(val graphql: InstagramGraphqlResponse)
{

    data class InstagramGraphqlResponse(val hashtag: InstagramHashtagResponse)

    data class InstagramHashtagResponse(val edge_hashtag_to_media: InstagramHashtagToMediaResponse)

    data class InstagramHashtagToMediaResponse(
        val page_info: InstagramPageInfo, val edges: ArrayList<InstagramEdgesResponse>
    )

    data class InstagramPageInfo(
        val has_next_page: Boolean,
        val end_cursor: String
    )

    data class InstagramEdgesResponse(val node: InstagramNodeResponse)
    data class InstagramNodeResponse(
        val __typename: String,
        val shortcode: String,
        val display_url: String,
        val thumbnail_src: String,
        val is_video: Boolean,
        val accessibility_caption: String
    )
}

annotation class Parcelize

