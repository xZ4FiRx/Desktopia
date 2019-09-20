package com.example.z4fir.desktopia.screens.showcase.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.screens.showcase.adapter.InstagramTagAdapter

import com.example.z4fir.desktopia.R
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.z4fir.desktopia.screens.showcase.ShowcaseViewModel
import com.example.z4fir.desktopia.databinding.FragmentShowcaseBinding
import com.example.z4fir.desktopia.screens.showcase.model.InstagramResponse
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
        viewModel.instagramTagResponse.observe(this,
            Observer {
                val data = viewModel.instagramTagResponse.value!!
                binding.showcaseList.apply {
                    adapter = InstagramTagAdapter(data, viewModel)
                    addItemDecoration(
                        SpacingItemDecoration(
                            2,
                            3,
                            true
                        ),
                        0
                    )
                }
            })
        //TODO Add observable to clickListener to launch new a DetailFragment.

        return binding.root
    }

    //TODO Add network error handling
    //TODO
}