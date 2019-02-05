package com.example.z4fir.desktopia.Fragments.ViewPagerFragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Log.d

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.z4fir.desktopia.Adapter.InstagramGridAdapter
import com.example.z4fir.desktopia.Model.InstagramResponse

import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.Util.InstagramDataFetcher
import com.example.z4fir.desktopia.Util.RestAPI
import com.example.z4fir.desktopia.Util.Tools
import com.example.z4fir.desktopia.Widgets.SpacingItemDecoration
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Response
import java.io.IOException


class InstagramFeedFragment() : Fragment()
{
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var data: ArrayList<InstagramResponse.InstagramEdgesResponse>? = null
    private val conTx: Context? = null

    companion object
    {
        fun newInstance(): InstagramFeedFragment = InstagramFeedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        handleData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.fragment_instagram_feed, container, false)



        swipeLayout = view.findViewById(R.id.recyclerview_refresh)

        viewManager = GridLayoutManager(conTx, 2)
        recyclerView = view.findViewById<RecyclerView>(R.id.insta_recyclerview).apply {

            setHasFixedSize(true)
            addItemDecoration(SpacingItemDecoration(2, Tools.dpToPx(context, 2), true))
            layoutManager = viewManager
        }
        return view
    }



    private fun handleData()
    {
        doAsync {

            var asyncData: ArrayList<InstagramResponse.InstagramEdgesResponse>? = null

            val service = RestAPI.retrofitInstance?.create(InstagramDataFetcher::class.java)
            val call = service?.getInstagramData("battlestation")

            try
            {
                val result: Response<InstagramResponse>? = call!!.execute()

                if (result!!.isSuccessful)
                {
                    val body = result.body()
                    asyncData = body!!.graphql.hashtag.edge_hashtag_to_media.edges

                    val filterTypeName = asyncData.filter { it.node.__typename == "GraphImage" }
                    val filterCaption1 = filterTypeName.filter { !it.node.accessibility_caption.contains("text") }
                    val filterCaption2 =
                        filterCaption1.filter { !it.node.accessibility_caption.contains("people standing") }
                    val filterCaption3 = filterCaption2.filter { !it.node.accessibility_caption.contains("food") }
                    val filterCaption4 = filterCaption3.filter { !it.node.accessibility_caption.contains("drink") }
                    val filterCaption5 = filterCaption4.filter { !it.node.accessibility_caption.contains("shoes") }
                    val filterCaption6 = filterCaption5.filter { !it.node.accessibility_caption.contains("standing") }
                    val filterCaption7 = filterCaption6.filter { !it.node.accessibility_caption.contains("outdoor") }
                    val filterCaption8 = filterCaption7.filter { !it.node.accessibility_caption.contains("meme") }
                    val filterCaption9 = filterCaption8.filter { !it.node.accessibility_caption.contains("beard") }

                    asyncData = filterCaption9 as ArrayList<InstagramResponse.InstagramEdgesResponse>

                }
            } catch (e: IOException)
            {

            }

            uiThread {

                data = asyncData

                viewAdapter = InstagramGridAdapter(data!!, parentFragment!!.context!!)
                recyclerView.adapter = viewAdapter

                swipeLayout.setOnRefreshListener {

                    (viewAdapter as InstagramGridAdapter).clear()
                    (viewAdapter as InstagramGridAdapter).addItem(data!!)
                    swipeLayout.isRefreshing = false
                }
            }
        }
    }

}