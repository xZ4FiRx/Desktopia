package com.example.z4fir.desktopia.screens.showcase.reddit.fragments


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.FragmentRedditShowcaseBinding
import com.example.z4fir.desktopia.screens.showcase.reddit.adapter.RedditPostAdapter
import com.example.z4fir.desktopia.screens.showcase.reddit.model.RedditPost
import com.example.z4fir.desktopia.screens.showcase.reddit.ui.RedditViewModel
import com.example.z4fir.desktopia.util.NetworkState

class RedditShowcaseFragment : Fragment() {

    companion object {
        const val KEY_SUB = "subreddit"
        const val KEY_LISTING = "listing"
        const val KEY_TITLE = "title"
        const val DEFAULT_SUBREDDIT = "battlestations"
        const val DEFAULT_LISTING = "hot"
    }

    private lateinit var binding: FragmentRedditShowcaseBinding
    private lateinit var model: RedditViewModel
    private val adapter = RedditPostAdapter()
    private val currentTitle = ""

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUB, model.currentSubreddit())
        outState.putString(KEY_LISTING, model.currentListing())
        outState.putString(KEY_TITLE, currentTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.reddit_listing -> {

            val currentSub = model.currentSubreddit()
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Filter by listing")
                .setItems(R.array.subreddits_array) { dialog, id ->

                    when (id) {

                        0 -> {
                            binding.showcaseRedditToolbar.title = "Hot"
                            model.setListing("hot")
                            model.subredditString.value = currentSub
                            model.refreshList()
                            dialog.dismiss()
                        }
                        1 -> {
                            binding.showcaseRedditToolbar.title = "New"
                            model.setListing("new")
                            model.subredditString.value = currentSub
                            model.refreshList()
                            dialog.dismiss()
                        }
                        2 -> {
                            binding.showcaseRedditToolbar.title = "Rising"
                            model.setListing("rising")
                            model.subredditString.value = currentSub
                            model.refreshList()
                            dialog.dismiss()
                        }
                        3 -> {
                            binding.showcaseRedditToolbar.title = "Top"
                            model.setListing("top")
                            model.subredditString.value = currentSub
                            model.refreshList()
                            dialog.dismiss()
                        }
                    }
                }

            builder.show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.reddit_listing_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        model = ViewModelProviders.of(this).get(RedditViewModel()::class.java)

        val subreddit = savedInstanceState?.getString(KEY_SUB) ?: DEFAULT_SUBREDDIT
        model.selectSubreddit(subreddit)

        val listing = savedInstanceState?.getString(KEY_LISTING) ?: DEFAULT_LISTING
        model.selectListing(listing)

        initAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRedditShowcaseBinding.inflate(inflater)
        //to use a LiveData object, this has to be added.
        binding.lifecycleOwner = this
        binding.viewModel = model

        binding.showcaseRedditList.adapter = adapter
        val ll = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.showcaseRedditList.layoutManager = ll

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRefresh()
        setToolbar()
    }

    private fun setToolbar() {

        (activity as AppCompatActivity).setSupportActionBar(binding.showcaseRedditToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.showcaseRedditToolbar.title = ""
        binding.showcaseRedditToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initAdapter() {

        model.post.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)
        })

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun initRefresh() {

        val swipe = binding.showcaseRedditSwipe

        swipe.setOnRefreshListener {
            model.refreshList()
        }

        model.refreshState.observe(this, Observer {
            swipe.isRefreshing = it == NetworkState.LOADING
        })
    }
}