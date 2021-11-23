package com.petrpol.rickandmortycharacters.model

data class ResponseInfo constructor(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)