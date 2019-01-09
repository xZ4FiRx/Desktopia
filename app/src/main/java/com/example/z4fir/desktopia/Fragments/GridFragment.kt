package com.example.z4fir.desktopia.Fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.z4fir.desktopia.R


class GridFragment : Fragment()
{

    var mPager: ViewPager? = null
    var mTabs: TabLayout? = null
    private var viewPagerAdapter: PagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }


    private fun setupViewPager(pager: ViewPager)
    {
        viewPagerAdapter = PagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment()

    }

    companion object
    {
        fun newInstance(): GridFragment = GridFragment()
    }

    private inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(mangage)
    {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(postition: Int): Fragment
        {
            return mFragmentList[postition]
        }

        override fun getCount(): Int
        {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String)
        {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence?
        {
            return mFragmentTitleList[position]
        }
    }
}


