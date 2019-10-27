package com.example.z4fir.desktopia.screens.showcase.instagram.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.z4fir.desktopia.screens.showcase.instagram.network.NetworkState

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit)
