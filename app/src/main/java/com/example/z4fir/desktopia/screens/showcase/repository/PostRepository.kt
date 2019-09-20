package com.example.z4fir.desktopia.screens.showcase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.z4fir.desktopia.screens.showcase.database.PostDatabase
import com.example.z4fir.desktopia.screens.showcase.database.asDomainModel
import com.example.z4fir.desktopia.screens.showcase.model.Post
import com.example.z4fir.desktopia.screens.showcase.model.asDataBaseModel
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PostRepository(private val database: PostDatabase)
{
    val posts: LiveData<List<Post>> = Transformations.map(database.postDao.getPost())
    {
        it.asDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using "withContext" this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshPost(hashTag: String)
    {
        withContext(Dispatchers.IO)
        {
            val jsonResponseList = mutableListOf<String>()
            val postList = ApiService.retrofitService.getInstagramTagData(hashTag).await()
            database.postDao.insertAll(postList.asDataBaseModel())
        }
    }
}