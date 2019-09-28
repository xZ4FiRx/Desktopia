package com.example.z4fir.desktopia.screens.showcase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.GridViewItemBinding
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import kotlin.properties.Delegates

class InstagramTagAdapter(private val dataResponse: List<InstagramResponse>?)
    : RecyclerView.Adapter<InstagramTagAdapter.InstagramTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstagramTagViewHolder {

        return InstagramTagViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = dataResponse!!.size

    override fun onBindViewHolder(holder: InstagramTagViewHolder, position: Int) {

        var imageHeight = .0
        var imageWidth = .0
        var displayImage = ""
        var shortCode = ""
        var thumbnail = ""
        val hashtag = dataResponse!![position].graphql.hashtag.hashtagName
        dataResponse[position].graphql.hashtag.edgeHashtagToMedia.edges.forEach {

            shortCode = it.node.shortCode
            displayImage = it.node.displayUrl
            thumbnail = it.node.thumbnailResources[3].src
            imageHeight = it.node.dimensions.height
            imageWidth = it.node.dimensions.width
        }

        Log.i("BindViewHolder Values\n",
            "ImageHeight: $imageHeight\n" +
                    "ImageWidth: $imageWidth\n" +
                    "displayImage: $displayImage\n" +
                    "shortCode: $shortCode\n" +
                    "thumbnail: $thumbnail\n" +
                    "hashtag: $hashtag")

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

        fun bind(index: Int, displayImage: String, thumbnail: String, hashtag: String, shortCode: String,
            height: Double, width: Double) {

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