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
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
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
        outState.putString("retain", "retain")
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

        setToolbar()
        setHashButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initStates()

    }

    private fun setToolbar() {

        (activity as AppCompatActivity).setSupportActionBar(binding.showcaseToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.showcaseToolbar.title = ""
        binding.showcaseToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setHashButtons() {

        val button1 = binding.instagramHashtagButton1
        val button2 = binding.instagramHashtagButton2
        val button3 = binding.instagramHashtagButton3

        button1.isEnabled = false

        button1.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("battlestation")
                refreshLayout()
                model.refreshList()

                button1.isEnabled = false
                button2.isEnabled = true
                button3.isEnabled = true
            }
        }

        button2.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("desksetup")
                model.refreshList()
                refreshLayout()

                button1.isEnabled = true
                button2.isEnabled = false
                button3.isEnabled = true
            }
        }

        button3.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("dreamsetup")
                model.refreshList()
                refreshLayout()

                button1.isEnabled = true
                button2.isEnabled = true
                button3.isEnabled = false
            }
        }
    }


    private fun initAdapter() {

        val button1 = binding.instagramHashtagButton1
        val button2 = binding.instagramHashtagButton2
        val button3 = binding.instagramHashtagButton3

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

            when(model.currentHashtag()){

                "battlestation" -> {button1.isEnabled = false}
                "desksetup" -> {button2.isEnabled = false}
                "pcbuilds" -> {button3.isEnabled = false}
            }
        })
    }

    private fun initStates() {

        val swipe = binding.swipeRefresh
        val recyclerview = binding.showcaseList

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        binding.swipeRefresh.setOnRefreshListener {
            model.refreshList()
        }

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

}