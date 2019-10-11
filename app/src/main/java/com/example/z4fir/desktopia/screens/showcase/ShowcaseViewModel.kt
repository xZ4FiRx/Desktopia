package com.example.z4fir.desktopia.screens.showcase

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.z4fir.desktopia.screens.showcase.model.EdgesResponse
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.screens.showcase.network.ApiService
import kotlinx.coroutines.Job

class ShowcaseViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var _hashTagDataResponse: LiveData<PagedList<EdgesResponse>>
    val hashTagDataResponse: LiveData<PagedList<EdgesResponse>>
        get() = _hashTagDataResponse

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .build()
        _hashTagDataResponse = initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<String, EdgesResponse> {
        val factory = object : DataSource.Factory<String, EdgesResponse>() {
            override fun create(): DataSource<String, EdgesResponse> {
                val dataService = ApiService.retrofitService
                return ItemDataSource("battlestation", dataService)
            }
        }
        return LivePagedListBuilder<String, EdgesResponse>(factory, config)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
