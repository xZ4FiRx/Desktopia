package com.example.z4fir.desktopia.screens.showcase.reddit.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.NetworkStateLayoutBinding
import com.example.z4fir.desktopia.databinding.RedditListViewItemBinding
import com.example.z4fir.desktopia.screens.showcase.reddit.model.RedditPost
import com.example.z4fir.desktopia.util.NetworkState
import com.example.z4fir.desktopia.util.NetworkStateViewHolder
import java.lang.IllegalArgumentException
import java.util.*


class RedditPostAdapter : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(DIFF_COMPARATOR) {

    private var networkState: NetworkState? = null

    companion object {

        val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                oldItem.name != newItem.name

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.reddit_list_view_item -> RedditPostViewHolder(RedditListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
            R.layout.network_state_layout -> NetworkStateViewHolder(NetworkStateLayoutBinding.inflate(LayoutInflater.from(parent.context)))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            R.layout.reddit_list_view_item -> (holder as RedditPostViewHolder).bindTo(getItem(position))
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
            R.layout.reddit_list_view_item
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

    class RedditPostViewHolder(private val binding: RedditListViewItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var sourceUrl: String
        private lateinit var postUrl: String
        private lateinit var title: String
        private lateinit var author: String
        private var created: Long = 0
        private var score = 0


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            var bundle = bundleOf("sourceurl" to sourceUrl,
                "posturl" to postUrl, "title" to title,
                "author" to author, "created" to created,
                "score" to score)

            view?.findNavController()?.navigate(R.id.redditPostDetailFragment, bundle)
        }

        fun bindTo(post: RedditPost?) {

            post?.preview?.images?.forEach {
                sourceUrl = it.source.url
            }

            postUrl = post!!.url
            title = post.title
            author = post.author!!
            created = post.created
            score = post.score!!


            binding.title = post!!.title
            binding.score = post.score.toString()
            binding.author = post.author

            post.preview?.images?.forEach {
                binding.url = it.source.url.replace("amp;", "")
            }

            val data = Date(post.created * 1000)
            val sdf = java.text.SimpleDateFormat("EEE, d MMM yy", Locale.US)

            binding.time = sdf.format(data)


        }
    }
}