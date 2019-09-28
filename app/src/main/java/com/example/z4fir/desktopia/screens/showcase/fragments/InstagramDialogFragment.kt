package com.example.z4fir.desktopia.screens.showcase.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.z4fir.desktopia.databinding.DialogImageBinding
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InstagramDialogFragment : DialogFragment() {
    private lateinit var binding: DialogImageBinding

    private lateinit var blur: BlurDialogEngine
    private var shortCode: String = ""
    private var height = .0
    private var width = .0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogImageBinding.inflate(LayoutInflater.from(parentFragment!!.context))

        blur = BlurDialogEngine(activity)
        blur.setBlurRadius(8)
        blur.setDownScaleFactor(10f)
        blur.setBlurActionBar(false)
        blur.setUseRenderScript(true)

        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.url = arguments!!.getString("url")
        binding.hashTag = arguments!!.getString("hashtag")
        shortCode = arguments!!.getString("shortcode")
        height = arguments!!.getDouble("height")
        width = arguments!!.getDouble("width")

        Log.i("InstagramDialogFragment",
            "Image url: ${arguments!!.getString("url")}")
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