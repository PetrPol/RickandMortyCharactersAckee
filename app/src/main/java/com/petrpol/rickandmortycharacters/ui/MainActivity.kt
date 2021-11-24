package com.petrpol.rickandmortycharacters.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.ui.adapters.ScreenSlidePagerAdapter
import com.petrpol.rickandmortycharacters.ui.favourite.FavouriteFragment
import com.petrpol.rickandmortycharacters.ui.home.CharactersFragment
import com.petrpol.rickandmortycharacters.utils.ViewPagerHelper.Companion.onPageChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setup nav controller
        MainActivityBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> MainActivityViewPager.currentItem = 0
                R.id.navigation_favourite -> MainActivityViewPager.currentItem = 1
            }
            true
        }

        //Setup Page adapter
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        MainActivityViewPager.adapter = pagerAdapter
        MainActivityViewPager.onPageChanged{position ->
            if (position==0){
                title = getString(R.string.title_characters)
                MainActivityBottomNav.selectedItemId = R.id.navigation_characters
                }
            else {
                title = getString(R.string.title_favourite)
                MainActivityBottomNav.selectedItemId = R.id.navigation_favourite
            }

        }
    }

    /** Navigate trough View pager on back pressed */
    override fun onBackPressed() {
        if (MainActivityViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            MainActivityViewPager.currentItem = MainActivityViewPager.currentItem - 1
        }
    }
}
