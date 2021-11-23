package com.petrpol.rickandmortycharacters.ui.adapters

interface AdapterCallback {
    fun itemSelected(id:Int)
    fun loadNextPage()
}