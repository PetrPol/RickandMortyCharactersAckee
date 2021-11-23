package com.petrpol.rickandmortycharacters.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.petrpol.rickandmortycharacters.repositories.CharactersRepository
import com.petrpol.rickandmortycharacters.retrofit.objects.CharactersResponse
import com.petrpol.rickandmortycharacters.ui.MainActivity
import com.petrpol.rickandmortycharacters.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel  @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _charactersList : MutableLiveData<ArrayList<CharacterObject>> = MutableLiveData()
    //Defines data state of next page loading -> true if there is next page
    private val _dataStateNext : MutableLiveData<DataState<Boolean>> = MutableLiveData()

    init {
        _dataStateNext.value = DataState.Loading
        _charactersList.value = ArrayList()
        loadNextPage()
    }


    //Getters
    val charactersList: LiveData<ArrayList<CharacterObject>>
        get() = _charactersList

    val dataStateNext: LiveData<DataState<Boolean>>
        get() = _dataStateNext

    fun loadNextPage() {
        //Test to last page
        if (_dataStateNext.value is DataState.Success)
            if (!(_dataStateNext.value as DataState.Success<Boolean>).data)
                return

        getCharactersPage(false)
    }

    private fun presentData(dataState: DataState<CharactersResponse>) {
        when (dataState){
            is DataState.Loading -> _dataStateNext.postValue(DataState.Loading)
            is DataState.Error -> _dataStateNext.postValue(dataState)
            is DataState.Success -> {
                _dataStateNext.postValue(DataState.Success(dataState.data.info.next!=null))
                val tmpList = _charactersList.value
                tmpList?.addAll(dataState.data.characters)
                _charactersList.postValue(tmpList)
            }
        }
    }

    fun refresh() {
        _charactersList.value = ArrayList()
        getCharactersPage(true)
    }

    fun searchByName(text: String?) {
        if (text==null)
            return

        _charactersList.value = ArrayList()
        getCharactersPage(true,text)

    }

    private fun getCharactersPage(reset: Boolean,nameQuery: String? = null){
        viewModelScope.launch {
            repository.getCharactersPage(reset,nameQuery)
                .onEach { dataState -> presentData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    fun setFavourite(favourite: Boolean, id: Int) {
        Log.i("TEST","setFavourite")
        val tmpList = _charactersList.value!!
        for (character in tmpList)
            if (character.id == id) {
                character.favourite = favourite
                Log.i("TEST","setFavourite " + id + "  " + character.name)
                break
            }
        _charactersList.postValue(tmpList)
    }
}