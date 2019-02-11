package com.example.z4fir.desktopia.Model

import com.google.gson.annotations.SerializedName


data class InstaResponse(val graphql: GraphqlResponse)
{

    data class GraphqlResponse(val hashtag: HashtagResponse)

    data class HashtagResponse(val edge_hashtag_to_media: HashtagToMediaResponse)

    data class HashtagToMediaResponse(
        val page_info: PageInfo,
        val edges: ArrayList<EdgesResponse>
    )

    data class PageInfo(
        val has_next_page: Boolean,
        val end_cursor: String
    )

    data class EdgesResponse(val node: NodeResponse)
    data class NodeResponse(
        @SerializedName("shortcode")
        val shortCode: String,
        @SerializedName("__typename")
        val __typename: String,
        @SerializedName("display_url")
        val display_url: String,
        @SerializedName("thumbnail_src")
        val thumbnail_src: String,
        @SerializedName("thumbnail_resources")
        val thumbnail_resources: ArrayList<ThumbNailResResponse>,
        @SerializedName("is_video")
        val is_video: Boolean,
        @SerializedName("accessibility_caption")
        val accessibility_caption: String
    )

    data class ThumbNailResResponse(
        @SerializedName("config_height")
        val configHeight: Int,
        @SerializedName("config_width")
        val configWidth: Int,
        val src: String
    )
}

