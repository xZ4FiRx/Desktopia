package com.example.z4fir.desktopia.Screens.Showcase

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.lang.Exception

class Tools
{
    companion object
    {
        fun dpToPx(c: Context?, dp: Int): Int
        {
            val r = c!!.resources
            return Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp.toFloat(),
                    r.displayMetrics
                )
            )
        }
    }
}