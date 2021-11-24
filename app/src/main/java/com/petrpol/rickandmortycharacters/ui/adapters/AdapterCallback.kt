package com.petrpol.rickandmortycharacters.ui.adapters

/** Basic callback for adapters */
interface AdapterCallback {
    /** Called when item is clicked */
    fun itemSelected(id:Int)

    /** Called when remaining 10 items to end */
    fun loadNextPage()
}