package com.example.z4fir.desktopia.screens.showcase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
import com.example.z4fir.desktopia.databinding.GridViewItemBinding

class InstagramTagAdapter(val data: List<String>?, private val onItemListener: InstagramTagViewHolder.OnItemListener) :
    RecyclerView.Adapter<InstagramTagAdapter.InstagramTagViewHolder>()
{

    private val TAG = InstagramTagAdapter::class.java.name

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [NodeResponse]
     * has been updated.
     */
    class InstagramTagViewHolder(
        private val binding: GridViewItemBinding,
        private val itemListener: OnItemListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?)
        {
            itemListener.onItemClick(adapterPosition)
        }

        fun bind(item: List<String>?, index: Int)
        {
            binding.urls = item
            binding.index = index
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        interface OnItemListener
        {
            fun onItemClick(position: Int)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstagramTagViewHolder
    {
        return InstagramTagViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)),
        onItemListener)
    }

    override fun getItemCount() = data!!.size

    override fun onBindViewHolder(holder: InstagramTagViewHolder, position: Int)
    {
        val instagramTagPost = data
        Log.i(TAG, " " + data!!.size)
        holder.bind(instagramTagPost, position)
    }
}


//TODO Add clickListener to RecyclerView
