package com.petrpol.rickandmortycharacters.ui.favourite

import androidx.lifecycle.ViewModel
import com.petrpol.rickandmortycharacters.repositories.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel  @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

}