package com.petrpol.rickandmortycharacters.utils

import androidx.viewpager2.widget.ViewPager2

class ViewPagerHelper {
    companion object{
        fun ViewPager2.onPageChanged(onPageChanged: (Int) -> Unit) {
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onPageChanged(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }
    }
}