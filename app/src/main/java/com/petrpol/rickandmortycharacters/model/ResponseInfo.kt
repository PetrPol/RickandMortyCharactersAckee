package com.petrpol.rickandmortycharacters.model

/** Data class represents info about response list */
data class ResponseInfo constructor(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)