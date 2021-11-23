package com.petrpol.rickandmortycharacters.retrofit.objects

import com.google.gson.annotations.SerializedName
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.model.ResponseInfo

data class CharactersResponse constructor(
    val info:ResponseInfo,
    @SerializedName("results")
    val characters:List<CharacterObject>
)
