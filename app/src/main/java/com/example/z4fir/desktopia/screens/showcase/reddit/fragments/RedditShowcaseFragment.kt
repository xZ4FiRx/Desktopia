package com.example.z4fir.desktopia.screens.showcase.reddit.fragments


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
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
        const val DEFAULT_SUBREDDIT = "battlestations"
        const val DEFAULT_LISTING = "hot"
        const val KEY_TITLE = "title"
    }

    private lateinit var binding: FragmentRedditShowcaseBinding
    private lateinit var model: RedditViewModel
    private val adapter = RedditPostAdapter()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUB, model.currentSubreddit())
        outState.putString(KEY_LISTING, model.currentListing())
        outState.putString(KEY_TITLE, setTitle(model.currentSubreddit()!!, model.currentListing()!!))
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


        initObservers()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRedditShowcaseBinding.inflate(inflater)
        //to use a LiveData object, this has to be added.
        binding.lifecycleOwner = this
        binding.viewModel = model

        binding.showcaseRedditToolbar.title = savedInstanceState?.getString(KEY_TITLE) ?:
                setTitle(model.currentSubreddit()!!, model.currentListing()!!)

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

        binding.showcaseRedditToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {

        model.post.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)

            binding.showcaseRedditToolbar.title = setTitle(model.currentSubreddit()!!, model.currentListing()!!)
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.reddit_listing -> {

            val currentSub = model.currentSubreddit()
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Filter by listing")
                .setItems(R.array.listing_array) { _, id ->

                    when (id) {

                        0 -> {

                            model.setListing("hot")
                            model.subredditString.value = currentSub
                        }
                        1 -> {
                            model.setListing("new")
                            model.subredditString.value = currentSub
                        }
                        2 -> {
                            model.setListing("rising")
                            model.subredditString.value = currentSub
                        }
                        3 -> {
                            model.setListing("top")
                            model.subredditString.value = currentSub
                        }
                    }
                }

            builder.show()
            true
        }

        R.id.reddit_subreddit_picker -> {

            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Filter by listing")
                .setItems(R.array.subreddit_array) { dialog, id ->

                    when (id) {
                        0 -> {
                            model.subredditString.value = "battlestations"
                        }
                        1 -> {
                            model.subredditString.value = "averageBattlestations"
                        }
                        2 -> {
                            model.subredditString.value = "battletops"
                        }
                        3 -> {
                            model.subredditString.value = "shittybattlestations"
                        }
                    }
                }

            builder.show()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun setTitle(subreddit: String, listing: String): String {

        var sub = subreddit
        var listingType = listing

        when (sub) {

            "battlestations" -> {
                sub.capitalize()
            }
            "averageBattlestations" -> {
                sub = "Average"
            }
            "battletops" -> {
                sub.capitalize()
            }
            "shittybattlestations" -> {
                sub = "Shitty"
            }
        }

        when (listing) {
            "top" -> {
                listingType = "top(24hr)"
            }
        }

        return "$sub - $listingType"
    }
}