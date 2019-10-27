package com.example.z4fir.desktopia.screens.showcase.instagram.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PostDao {


    @Query("SELECT * from post_table")
    fun getAllPost(): DataSource.Factory<Int,Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPost(post: List<DatabasePost>)

}