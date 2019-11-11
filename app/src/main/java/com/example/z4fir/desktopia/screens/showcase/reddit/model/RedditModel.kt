package com.example.z4fir.desktopia.screens.showcase.reddit.model

import com.squareup.moshi.Json


class ListingResponse(
    @Json(name = "data") val data: RedditDataResponse)

class RedditDataResponse(
    @Json(name = "children") val children: List<RedditChildren>,
    @Json(name = "after") val after: String?,
    @Json(name = "before") val before: String?
)

class RedditChildren(
    @Json(name = "data") val data: RedditPost)


class RedditPost(
    @Json(name = "subreddit") val subreddit: String,
    @Json(name = "author") val author: String,
    @Json(name = "title") val title: String,
    @Json(name = "name") val name: String,
    @Json(name = "secure_media") val secureMedia: SecureMedia?,
    @Json(name = "score") val score: Int,
    @Json(name = "preview") val preview: PreviewImages,
    @Json(name = "num_comments") val num_comments: Int,
    @Json(name = "created") val created: Long,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "stickied") val stickied: Boolean,
    @Json(name = "url") val url: String
)

class SecureMedia(
    @Json(name = "oembed") val oembed: Oembed)

class Oembed(
    @Json(name = "thumbnail_url") val thumbnailUrl: String)

class PreviewImages(
    @Json(name = "images") val images: List<PostImages>)

class PostImages(
    @Json(name = "source") val source: SourceImage,
    @Json(name = "resolutions") val resoultion: List<ImagesResolutions>)

class SourceImage(
    @Json(name = "url") val url: String)

class ImagesResolutions(
    @Json(name = "url") val url: String)




