package com.example.z4fir.desktopia.Fragments.ViewPagerFragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.util.Log.e

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.z4fir.desktopia.Adapter.InstaGridAdapter
import com.example.z4fir.desktopia.Model.InstaResponse
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.Util.RestAPI
import com.example.z4fir.desktopia.Util.Tools
import com.example.z4fir.desktopia.Widgets.SpacingItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class InstaGridFragment() : Fragment()
{
    private val instaDataRequest = RestAPI.getDataApi()
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var progress: ProgressBar? = null
    private var data: ArrayList<InstaResponse.EdgesResponse>? = null
    private val conTx: Context? = null


    companion object
    {
        fun newInstance(): InstaGridFragment = InstaGridFragment()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        d("FEED", " Getting Called In OnSave")
        outState.putParcelable("keyRecListState", viewManager.onSaveInstanceState())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.fragment_insta_feed, container, false)

        if (savedInstanceState != null)
        {
            val state: Parcelable = savedInstanceState.getParcelable("keyRecListState")
            viewManager.onRestoreInstanceState(state)
            d("FEED", " Getting Called In OnCreateView")
        }

        swipeLayout = view.findViewById(R.id.recyclerview_refresh)
        progress = view.findViewById(R.id.progressBar)


        viewManager = GridLayoutManager(conTx, 2)
        recyclerView = view.findViewById<RecyclerView>(R.id.insta_recyclerview).apply {

            setHasFixedSize(true)
            addItemDecoration(SpacingItemDecoration(2, Tools.dpToPx(context, 2), true))
            layoutManager = viewManager
        }

        populateRecyclerView()

        swipeLayout.setOnRefreshListener {
            (viewAdapter as InstaGridAdapter).clear()
            (viewAdapter as InstaGridAdapter).addItem(populateRecyclerView()!!)
            recyclerView.adapter!!.notifyDataSetChanged()
            swipeLayout.isRefreshing = false
        }

        return view
    }

    private fun View.setVisibility(isVisible: Boolean)
    {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun populateRecyclerView(): ArrayList<InstaResponse.EdgesResponse>?
    {
        GlobalScope.launch(Dispatchers.Main) {

            progress!!.setVisibility(true)
            val dataRequest = instaDataRequest.getInstaData("battlestation")
            val dataResponse = dataRequest.await()

            progress!!.setVisibility(false)

            if (dataResponse.isSuccessful)
            {
                data = imageFilter(dataResponse.body())

                viewAdapter = InstaGridAdapter(imageFilter(dataResponse.body()), parentFragment!!.context!!)
                recyclerView.adapter = viewAdapter

            } else
            {
                e("ERROR", " ${dataResponse.code()}")
            }
        }
        return data
    }


    //TODO recode to use for loop and improve filter (check for numbers in string)
    private fun imageFilter(data: InstaResponse?): ArrayList<InstaResponse.EdgesResponse>
    {
        val body = data!!.graphql.hashtag.edge_hashtag_to_media.edges

        val filterTypeName = body.filter { it.node.__typename == "GraphImage" }
        val filterCaption1 = filterTypeName.filter { !it.node.accessibility_caption.contains("text") }
        val filterCaption2 = filterCaption1.filter { !it.node.accessibility_caption.contains("closeup") }
        val filterCaption3 = filterCaption2.filter { !it.node.accessibility_caption.contains("food") }
        val filterCaption4 = filterCaption3.filter { !it.node.accessibility_caption.contains("drink") }
        val filterCaption5 = filterCaption4.filter { !it.node.accessibility_caption.contains("shoes") }
        val filterCaption6 = filterCaption5.filter { !it.node.accessibility_caption.contains("standing") }
        val filterCaption7 = filterCaption6.filter { !it.node.accessibility_caption.contains("outdoor") }
        val filterCaption8 = filterCaption7.filter { !it.node.accessibility_caption.contains("[0-9.]*") }
        val filterCaption9 = filterCaption8.filter { !it.node.accessibility_caption.contains("beard") }

        return filterCaption9 as ArrayList<InstaResponse.EdgesResponse>
    }
}