package com.petrpol.rickandmortycharacters.utils

import android.widget.SearchView

/** Support class to simplify on text submit listener of search view */
class SearchViewHelper {
    companion object{
        fun SearchView.onTextSubmit(onTextSubmit: (String?) -> Unit) {

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(text: String?): Boolean {
                    onTextSubmit.invoke(text)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }
    }
}