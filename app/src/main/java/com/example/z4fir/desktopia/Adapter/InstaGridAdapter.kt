package com.example.z4fir.desktopia.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.z4fir.desktopia.Model.InstaResponse
import com.example.z4fir.desktopia.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class InstaGridAdapter(var data: ArrayList<InstaResponse.EdgesResponse>, var ctx: Context) :
    RecyclerView.Adapter<InstaGridAdapter.InstaViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstaGridAdapter.InstaViewHolder
    {
        ctx = parent.context
        val imageView = LayoutInflater.from(ctx).inflate(R.layout.item_grid_image, parent, false)

        return InstaViewHolder(imageView)

    }

    override fun getItemCount(): Int
    {
        return data.size
    }


    override fun onBindViewHolder(holder: InstaGridAdapter.InstaViewHolder, position: Int)
    {
        val stringURL: String = data[position].node.thumbnail_resources[3].src
        holder.bindView(stringURL)

    }

    fun clear()
    {
        data.clear()
        notifyDataSetChanged()
    }

    fun addItem(items: ArrayList<InstaResponse.EdgesResponse>)
    {
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class InstaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindView(stringURL: String)
        {
            val imageView = itemView.findViewById<ImageView>(R.id.image)

            Picasso.get().load(stringURL).fit().centerInside().into(imageView)
        }
    }
}