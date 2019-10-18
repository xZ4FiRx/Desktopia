package com.example.z4fir.desktopia.screens.showcase.database




data class Post(
    val id: Long,
    val width: Double,
    val height: Double,
    val caption: String,
    val shortCode: String,
    val displayUrl: String)