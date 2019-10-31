package com.example.z4fir.desktopia.screens.showcase.instagram.fragments

import android.os.Bundle
import android.view.*
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.FragmentInstagramDetailBinding
import com.example.z4fir.desktopia.util.ViewAnimation
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.instagram_detail_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_instagram_direct -> {

            Toast.makeText(context, "This is the best way to kill someone who ", Toast.LENGTH_SHORT)
            true

        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInstagramDetailBinding.inflate(LayoutInflater.from(parentFragment!!.context))

        (activity as AppCompatActivity).setSupportActionBar(binding.tagPostDetailToolbar)

        binding.tagPostDetailToolbar.title = ""

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
        val scrollView = binding.tagPostDetailScroll

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