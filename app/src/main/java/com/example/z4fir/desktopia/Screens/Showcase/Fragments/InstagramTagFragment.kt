package com.example.z4fir.desktopia.Screens.Showcase.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.example.z4fir.desktopia.Screens.Showcase.Recyclerview.InstagramTagAdapter

import com.example.z4fir.desktopia.R
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.z4fir.desktopia.Screens.Showcase.ShowcaseViewModel
import com.example.z4fir.desktopia.databinding.FragmentShowcaseBinding


class InstagramTagFragment : Fragment()
{
    private lateinit var showcaseViewModel: ShowcaseViewModel
    private lateinit var binding: FragmentShowcaseBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        //Using DatabindingUtil class to associate fragment_showcase with InstagramTagFragment.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_showcase, container, false)

        //Initialization of the ViewModel object.
        showcaseViewModel = ViewModelProviders.of(this).get(ShowcaseViewModel::class.java)

        val layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
        binding.showcaseList.layoutManager = layoutManager
        val adapter = InstagramTagAdapter()
        binding.showcaseList.adapter = adapter


        val swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener {
            //TODO Refresh data here
        }


        return binding.root
    }
}