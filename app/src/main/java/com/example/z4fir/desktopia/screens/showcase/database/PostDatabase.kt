package com.example.z4fir.desktopia.screens.showcase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabasePost::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {


    abstract fun postDao(): PostDao


}

private lateinit var INSTANCE: PostDatabase

fun getDatabase(context: Context): PostDatabase {

    synchronized(PostDatabase::class.java) {

        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PostDatabase::class.java,
                "post").build()
        }
    }

    return INSTANCE
}

