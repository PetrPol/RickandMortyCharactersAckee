package com.petrpol.rickandmortycharacters.utils

/** Class to define state of data */
sealed class DataState <out R> {

    /** Class represents success state of data - contains data */
    data class Success<out T>(val data: T): DataState<T>()

    /** Class represents error state of data - contains exception */
    data class Error(val exception: Exception): DataState<Nothing>()

    /** Class represents loading state of data */
    object  Loading: DataState<Nothing>()

}