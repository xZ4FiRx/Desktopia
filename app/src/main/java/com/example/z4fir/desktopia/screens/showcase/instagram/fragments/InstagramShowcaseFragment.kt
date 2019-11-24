package com.example.z4fir.desktopia.screens.showcase.instagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.z4fir.desktopia.screens.showcase.instagram.adapter.InstagramTagAdapter

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.screens.showcase.instagram.ui.InstagramViewModel
import com.example.z4fir.desktopia.util.NetworkState
import com.example.z4fir.desktopia.screens.showcase.instagram.model.Edges
import com.example.z4fir.desktopia.util.SpacingItemDecoration
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.z4fir.desktopia.databinding.FragmentInstagramShowcaseBinding


class InstagramShowcaseFragment : Fragment() {

    companion object {
        const val KEY = "hashtag"
        const val DEFAULT_HASHTAG = "battlestation"
    }

    private lateinit var binding: FragmentInstagramShowcaseBinding
    private lateinit var model: InstagramViewModel
    private val adapter = InstagramTagAdapter()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, model.currentHashtag())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        model = ViewModelProviders.of(this).get(InstagramViewModel(activity!!.application)::class.java)

        val hashtag = savedInstanceState?.getString(KEY) ?: DEFAULT_HASHTAG
        model.addingHashtag(hashtag)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentInstagramShowcaseBinding.inflate(inflater)



        binding.lifecycleOwner = this
        binding.viewModel = model

        setToolbar()
        setHashTagButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRefresh()
    }

    private fun setToolbar() {

        (activity as AppCompatActivity).setSupportActionBar(binding.showcaseInstagramToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.showcaseInstagramToolbar.title = ""
        binding.showcaseInstagramToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_instagramShowcaseFragment_to_showcaseLanding)
        }
    }

    private fun setHashTagButtons() {

        val button1 = binding.instagramHashtagButton1
        val button2 = binding.instagramHashtagButton2
        val button3 = binding.instagramHashtagButton3

        button1.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("battlestation")

                button1.isEnabled = false
                button2.isEnabled = true
                button3.isEnabled = true
            }
        }

        button2.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("desksetup")

                button1.isEnabled = true
                button2.isEnabled = false
                button3.isEnabled = true
            }
        }

        button3.setOnClickListener {

            if (!binding.swipeRefresh.isRefreshing) {

                model.addingHashtag("dreamsetup")

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

        binding.showcaseInstagramList.adapter = adapter

        val gm = GridLayoutManager(activity, 2)
        gm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                R.layout.network_state_layout -> 2
                R.layout.instagram_grid_view_item -> 1
                else -> 1
            }
        }

        binding.showcaseInstagramList.layoutManager = gm
        binding.showcaseInstagramList.addItemDecoration(SpacingItemDecoration(2,
            4, true), 0)

        model.post.observe(this, Observer<PagedList<Edges>> {

            adapter.submitList(it)

            when(model.currentHashtag()){

                "battlestation" -> {button1.isEnabled = false}
                "desksetup" -> {button2.isEnabled = false}
                "dreamsetup" -> {button3.isEnabled = false}
            }
        })
    }

    private fun initRefresh() {

        val swipe = binding.swipeRefresh
        val recyclerview = binding.showcaseInstagramList

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



}