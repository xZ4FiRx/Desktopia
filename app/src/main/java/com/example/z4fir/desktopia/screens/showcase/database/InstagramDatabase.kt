package com.example.z4fir.desktopia.screens.showcase.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.z4fir.desktopia.screens.showcase.model.Post


/**
 * InstagramPost represents a post entity in the database.
 */
@Entity
data class InstagramDatabasePost constructor(
    @PrimaryKey
    val shortCode: String,
    val hashTag: String,
    val thumbNail: String
)

fun List<InstagramDatabasePost>.asDomainModel(): List<Post>
{
    return map {
        Post(
            hashTag = it.hashTag,
            thumbNail = it.thumbNail,
            shortCode = it.shortCode
        )
    }
}