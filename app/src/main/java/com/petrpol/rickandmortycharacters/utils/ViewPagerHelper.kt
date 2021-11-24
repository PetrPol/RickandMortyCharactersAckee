package com.petrpol.rickandmortycharacters.utils

import androidx.viewpager2.widget.ViewPager2

/** Support class to simplify callback about page changed of view pager */
class ViewPagerHelper {
    companion object{
        fun ViewPager2.onPageChanged(onPageChanged: (Int) -> Unit) {
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onPageChanged(position)
                }
            })
        }
    }
}