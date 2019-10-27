package com.example.z4fir.desktopia.screens.showcase.instagram.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import com.example.z4fir.desktopia.databinding.FragmentDialogBinding
import com.example.z4fir.desktopia.util.ViewAnimation
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine
import java.util.*


class InstagramDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding
    private lateinit var blur: BlurDialogEngine
    private lateinit var captionExpanded: View
    private lateinit var infoExpanded: View

    private var url = ""
    private var caption = ""
    private var shortCode: String = ""
    private var height = .0
    private var width = .0
    private var timeStamp: Long = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogBinding.inflate(LayoutInflater.from(parentFragment!!.context))

        blur = BlurDialogEngine(activity)
        blur.setBlurRadius(8)
        blur.setDownScaleFactor(10f)
        blur.setBlurActionBar(false)
        blur.setUseRenderScript(true)

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

        if(caption.isEmpty())
        {
            caption = "No caption available"
        }

        binding.caption = caption



    }

    private fun toggleSection(bt: View, lyt: View) {

        val show = toggleArrow(bt)
        val scrollView = binding.dialogScroll

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

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()

        blur.onResume(retainInstance)

        val drawable: Drawable = dialog?.window?.decorView!!.background
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        drawable.alpha = 0
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        blur.onDismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        blur.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.setDismissMessage(null)
    }
}