package com.example.z4fir.desktopia.Fragments.ViewPagerFragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.z4fir.desktopia.R


class RedditFeedFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        var view: View = inflater.inflate(R.layout.fragment_reddit_feed, container, false)

        return view
    }

    companion object
    {
        fun newInstance(): RedditFeedFragment = RedditFeedFragment()
    }


}
