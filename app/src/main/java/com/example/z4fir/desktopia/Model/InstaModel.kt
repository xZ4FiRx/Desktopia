package com.example.z4fir.desktopia.Model

object InstaModel
{

    class InstagramResponse(val graphql: InstagramGraphqlResponse)

    class InstagramGraphqlResponse(val hashtag: InstagramHashtagResponse)

    class InstagramHashtagResponse(val edge_hashtag_to_media: InstagramHashtagToMediaResponse)

    class InstagramHashtagToMediaResponse(
        val page_info: InstagramPageInfo,
        val edges: ArrayList<InstagramEdgesResponse>)

    class InstagramPageInfo(
        val has_next_page: Boolean,
        val end_cursor: String)

    class InstagramEdgesResponse(val node: InstagramNodeResponse)

    class InstagramNodeResponse(
        val __typename: String,
        val shortCode: String,
        val display_url: String,
        val thumbnail_src: String,
        val thumbnail_resources: ArrayList<InstagramThumbnailResourceResponse>,
        val is_video: Boolean,
        val accessibility_caption: String)

    class InstagramThumbnailResourceResponse(
        val src: String,
        val config_width: Int,
        val config_height: Int)

}
