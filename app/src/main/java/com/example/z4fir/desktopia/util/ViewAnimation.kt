package com.example.z4fir.desktopia.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.animation.Animation
import android.view.animation.Transformation

object ViewAnimation {

    fun expand(v: View, animListener: AnimListener) {
        val a = expandAction(v)

        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                animListener.onFinish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        v.startAnimation(a)
    }

    private fun expandAction(v: View): Animation {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val targtetHeight = v.measuredHeight

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    LayoutParams.WRAP_CONTENT
                else
                    (targtetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (targtetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
        return a
    }

    fun collapse(v: View) {

        val initialHeight = v.measuredHeight

        val a = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    interface AnimListener {
        fun onFinish()
    }

}
