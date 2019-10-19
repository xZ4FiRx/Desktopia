package com.example.z4fir.desktopia.screens.showcase.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.z4fir.desktopia.screens.showcase.adapter.InstagramTagAdapter

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.screens.showcase.ui.ShowcaseViewModel
import com.example.z4fir.desktopia.databinding.FragmentShowcaseBinding
import com.example.z4fir.desktopia.screens.showcase.network.NetworkState
import com.example.z4fir.desktopia.screens.showcase.model.Edges
import com.example.z4fir.desktopia.util.SpacingItemDecoration
import androidx.appcompat.app.AppCompatActivity


class InstagramTagFragment : Fragment() {

    companion object {
        const val KEY = "hashtag"
        const val DEFAULTHASHTAG = "battlestation"
    }

    private lateinit var binding: FragmentShowcaseBinding
    private lateinit var model: ShowcaseViewModel
    private val adapter = InstagramTagAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        model = ViewModelProviders.of(this).get(ShowcaseViewModel(activity!!.application)::class.java)
        binding = FragmentShowcaseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = model

        model.addingHashtag(DEFAULTHASHTAG)
        binding.showcaseToolbar.title = "#Battlestation"

        binding.swipeRefresh.setOnRefreshListener {
            model.refreshList()
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.showcaseToolbar)

        initAdapter()
        states()

        return binding.root
    }


    private fun initAdapter() {

        binding.showcaseList.adapter = adapter

        val gm = GridLayoutManager(activity, 2)
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

        model.post.observe(this, Observer<PagedList<Edges>> {
            adapter.submitList(it)
        })
    }

    fun states() {

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        model.refreshState.observe(this, Observer {

            val swipe = binding.swipeRefresh
            val recyclerview = binding.showcaseList

            swipe.isRefreshing = it == NetworkState.LOADING

            if (swipe.isRefreshing) {
                recyclerview.visibility = View.GONE
            } else {
                adapter.notifyDataSetChanged()
                recyclerview.visibility = View.VISIBLE
            }
        })
    }


    private fun refreshLayout() {
        binding.swipeRefresh.isRefreshing = true
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.showcase_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_selection -> {

            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.hashtag_chooser)

                .setItems(R.array.hashtag_array) { dialog, id ->

                    when (id) {
                        0 -> {

                            binding.showcaseToolbar.title = "#Battlestation"
                            model.addingHashtag("battlestation")
                            refreshLayout()
                            model.refreshList()
                            dialog.dismiss()

                        }
                        1 -> {
                            binding.showcaseToolbar.title = "#Pcgamingsetup"
                            model.addingHashtag("pcgamingsetup")
                            refreshLayout()
                            model.refreshList()
                            dialog.dismiss()
                        }
                        2 -> {
                            binding.showcaseToolbar.title = "#Workstation"
                            model.addingHashtag("cat")
                            refreshLayout()
                            model.refreshList()
                            dialog.dismiss()
                        }
                    }
                }

            builder.show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}