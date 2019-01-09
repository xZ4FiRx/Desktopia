package com.example.z4fir.desktopia.Fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import java.util.ArrayList








//TabsTextIcon copied from MaterialX project in Java and converted to Kotlin.














class TabsTextIcon : AppCompatActivity()
{
    private var view_pager: ViewPager? = null
    private var viewPagerAdapter: SectionsPagerAdapter? = null
    private var tab_layout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs_text_icon)

        initComponent()
        initToolbar()
    }

    private fun initToolbar()
    {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Store")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun initComponent()
    {
        view_pager = findViewById(R.id.view_pager) as ViewPager
        tab_layout = findViewById(R.id.tab_layout) as TabLayout
        setupViewPager(view_pager!!)

        tab_layout!!.setupWithViewPager(view_pager)

        tab_layout!!.getTabAt(0)!!.setIcon(R.drawable.ic_music)
        tab_layout!!.getTabAt(1)!!.setIcon(R.drawable.ic_movie)
        tab_layout!!.getTabAt(2)!!.setIcon(R.drawable.ic_book)

        // set icon color pre-selected
        tab_layout!!.getTabAt(0)!!.icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        tab_layout!!.getTabAt(1)!!.icon!!.setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)
        tab_layout!!.getTabAt(2)!!.icon!!.setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)

        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab)
            {
                tab.icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab)
            {
                tab.icon!!.setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab)
            {

            }
        })

    }

    private fun setupViewPager(viewPager: ViewPager)
    {
        viewPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(FragmentTabsStore.newInstance(), "MUSIC")    // index 0
        viewPagerAdapter!!.addFragment(FragmentTabsStore.newInstance(), "MOVIES")   // index 1
        viewPagerAdapter!!.addFragment(FragmentTabsStore.newInstance(), "BOOKS")    // index 2
        viewPager.adapter = viewPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home)
        {
            finish()
        } else
        {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class SectionsPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager)
    {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment
        {
            return mFragmentList[position]
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