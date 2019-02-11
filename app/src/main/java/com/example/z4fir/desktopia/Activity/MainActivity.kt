package com.example.z4fir.desktopia.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.z4fir.desktopia.Fragments.GridFragment
import com.example.z4fir.desktopia.Fragments.HelpFragment
import com.example.z4fir.desktopia.Fragments.PurchaseFragment
import com.example.z4fir.desktopia.R


class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)

        val firstFrag = GridFragment.newInstance()
        openFragment(firstFrag)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId)
        {
            R.id.navigation_first ->
            {
                val firstFrag = GridFragment.newInstance()
                openFragment(firstFrag)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_second ->
            {
                val secondFrag = PurchaseFragment.newInstance()
                openFragment(secondFrag)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_last ->
            {
                val lastFrag = HelpFragment.newInstance()
                openFragment(lastFrag)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
