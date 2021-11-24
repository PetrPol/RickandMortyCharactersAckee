package com.petrpol.rickandmortycharacters.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.petrpol.rickandmortycharacters.ui.favourite.FavouriteFragment
import com.petrpol.rickandmortycharacters.ui.home.CharactersFragment

/**
 * A simple pager adapter that represents 2 ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position== 0)
            CharactersFragment()
        else
            FavouriteFragment()
    }
}