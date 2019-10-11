package com.example.z4fir.desktopia.screens.showcase.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.balysv.materialripple.MaterialRippleLayout
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.GridViewItemBinding
import com.example.z4fir.desktopia.screens.showcase.model.EdgesResponse
import kotlin.properties.Delegates

class InstagramTagAdapter : PagedListAdapter<EdgesResponse,
        InstagramTagAdapter.InstagramTagViewHolder>(DIFF_COMPARATOR) {


    companion object {

        val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<EdgesResponse>() {
            override fun areItemsTheSame(oldItem: EdgesResponse, newItem: EdgesResponse): Boolean =
                oldItem.node.id != newItem.node.id

            override fun areContentsTheSame(oldItem: EdgesResponse, newItem: EdgesResponse): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstagramTagViewHolder {

        Log.i("InstagramTagAdapter", "onCreateViewHolder Called")
        return InstagramTagViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: InstagramTagViewHolder, position: Int) {

        val item = getItem(position)

        val displayImage = item!!.node.displayUrl
        val thumbnail = item.node.thumbnailResources[3].src
        val hashtag = "placeholder"
        val shortCode = item.node.shortCode
        val imageHeight = item.node.dimensions.height
        val imageWidth = item.node.dimensions.width

        holder.bind(position, displayImage, thumbnail, hashtag, shortCode, imageHeight, imageWidth)
    }


    class InstagramTagViewHolder(private val binding: GridViewItemBinding)
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

        fun bind(index: Int, displayImage: String, thumbnail: String, hashtag: String,
            shortCode: String, height: Double, width: Double) {

            binding.url = thumbnail
            binding.index = index
            binding.executePendingBindings()

            url = displayImage
            this.hashtag = hashtag
            this.shortCode = shortCode
            this.height = height
            this.width = width
        }
    }
}