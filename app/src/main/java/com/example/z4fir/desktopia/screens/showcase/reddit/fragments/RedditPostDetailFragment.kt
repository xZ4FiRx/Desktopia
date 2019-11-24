package com.example.z4fir.desktopia.screens.showcase.reddit.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.FragmentRedditDetailBinding
import com.viven.imagezoom.ImageZoomHelper
import java.util.*


class RedditPostDetailFragment : Fragment() {

    private lateinit var binding: FragmentRedditDetailBinding

    private var sourceUrl = ""
    private var postUrl = ""
    private var title = ""
    private var author = ""

    private var created: Long = 0
    private var score = 0
    private var permalink = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.reddit_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_reddit_link -> {

            val directLink = "https://reddit.com${permalink}"
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(directLink)
            }

            startActivity(intent)
            true
        }

        R.id.action_reddit_share -> {

            val directLink = postUrl
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check this out.\n" +
                        "I found it using Desktopia.\n" +
                        directLink)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share this")
            startActivity(shareIntent)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRedditDetailBinding.inflate(LayoutInflater.from(parentFragment!!.context))

        (activity as AppCompatActivity).setSupportActionBar(binding.redditDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.redditDetailToolbar.title = ""
        binding.redditDetailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        sourceUrl = arguments!!.getString("sourceurl")!!
        postUrl = arguments!!.getString("posturl")!!
        title = arguments!!.getString("title")!!
        author = arguments!!.getString("author")!!
        permalink = arguments!!.getString("permalink")!!
        created = arguments!!.getLong("created")
        score = arguments!!.getInt("score")

        ImageZoomHelper.setViewZoomable(binding.redditDetailImage)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = Date(created * 1000)
        val sdf = java.text.SimpleDateFormat("MMM d, yy", Locale.US)
        binding.dataFormat = sdf.format(data)

        binding.title = title
        binding.author = author
        binding.sourceUrl = sourceUrl.replace("amp;", "")
        binding.score = score.toString()
    }
}