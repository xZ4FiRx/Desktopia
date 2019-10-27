package com.example.z4fir.desktopia.screens.showcase.reddit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.z4fir.desktopia.databinding.FragmentRedditShowcaseBinding


class RedditPostFragment: Fragment()
{

    private lateinit var binding: FragmentRedditShowcaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentRedditShowcaseBinding.inflate(inflater)

        return binding.root
    }
}