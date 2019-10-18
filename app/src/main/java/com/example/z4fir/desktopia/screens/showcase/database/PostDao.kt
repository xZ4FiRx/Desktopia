package com.example.z4fir.desktopia.screens.showcase.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
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