package com.example.z4fir.desktopia.screens.showcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.databinding.FragmentShowcaseLandingBinding


class ShowcaseLandingFragment : Fragment() {

    private lateinit var binding: FragmentShowcaseLandingBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentShowcaseLandingBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instagramButton = binding.instagramImageButton
        val redditButton = binding.redditImageButton

        instagramButton.setOnClickListener {
            findNavController().navigate(R.id.gridFragment)
        }

        redditButton.setOnClickListener {
            findNavController().navigate(R.id.redditPostFragment)
        }
    }
}


