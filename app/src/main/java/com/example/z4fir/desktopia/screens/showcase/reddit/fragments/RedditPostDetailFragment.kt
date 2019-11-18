package com.example.z4fir.desktopia.screens.showcase.reddit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.z4fir.desktopia.databinding.FragmentRedditDetailBinding
import java.util.*


class RedditPostDetailFragment : Fragment() {

    private lateinit var binding: FragmentRedditDetailBinding

    private var sourceUrl = ""
    private var postUrl = ""
    private var title = ""
    private var author = ""
    private var created: Long = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        created = arguments!!.getLong("created")
        score = arguments!!.getInt("score")

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