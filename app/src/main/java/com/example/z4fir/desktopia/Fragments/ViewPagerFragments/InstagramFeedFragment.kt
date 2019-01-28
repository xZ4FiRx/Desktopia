package com.example.z4fir.desktopia.Fragments.ViewPagerFragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.z4fir.desktopia.MainActivity
import com.example.z4fir.desktopia.Model.InstaModel

import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.Util.InstagramDataFetcher
import com.example.z4fir.desktopia.Util.RestAPI
import com.example.z4fir.desktopia.Util.Tools
import com.example.z4fir.desktopia.Widgets.SpacingItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class InstagramFeedFragment : Fragment()
{
    private var items: ArrayList<InstaModel.InstagramResponse>

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val ctx: Context? = null

    //Instagram JSON Data
    private var endCursor: String? = null
    private var typeName: String? = null
    private var shortCode: String? = null
    private var displayURL: String? = null
    private var isVideo: Boolean? = null
    private var accessibilityCaption: String? = null
    private var imageSrc: String? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        retrofitCall("battlestations")

    }

    companion object
    {
        fun newInstance(): InstagramFeedFragment = InstagramFeedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        var view: View = inflater.inflate(R.layout.fragment_instagram_feed, container, false)


        viewManager = GridLayoutManager(ctx, 2)
        viewAdapter = InstaRecAdapter(items)

        recyclerView = view.findViewById<RecyclerView>(R.id.insta_recyclerview).apply {

            setHasFixedSize(true)
            addItemDecoration(SpacingItemDecoration(2, Tools.dpToPx(this!!.context!!, 4), true))
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    private fun retrofitCall(hashtag: String)
    {
        val service = RestAPI.instagramRetrofitInstance?.create(InstagramDataFetcher::class.java)
        val call = service?.getInstagramData(hashtag)
        call?.enqueue(object : Callback<InstaModel.InstagramResponse>
        {
            override fun onFailure(call: Call<InstaModel.InstagramResponse>, t: Throwable)
            {
                Log.d("FEED", " $t")
            }

            override fun onResponse(
                call: Call <InstaModel.InstagramResponse>,
                response: Response <InstaModel.InstagramResponse>
            )
            {
                for (item in items!!.indices)
                {
                    d("FEED", item.toString())
                }
            }
        })
    }


    class InstaRecAdapter(private val data: ArrayList<InstaModel.InstagramResponse>) :
        RecyclerView.Adapter<InstaRecAdapter.InstaViewHolder>()
    {
        private val ctx: Context? = null

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): InstaRecAdapter.InstaViewHolder
        {
            val imageView = LayoutInflater.from(ctx).inflate(R.layout.item_grid_image, parent, false) as ImageView

            return InstaViewHolder(imageView)

        }

        override fun getItemCount(): Int
        {
            return data.size
        }

        override fun onBindViewHolder(holder: InstaRecAdapter.InstaViewHolder, p1: Int)
        {
            Glide.with(ctx).load(data[p1].graphql.hashtag.edge_hashtag_to_media.edges[p1].node.thumbnail_src)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.itemView as ImageView)
        }

        class InstaViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView)
    }
}

