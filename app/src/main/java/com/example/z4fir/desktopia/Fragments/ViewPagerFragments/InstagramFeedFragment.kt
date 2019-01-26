package com.example.z4fir.desktopia.Fragments.ViewPagerFragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.z4fir.desktopia.Model.InstaModel

import com.example.z4fir.desktopia.R
import com.example.z4fir.desktopia.Util.InstagramDataFetcher
import com.example.z4fir.desktopia.Util.RestAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InstagramFeedFragment : Fragment()
{
    private val instagramHashTag: String? = null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val service = RestAPI.instagramRetrofitInstance?.create(InstagramDataFetcher::class.java)
        val call = service?.getInstagramData("battlestations")
        call?.enqueue(object : Callback<InstaModel.InstagramResponse>
        {
            override fun onFailure(call: Call<InstaModel.InstagramResponse>, t: Throwable)
            {
                Log.d("FEED", " $t")
            }

            override fun onResponse(call: Call<InstaModel.InstagramResponse>, response: Response<InstaModel.InstagramResponse>)
            {
                val body = response.body()


                Log.d("FEED", " " + response.isSuccessful)

            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        var view: View = inflater.inflate(R.layout.fragment_instagram_feed, container, false)

        return view
    }

    companion object
    {
        fun newInstance(): InstagramFeedFragment = InstagramFeedFragment()
    }
}
