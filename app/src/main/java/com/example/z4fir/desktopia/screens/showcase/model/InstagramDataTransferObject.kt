package com.example.z4fir.desktopia.screens.showcase.model

import com.example.z4fir.desktopia.screens.showcase.database.InstagramDatabasePost
import com.squareup.moshi.Json


data class InstagramResponse(@Json(name = "graphql") val graphql: GraphqlResponse)
data class GraphqlResponse(@Json(name = "hashtag") val hashtag: HashtagResponse)
data class HashtagResponse(@Json(name = "edge_hashtag_to_media") val edgeHashtagToMedia: HashtagToMediaResponse,
    @Json(name = "name") val hashtagName: String)

data class HashtagToMediaResponse(@Json(name = "page_info") val pageInfo: PageInfo,
    @Json(name = "edges") val edges: List<EdgesResponse>)

data class PageInfo(@Json(name = "has_next_page") val hasNextPage: Boolean,
    @Json(name = "end_cursor") val endCursor: String)

data class EdgesResponse(@Json(name = "node") val node: NodeResponse)
data class NodeResponse(@Json(name = "shortcode") val shortCode: String,
    @Json(name = "dimensions") val dimensions: Dimensions,
    @Json(name = "__typename") val typename: String,
    @Json(name = "display_url") val displayUrl: String,
    @Json(name = "thumbnail_src") val thumbnailSrc: String,
    @Json(name = "thumbnail_resources") val thumbnailResources: List<ThumbNailResResponse>,
    @Json(name = "is_video") val isVideo: Boolean,
    @Json(name = "accessibility_caption") val accessibilityCaption: String = "")

data class Dimensions(@Json(name = "height") val height: Double,
    @Json(name = "width") val width: Double)

data class ThumbNailResResponse(@Json(name = "src") val src: String)


/**
 * Convert Network results to database objects
 */
fun InstagramResponse.asDataBaseModel(): List<InstagramDatabasePost> {
    return graphql.hashtag.edgeHashtagToMedia.edges.map {
        InstagramDatabasePost(
            shortCode = it.node.shortCode,
            thumbNail = it.node.thumbnailResources[3].src,
            hashTag = graphql.hashtag.hashtagName)
    }
}


