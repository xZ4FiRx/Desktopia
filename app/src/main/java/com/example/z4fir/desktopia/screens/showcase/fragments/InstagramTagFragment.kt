package com.example.z4fir.desktopia.screens.showcase.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.z4fir.desktopia.screens.showcase.adapter.InstagramTagAdapter

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.z4fir.desktopia.screens.showcase.ShowcaseViewModel
import com.example.z4fir.desktopia.databinding.FragmentShowcaseBinding
import com.example.z4fir.desktopia.util.SpacingItemDecoration


class InstagramTagFragment : Fragment()
{


    private lateinit var viewModel: ShowcaseViewModel


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        viewModel = ViewModelProviders.of(this).get(ShowcaseViewModel(activity!!.application)::class.java)

        val binding = FragmentShowcaseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.hashtagDataResponse.observe(this,
            Observer {
                val hashtagList = viewModel.hashtagDataResponse.value!!
                binding.showcaseList.apply {
                    adapter = InstagramTagAdapter(hashtagList)
                    addItemDecoration(SpacingItemDecoration(2, 4, true), 0)
                }
            })

        return binding.root
    }
}