package com.example.z4fir.desktopia.screens.showcase.model

import com.example.z4fir.desktopia.screens.showcase.database.DatabasePost
import com.squareup.moshi.Json

data class InstagramResponse(
    @Json(name = "graphql") val graphql: Graphql)

data class Graphql(
    @Json(name = "hashtag") val hashtag: Hashtag)

data class Hashtag(
    @Json(name = "edge_hashtag_to_media") val edgeHashtagToMedia: HashtagToMedia,
    @Json(name = "name") val hashtagName: String)

data class HashtagToMedia(
    @Json(name = "page_info") val pageInfo: PageInfo,
    @Json(name = "edges") val edges: List<Edges>)

data class PageInfo(
    @Json(name = "has_next_page") val hasNextPage: Boolean,
    @Json(name = "end_cursor") val endCursor: String)

data class Edges(
    @Json(name = "node") val node: NodeResponse)

data class NodeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "edge_media_to_caption") val edgeMediaToCaption: EdgeMediaToCaption,
    @Json(name = "shortcode") val shortCode: String,
    @Json(name = "dimensions") val dimensions: Dimensions,
    @Json(name = "__typename") val typename: String,
    @Json(name = "display_url") val displayUrl: String,
    @Json(name = "thumbnail_src") val thumbnailSrc: String,
    @Json(name = "thumbnail_resources") val thumbnailResources: List<ThumbNailRes>,
    @Json(name = "is_video") val isVideo: Boolean,
    @Json(name = "accessibility_caption") val accessibilityCaption: String = "")

data class EdgeMediaToCaption(
    @Json(name = "edges") val edges: List<CaptionNode>)

data class CaptionNode(
    @Json(name = "node") val node: NodeText)

data class NodeText(
    @Json(name = "text") val text: String)

data class Dimensions(
    @Json(name = "height") val height: Double,
    @Json(name = "width") val width: Double)

data class ThumbNailRes(
    @Json(name = "src") val src: String)


fun HashtagToMedia.asDataBaseModel(): List<DatabasePost>
{
    return edges.map {

        DatabasePost(
            id = it.node.id,
            width = it.node.dimensions.width,
            height = it.node.dimensions.height,
            caption = it.node.edgeMediaToCaption.edges[0].node.text,
            shortCode = it.node.shortCode,
            displayUrl = it.node.displayUrl)
    }
}

