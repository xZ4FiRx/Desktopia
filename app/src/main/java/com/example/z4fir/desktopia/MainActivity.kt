package com.example.z4fir.desktopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.z4fir.desktopia.util.setupWithNavController
import com.viven.imagezoom.ImageZoomHelper


class MainActivity : AppCompatActivity() {


    private var currentNavController: LiveData<NavController>? = null
    private lateinit var imageZoomHelper: ImageZoomHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        imageZoomHelper = ImageZoomHelper(this)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_bottom_view)
        val navGraphIds = listOf(R.navigation.grid, R.navigation.purchase, R.navigation.help)
        val controller = bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent
        )

        currentNavController = controller
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return imageZoomHelper.onDispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

}