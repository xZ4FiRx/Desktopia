package com.example.z4fir.desktopia.screens.showcase.instagram.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.z4fir.desktopia.screens.showcase.instagram.adapter.InstagramTagAdapter

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.screens.showcase.instagram.ui.ShowcaseViewModel
import com.example.z4fir.desktopia.screens.showcase.instagram.network.NetworkState
import com.example.z4fir.desktopia.screens.showcase.instagram.model.Edges
import com.example.z4fir.desktopia.util.SpacingItemDecoration
import androidx.appcompat.app.AppCompatActivity
import com.example.z4fir.desktopia.databinding.FragmentInstagramShowcaseBinding


class InstagramShowcaseFragment : Fragment() {

    companion object {
        const val KEY = "hashtag"
        const val DEFAULT_HASHTAG = "battlestation"
    }

    private lateinit var binding: FragmentInstagramShowcaseBinding
    private lateinit var model: ShowcaseViewModel
    private val adapter = InstagramTagAdapter()
    private var toolbarTitle = ""

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY, model.currentHashtag())
        outState.putString("toolbar", toolbarTitle)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        model = ViewModelProviders.of(this).get(ShowcaseViewModel(activity!!.application)::class.java)

        val hashtag = savedInstanceState?.getString(KEY) ?: DEFAULT_HASHTAG
        model.addingHashtag(hashtag)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentInstagramShowcaseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = model

        toolbarTitle = savedInstanceState?.getString("toolbar") ?: ""

        binding.showcaseToolbar.title = toolbarTitle

        (activity as AppCompatActivity).setSupportActionBar(binding.showcaseToolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
            model.refreshList()
        }

        initAdapter()
        initStates()
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

    private fun initStates() {

        val swipe = binding.swipeRefresh
        val recyclerview = binding.showcaseList

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        model.refreshState.observe(this, Observer {

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
        inflater.inflate(R.menu.instagram_showcase_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_selection -> {

            binding.showcaseToolbar.title = toolbarTitle

            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.hashtag_chooser)

                .setItems(R.array.hashtag_array) { dialog, id ->

                    when (id) {
                        0 -> {
                            toolbarTitle = "#Battlestation"
                            model.addingHashtag("battlestation")
                            refreshLayout()
                            model.refreshList()
                            dialog.dismiss()

                        }
                        1 -> {
                            toolbarTitle = "#Pcgamingsetup"
                            model.addingHashtag("pcgamingsetup")
                            refreshLayout()
                            model.refreshList()
                            dialog.dismiss()
                        }
                        2 -> {
                            toolbarTitle = "#Workstation"
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