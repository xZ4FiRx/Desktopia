package com.example.z4fir.desktopia.screens.showcase.instagram.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.FragmentInstagramDetailBinding
import com.example.z4fir.desktopia.util.ViewAnimation
import com.viven.imagezoom.ImageZoomHelper
import java.util.*


class InstagramDetailFragment : Fragment() {

    private lateinit var binding: FragmentInstagramDetailBinding
    private lateinit var captionExpanded: View
    private lateinit var infoExpanded: View

    private var url = ""
    private var caption = ""
    private var shortCode: String = ""
    private var height = .0
    private var width = .0
    private var timeStamp: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.instagram_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_instagram_direct -> {

            val directLink = "https://instagram.com/p/${shortCode}"
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(directLink)
            }

            startActivity(intent)
            true
        }

        R.id.action_instagram_share -> {

            val directLink = "https://instagram.com/p/${shortCode}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check this out.\n" +
                        "I found it using Desktopia.\n" +
                        "$directLink")
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

        binding = FragmentInstagramDetailBinding.inflate(LayoutInflater.from(parentFragment!!.context))

        (activity as AppCompatActivity).setSupportActionBar(binding.instagramDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)

        binding.instagramDetailToolbar.title = ""
        binding.instagramDetailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        captionExpanded = binding.expandedCaption
        infoExpanded = binding.expandedInfo

        url = arguments!!.getString("url")!!
        caption = arguments!!.getString("caption")!!

        shortCode = arguments!!.getString("shortcode")!!
        width = arguments!!.getDouble("width")
        height = arguments!!.getDouble("height")
        timeStamp = arguments!!.getLong("timestamp")

        binding.btToggleInfo.setOnClickListener { view ->

            toggleSection(view, infoExpanded)
        }
        binding.btToggleCaption.setOnClickListener { view ->

            toggleSection(view, captionExpanded)
        }

        ImageZoomHelper.setViewZoomable(binding.instagramDetailImage)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = Date(timeStamp * 1000)
        val sdf = java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US)
        binding.timeStamp = sdf.format(data)

        binding.url = url

        if (caption.isEmpty()) {

            caption = "No caption available"
        }

        binding.caption = caption
    }

    private fun toggleSection(bt: View, lyt: View) {

        val show = toggleArrow(bt)
        val scrollView = binding.instagramDetailScroll

        if (show) {
            ViewAnimation.expand(lyt, object : ViewAnimation.AnimListener {
                override fun onFinish() {
                    nestedScrollTo(scrollView, lyt)
                }
            })
        } else {
            ViewAnimation.collapse(lyt)
        }
    }

    fun nestedScrollTo(nested: ScrollView, targetView: View) {
        nested.post {

            nested.scrollTo(500, targetView.bottom)
        }
    }

    private fun toggleArrow(view: View): Boolean {

        return if (view.rotation == 0f) {

            view.animate().setDuration(200).rotation(180f)
            true
        } else {

            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}