package com.example.z4fir.desktopia.screens.showcase.instagram.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class DatabasePost(
    @PrimaryKey val id: Long,
    val width: Double,
    val height: Double,
    val caption: String,
    val shortCode: String,
    val displayUrl: String)


/**
 * This function maps DatabasePost to a plain Kotlin data class [Post].
 * These objects should be displayed on screen, or manipulated by the app.
 */
fun List<DatabasePost>.asDomainModel(): List<Post> {
    return map {

        Post(
            id = it.id,
            width = it.width,
            height = it.height,
            caption = it.caption,
            shortCode = it.shortCode,
            displayUrl = it.displayUrl)
    }
}


