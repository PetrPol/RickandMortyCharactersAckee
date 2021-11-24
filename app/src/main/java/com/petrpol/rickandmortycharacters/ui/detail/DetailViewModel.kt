package com.petrpol.rickandmortycharacters.ui.detail

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

/** ViewModel for DetailFragment */
@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _character : MutableLiveData<CharacterObject> = MutableLiveData()
    private val _favourite : MutableLiveData<Boolean> = MutableLiveData()
    private val _dataState : MutableLiveData<DataState<Boolean>> = MutableLiveData()

    init {
        _dataState.value = DataState.Success(false)
        _favourite.value = false
    }

    //Getters
    val character: LiveData<CharacterObject>
        get() = _character

    val favourite: LiveData<Boolean>
        get() = _favourite

    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState

    /** Gets info about selected Character by id from DB */
    fun getCharacter(id:Int){
        viewModelScope.launch {
            repository.getSingleCharacter(id)
                .onEach { dataState -> presentData(dataState) }
                .launchIn(viewModelScope)
        }
        isFavourite(id)
    }

    /** Stores or remove character from favourite DB and invert favourite value */
    fun favouriteChange() {
        //Character not loaded check
        if (_dataState.value is DataState.Success)
            if (!(_dataState.value as DataState.Success<Boolean>).data)
                return

        if (_favourite.value!!) {
            viewModelScope.launch {
                repository.removeCharacterFromFavourites(_character.value!!.id)
                _favourite.postValue(false)
            }
        } else{
            viewModelScope.launch {
                repository.addCharacterToFavourites(_character.value!!)
                _favourite.postValue(true)
            }
        }
    }

    /** Post true if character is favourite */
    private fun isFavourite(id: Int) {
        viewModelScope.launch {
            _favourite.postValue(repository.isCharacterFavourite(id))
        }
    }

    /** Support function to present data from repository to live data */
    private fun presentData(dataState: DataState<CharacterObject>) {
        when (dataState) {
            is DataState.Loading -> _dataState.postValue(dataState)
            is DataState.Error -> _dataState.postValue(dataState)
            is DataState.Success -> {
                _dataState.postValue(DataState.Success(true))
                _character.postValue(dataState.data)
            }
        }
    }
}