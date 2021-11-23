package com.petrpol.rickandmortycharacters.utils

import android.util.Log
import android.widget.SearchView

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