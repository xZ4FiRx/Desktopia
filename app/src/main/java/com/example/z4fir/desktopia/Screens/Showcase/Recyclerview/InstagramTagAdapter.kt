package com.example.z4fir.desktopia.Screens.Showcase.Recyclerview

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.z4fir.desktopia.Screens.Showcase.Model.InstagramResponse
import com.example.z4fir.desktopia.databinding.ItemGridImageBinding

class InstagramTagAdapter() :
    RecyclerView.Adapter<InstagramTagAdapter.InstagramTagViewHolder>()
{

    var data = listOf<InstagramResponse.EdgesResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstagramTagViewHolder
    {
        return InstagramTagViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: InstagramTagViewHolder, position: Int)
    {

    }

    class InstagramTagViewHolder private constructor(val binding: ItemGridImageBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        /**
         *The from() function needs to be in a companion object so it can be
         * called on the ViewHolder class, not called on a ViewHolder instance.
         */
        companion object
        {
            fun from(parent: ViewGroup): InstagramTagViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGridImageBinding.inflate(layoutInflater, parent, false)
                return InstagramTagViewHolder(binding)
            }
        }

        fun bind(item: InstagramResponse.NodeResponse)
        {

        }
    }
}


/**
 *
 */
class InstagramTagDiffCallback : DiffUtil.ItemCallback<InstagramResponse.EdgesResponse>()
{
    override fun areItemsTheSame(
        oldItem: InstagramResponse.EdgesResponse,
        newItem: InstagramResponse.EdgesResponse
    ): Boolean
    {
        return oldItem.node == newItem.node
    }

    override fun areContentsTheSame(
        oldItem: InstagramResponse.EdgesResponse,
        newItem: InstagramResponse.EdgesResponse
    ): Boolean
    {
        return oldItem == newItem
    }
}

//
//class InstagramTagListener(val clickListener: ())
//{
//    fun onClick(tagPost: InstagramResponse.NodeResponse) =
//
//}