package com.example.z4fir.desktopia.Fragments


import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.z4fir.desktopia.Fragments.ViewPagerFragments.InstaGridFragment
import com.example.z4fir.desktopia.Fragments.ViewPagerFragments.RedditFeedFragment

import com.example.z4fir.desktopia.R


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GridFragment() : Fragment()
{
    private var viewPagerAdapter: PagerAdapter? = null

    companion object
    {
        fun newInstance(): GridFragment = GridFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val view: View = inflater.inflate(R.layout.fragment_grid, container, false)

        val pagerView: ViewPager = view.findViewById(R.id.view_pager)
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)

        viewPagerAdapter = PagerAdapter(childFragmentManager)
        viewPagerAdapter!!.addFragment(InstaGridFragment.newInstance(), "Instagram")
        viewPagerAdapter!!.addFragment(RedditFeedFragment.newInstance(), "Reddit")
        pagerView.offscreenPageLimit = 2
        pagerView.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(pagerView)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_instagram)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_reddit)

        tabLayout.getTabAt(0)!!.icon!!.setColorFilter(ContextCompat.getColor(this.context!!, R.color.INACTIVE_ICONS)
            , PorterDuff.Mode.SRC_IN)
        tabLayout.getTabAt(1)!!.icon!!.setColorFilter(ContextCompat.getColor(this.context!!, R.color.INACTIVE_ICONS)
            , PorterDuff.Mode.SRC_IN)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabReselected(tab: TabLayout.Tab?)
            {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {
                tab!!.icon!!.setColorFilter(resources.getColor(R.color.INACTIVE_ICONS), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                tab!!.icon!!.setColorFilter(resources.getColor(R.color.ACTIVE_ICONS), PorterDuff.Mode.SRC_IN)
            }
        })

        return view
    }

    private inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager)
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
            return null
        }
    }
}