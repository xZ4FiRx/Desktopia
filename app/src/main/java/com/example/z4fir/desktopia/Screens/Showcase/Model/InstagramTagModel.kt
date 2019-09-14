package com.example.z4fir.desktopia.Screens.Showcase.Model

//TODO Add Moshi version of SerializedName annotaion like GSON.

data class InstagramResponse(val graphql: GraphqlResponse)
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
        val shortCode: String,
        val __typename: String,
        val display_url: String,
        val thumbnail_src: String,
        val thumbnail_resources: ArrayList<ThumbNailResResponse>,
        val is_video: Boolean,
        val accessibility_caption: String
    )

    data class ThumbNailResResponse(
        val configHeight: Int,
        val configWidth: Int,
        val src: String
    )
}

