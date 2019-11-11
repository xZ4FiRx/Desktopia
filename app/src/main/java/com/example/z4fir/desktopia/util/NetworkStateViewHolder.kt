package com.example.z4fir.desktopia.util

import android.graphics.PorterDuff
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.z4fir.desktopia.databinding.NetworkStateLayoutBinding

class NetworkStateViewHolder(binding: NetworkStateLayoutBinding)
    : RecyclerView.ViewHolder(binding.root) {

    private val progressBar = binding.networkProgressBar

    fun bindView(networkState: NetworkState?) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            progressBar.indeterminateDrawable
                .setColorFilter(0xABABAB, PorterDuff.Mode.ADD)
        }
        progressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
    }

    private fun toVisibility(constraint: Boolean): Int {
        return if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}