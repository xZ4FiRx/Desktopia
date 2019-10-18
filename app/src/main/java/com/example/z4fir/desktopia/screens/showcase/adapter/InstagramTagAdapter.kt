package com.example.z4fir.desktopia.screens.showcase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.InstagramGridViewItemBinding
import com.example.z4fir.desktopia.databinding.NetworkStateLayoutBinding
import com.example.z4fir.desktopia.screens.showcase.network.NetworkState
import com.example.z4fir.desktopia.screens.showcase.network.Status
import java.lang.IllegalArgumentException
import kotlin.properties.Delegates
import androidx.recyclerview.widget.*
import com.example.z4fir.desktopia.screens.showcase.database.Post
import com.example.z4fir.desktopia.screens.showcase.model.Edges


class InstagramTagAdapter : PagedListAdapter<Edges,
        RecyclerView.ViewHolder>(DIFF_COMPARATOR) {

    private var networkState: NetworkState? = null

    companion object {

        val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<Edges>() {
            override fun areItemsTheSame(oldItem: Edges, newItem: Edges): Boolean =
                oldItem.node.id != newItem.node.id

            override fun areContentsTheSame(oldItem: Edges, newItem: Edges): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.instagram_grid_view_item -> InstagramTagViewHolder(InstagramGridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
            R.layout.network_state_layout -> NetworkStateViewHolder(NetworkStateLayoutBinding.inflate(LayoutInflater.from(parent.context)))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            R.layout.instagram_grid_view_item -> (holder as InstagramTagViewHolder).bindTo(getItem(position))
            R.layout.network_state_layout -> (holder as NetworkStateViewHolder).bindView(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_layout
        } else {
            R.layout.instagram_grid_view_item
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }

    fun setNetworkState(networkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = networkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class InstagramTagViewHolder(private val binding: InstagramGridViewItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var url: String
        private lateinit var hashtag: String
        private lateinit var shortCode: String
        private var height by Delegates.notNull<Double>()
        private var width by Delegates.notNull<Double>()

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            val bundle = bundleOf("url" to url, "hashtag" to hashtag,
                "shortcode" to shortCode, "height" to height, "width" to width)

            Log.i("ViewHolderOnClick\n",
                "ImageHeight: $height\n" +
                        "ImageWidth: $width\n" +
                        "displayImage: $url\n" +
                        "shortCode: $shortCode\n" +
                        "hashtag: $hashtag")

            view!!.findNavController().navigate(R.id.instagramDialogFragment, bundle)
        }

        fun bindTo(post: Edges?) {

            binding.url = post!!.node.displayUrl
            binding.index = adapterPosition
            binding.executePendingBindings()

            url = post.node.displayUrl
            this.hashtag = "placeholder"
            this.shortCode = post.node.shortCode
            this.height = post.node.dimensions.height
            this.width = post.node.dimensions.width
        }
    }

    internal class NetworkStateViewHolder(binding: NetworkStateLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val progressBar = binding.progressBar


        fun bindView(networkState: NetworkState?) {
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
}