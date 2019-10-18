package com.example.z4fir.desktopia.screens.showcase.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.z4fir.desktopia.screens.showcase.adapter.InstagramTagAdapter

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.screens.showcase.ShowcaseViewModel
import com.example.z4fir.desktopia.databinding.FragmentShowcaseBinding
import com.example.z4fir.desktopia.screens.showcase.database.Post
import com.example.z4fir.desktopia.screens.showcase.network.NetworkState
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.util.SpacingItemDecoration


class InstagramTagFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val viewModel = ViewModelProviders.of(this).get(ShowcaseViewModel(activity!!.application)::class.java)

        val binding = FragmentShowcaseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = InstagramTagAdapter()
        val gm = GridLayoutManager(activity, 2)
        binding.showcaseList.adapter = adapter
        gm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                R.layout.network_state_layout -> 2
                R.layout.instagram_grid_view_item -> 1
                else -> 1

            }
        }
        binding.showcaseList.layoutManager = gm

        binding.showcaseList.addItemDecoration(SpacingItemDecoration(2,
            4, true), 0)

        viewModel.data.observe(this, Observer<PagedList<Edges>> {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.refreshState.observe(this, Observer {
            val swipe = binding.swipeRefresh
            val recyclerview = binding.showcaseList

            swipe.isRefreshing = it == NetworkState.LOADING

            if (swipe.isRefreshing) {
                recyclerview.visibility = View.GONE
            } else {
                recyclerview.visibility = View.VISIBLE
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshList()
        }

        return binding.root
    }
}