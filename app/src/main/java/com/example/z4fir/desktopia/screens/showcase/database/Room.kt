package com.example.z4fir.desktopia.screens.showcase.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PostDao
{
    @Query("select * from instagramdatabasepost")
    fun getPost(): LiveData<List<InstagramDatabasePost>>

    /**
     * [OnConflictStrategy.REPLACE] overrides the database entry
     * if the post entry is already present in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(post: List<InstagramDatabasePost>)
}

@Database(entities = [InstagramDatabasePost::class], version = 1)
abstract class PostDatabase() : RoomDatabase()
{
    abstract val postDao: PostDao
}

private lateinit var INSTANCE: PostDatabase

/**
 * [isInitialized] Kotlin property returns true if the lateinit property (INSTANCE)
 * has been assigned a value and false otherwise.
 */
fun getDatabase(context: Context): PostDatabase
{
    synchronized(PostDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized)
        {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PostDatabase::class.java,
                "post"
            ).build()
        }
    }
    return INSTANCE
}