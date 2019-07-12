package com.bolaware.videosfeedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.*

const val BOTTOM_NAV_C0NSTANT_KEY= "bottom_nav_height"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationBar()

        initFragments()
    }

    private fun initFragments(){
        bottom_navigation_view.post {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(BOTTOM_NAV_C0NSTANT_KEY, bottom_navigation_view.height + 5)
            homeFragment.arguments = bundle
            supportFragmentManager.beginTransaction().add(R.id.container, homeFragment).commit()
        }
    }

    private fun setupBottomNavigationBar(){
        val menuView = bottom_navigation_view.getChildAt(0) as BottomNavigationMenuView
        val iconView = menuView.getChildAt(2).findViewById<View>(com.google.android.material.R.id.icon)
        val layoutParams = iconView.layoutParams
        val displayMetrics = resources.displayMetrics
        layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, displayMetrics).toInt()
        layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, displayMetrics).toInt()
        iconView.layoutParams = layoutParams

        bottom_navigation_view.itemIconTintList = null
    }
}
