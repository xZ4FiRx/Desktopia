package com.example.z4fir.desktopia.Screens.Showcase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.z4fir.desktopia.Screens.Showcase.Model.InstagramResponse
import com.example.z4fir.desktopia.Screens.Showcase.Network.ApiService
import com.example.z4fir.desktopia.Screens.Showcase.Network.InstagramTagApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ShowcaseViewModel : ViewModel()
{

    private val _instagramTagResponse = MutableLiveData<List<InstagramResponse>>()
    val instagramTagResponse: LiveData<List<InstagramResponse>>
        get() = _instagramTagResponse

    /**
     * The Deferred interface defines a coroutine job that returns a result value (Deferred inherits from Job). The Deferred interface includes
     * a method called await(), which causes your code to wait without blocking until the value is ready, and then that value is returned.
     */
    private var viewModel = Job()
    private val coroutineScope = CoroutineScope(viewModel + Dispatchers.Main)


    fun getInstramgramTagData()
    {
        coroutineScope.launch {

            var getDeferredData = ApiService.retrofitService.getInstagramTagData("cat")

            try
            {
                var result = getDeferredData.await()

            } catch (e: Exception)
            {
                Log.e("ShowcaseViewModel", e.toString())
            }

        }
    }

    //TODO
    fun tagFilter()
    {

    }

    override fun onCleared()
    {
        super.onCleared()
        viewModel.cancel()
    }
}