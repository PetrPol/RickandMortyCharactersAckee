package com.petrpol.rickandmortycharacters.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.repositories.CharactersRepository
import com.petrpol.rickandmortycharacters.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel  @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _charactersList : MutableLiveData<ArrayList<CharacterObject>> = MutableLiveData()
    private val _dataState : MutableLiveData<DataState<Boolean>> = MutableLiveData()

    init {
        getFavouriteCharacters()
    }

    //Getters
    val charactersList: LiveData<ArrayList<CharacterObject>>
        get() = _charactersList

    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState


    fun getFavouriteCharacters() {
        viewModelScope.launch {
            repository.getAllFavouriteCharacters()
                .onEach { dataState -> presentData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun presentData(dataState: DataState<List<CharacterObject>>) {
        when (dataState){
            is DataState.Loading -> _dataState.postValue(dataState)
            is DataState.Error -> _dataState.postValue(dataState)
            is DataState.Success -> {
                _dataState.postValue(DataState.Success(true))
                _charactersList.postValue(dataState.data as ArrayList<CharacterObject>)
            }
        }
    }
}